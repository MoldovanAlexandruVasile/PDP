import Domain.Matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static Domain.AppUtils.randomNumber;
import static Domain.AppUtils.separate;

public class Main {

    private static final int MAX_THREADS_TO_RUN = 3;

    public static void main(String[] args) throws IOException, InterruptedException {
        String operation = readOperation();
        Integer threads = readThreadsNumber();

        List<Matrix> list = defineMatrixAndNumber(operation);
        Matrix matrix1 = list.get(0);
        Matrix matrix2 = list.get(1);
        printTheMatrix(matrix1, matrix2);
        Matrix matrix3 = defineMatrix3(matrix1, matrix2, operation);
        ArrayList<FutureTask> threadsCreated = new ArrayList<>();

        if (threads != 0) {
            if (threads == 1) {
                System.out.println("First branch\nCreating a single thread for the entire matrix.");
                System.out.println("Number of created threads: 1.\n");
                RunnableThread runnableThread = new RunnableThread(1000, "1", matrix1, matrix2, matrix3, 0, 1, operation, -1);
                FutureTask<String> futureTask = new FutureTask<>(runnableThread);
                threadsCreated.add(futureTask);
            } else if (threads >= matrix1.getNumberLines() && threads < matrix1.getNumberLines() * matrix1.getNumberColumns()) {
                System.out.println("Second branch\nCreating a thread for each line of the matrix.");
                System.out.println("Number of created threads: " + matrix1.getNumberLines() + "\n");
                for (Integer l = 0; l < matrix1.getNumberLines(); l++) {
                    String name = "Thread " + String.valueOf(l);
                    RunnableThread runnableThread = new RunnableThread(1000, name, matrix1, matrix2, matrix3, l, 1, operation, -1);
                    FutureTask<String> futureTask = new FutureTask<>(runnableThread);
                    threadsCreated.add(futureTask);
                }
            } else if (threads >= matrix1.getNumberLines() * matrix1.getNumberColumns()) {
                System.out.println("Third branch.\nCreating a thread for each value of the matrix.");
                System.out.println("Number of created threads: " + matrix1.getNumberLines() * matrix1.getNumberColumns() + "\n");
                for (Integer l = 0; l < matrix1.getNumberLines(); l++)
                    for (Integer c = 0; c < matrix1.getNumberColumns(); c++) {
                        String name = "Thread " + String.valueOf(l) + " " + String.valueOf(c);
                        RunnableThread runnableThread = new RunnableThread(1000, name, matrix1, matrix2, matrix3, l, 1, operation, c);
                        FutureTask<String> futureTask = new FutureTask<>(runnableThread);
                        threadsCreated.add(futureTask);
                    }
            } else {
                System.out.println("Fourth branch\nCreating a thread that iterates the from K to K lines.");
                threads = checkNumberOfThreadsThatCanBeUsed(threads);
                System.out.println("Number of created threads: " + threads + "\n");
                Integer fromLineToLine = checkNumberOfThreadsThatCanBeUsed(threads);
                for (Integer startFrom = 0; startFrom < threads; startFrom++) {
                    String name = "Thread " + String.valueOf(startFrom);
                    RunnableThread runnableThread = new RunnableThread(1000, name, matrix1, matrix2, matrix3, startFrom, fromLineToLine, operation, -1);
                    FutureTask<String> futureTask = new FutureTask<>(runnableThread);
                    threadsCreated.add(futureTask);
                }
            }
        } else System.out.println("Cannot create 0 threads to execute this !");

        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS_TO_RUN);
        for (Integer pos = 0; pos < threadsCreated.size(); pos++)
            executor.execute(threadsCreated.get(pos));

        while (true) {
            try {
                FutureTask<String> futureTask2 = null;
                Integer pos = 0;
                while (pos != threadsCreated.size()) {
                    FutureTask<String> current = threadsCreated.get(pos);
                    if (current.isDone()) {
                        System.out.println(current.get() + " - DONE !");
                        threadsCreated.remove(current);
                    } else {
                        futureTask2 = current;
                        pos++;
                    }
                }
                if (0 == threadsCreated.size()) {
                    executor.shutdown();
                    Thread.sleep(1000);
                    System.out.println("\nResult:");
                    matrix3.printMatrix();
                    return;
                } else System.out.println("Waiting for FutureTasks to finish...");
                futureTask2.get(200L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException ex) {

            }
        }
    }

    private static Matrix defineMatrix3(Matrix matrix1, Matrix matrix2, String operation) {
        if (operation.equals("+") || operation.equals("-"))
            return new Matrix(matrix1.getNumberLines(), matrix1.getNumberColumns(), false);
        else if (operation.equals("*"))
            return new Matrix(matrix1.getNumberLines(), matrix2.getNumberColumns(), false);
        return null;
    }

    private static Integer checkNumberOfThreadsThatCanBeUsed(Integer numberLines) {
        if (numberLines % 2 <= numberLines % 3)
            return 2;
        else return 3;
    }

    private static void printTheMatrix(Matrix matrix1, Matrix matrix2) {
        System.out.println("Matrix 1:\n");
        matrix1.printMatrix();
        separate();
        System.out.println("Matrix 2:\n");
        matrix2.printMatrix();
        separate();
    }

    public static Integer readThreadsNumber() throws IOException {
        System.out.print("Number of threads: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer operation = Integer.valueOf(reader.readLine());
        return operation;
    }

    public static String readOperation() throws IOException {
        System.out.print("Operation you want to perform: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String operation = reader.readLine();
        return operation;
    }

    public static List<Matrix> defineMatrixAndNumber(String operation) {
        Integer lines = randomNumber();
        Integer columns = randomNumber();
        Matrix matrix1 = null;
        Matrix matrix2 = null;
        if (operation.equals("+") || operation.equals("-")) {
            matrix1 = new Matrix(lines, columns, true);
            matrix2 = new Matrix(lines, columns, true);
        } else if (operation.equals("*")) {
            matrix1 = new Matrix(lines, columns, true);
            matrix2 = new Matrix(columns, lines, true);
        } else System.out.println("Error: invalid operation sign.");
        List<Matrix> list = new ArrayList<>();
        list.add(matrix1);
        list.add(matrix2);
        return list;
    }
}
