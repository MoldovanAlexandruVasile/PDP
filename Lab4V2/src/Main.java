import Domain.Matrix;
import ProduceConsumer.PC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static Domain.AppUtils.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Integer threads = readThreadsNumber();

        List<Matrix> list = defineMatrixAndNumber();
        Matrix matrix1 = list.get(0);
        Matrix matrix2 = list.get(1);
        Matrix auxResult = defineMatrix(matrix1, matrix2);
        Matrix matrix3 = defineMatrix3(auxResult);
        Matrix finalResult = defineMatrix(auxResult, matrix3);
        printMatrix(matrix1, "1");
        printMatrix(matrix2, "2");
        printMatrix(matrix3, "3");

        if (threads != 0) {
            if (threads == 1) {
                System.out.println("First branch\nCreating a single thread for the entire matrix.");
                System.out.println("Number of created threads: 1.\n");
                PC pc = new PC("1", matrix1, matrix2, auxResult, matrix3, finalResult, 0, 1, -1);
                runStuff(pc);
            } else if (threads >= matrix1.getNumberLines() && threads < matrix1.getNumberLines() * matrix1.getNumberColumns()) {
                System.out.println("Second branch\nCreating a thread for each line of the matrix.");
                System.out.println("Number of created threads: " + matrix1.getNumberLines() + "\n");
                for (Integer l = 0; l < matrix1.getNumberLines(); l++) {
                    String name = String.valueOf(l);
                    PC pc = new PC(name, matrix1, matrix2, auxResult, matrix3, finalResult, l, 1, -1);
                    runStuff(pc);
                }
            } else if (threads >= matrix1.getNumberLines() * matrix1.getNumberColumns()) {
                System.out.println("Third branch.\nCreating a thread for each value of the matrix.");
                System.out.println("Number of created threads: " + matrix1.getNumberLines() * matrix1.getNumberColumns() + "\n");
                for (Integer l = 0; l < matrix1.getNumberLines(); l++)
                    for (Integer c = 0; c < matrix1.getNumberColumns(); c++) {
                        String name = String.valueOf(l) + " " + String.valueOf(c);
                        PC pc = new PC(name, matrix1, matrix2, auxResult, matrix3, finalResult, l, 1, c);
                        runStuff(pc);
                    }
            } else {
                System.out.println("Fourth branch\nCreating a thread that iterates the from K to K lines.");
                threads = checkNumberOfThreadsThatCanBeUsed(threads);
                System.out.println("Number of created threads: " + threads + "\n");
                Integer fromLineToLine = checkNumberOfThreadsThatCanBeUsed(threads);
                for (Integer startFrom = 0; startFrom < threads; startFrom++) {
                    String name = String.valueOf(startFrom);
                    PC pc = new PC(name, matrix1, matrix2, auxResult, matrix3, finalResult, startFrom, fromLineToLine, -1);
                    runStuff(pc);
                }
            }
        } else log("Cannot create 0 threads to execute this !");
        printMatrix(auxResult, "aux");
        printMatrix(finalResult, "resulted");
    }

    private static Matrix defineMatrix(Matrix matrix1, Matrix matrix2) {
        return new Matrix(matrix1.getNumberLines(), matrix2.getNumberColumns(), false);
    }

    private static Matrix defineMatrix3(Matrix aux) {
        return new Matrix(aux.getNumberColumns(), aux.getNumberLines(), true);
    }

    private static Integer checkNumberOfThreadsThatCanBeUsed(Integer numberLines) {
        if (numberLines % 2 <= numberLines % 3)
            return 2;
        else return 3;
    }

    private static void printMatrix(Matrix matrix1, String name) {
        System.out.println("\nMatrix " + name + ":\n");
        matrix1.printMatrix();
        separate();
    }

    public static Integer readThreadsNumber() throws IOException {
        System.out.print("Number of threads: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer operation = Integer.valueOf(reader.readLine());
        return operation;
    }

    public static List<Matrix> defineMatrixAndNumber() {
        Integer lines = randomNumber();
        Integer columns = randomNumber();
        Matrix matrix1 = new Matrix(lines, columns, true);
        Matrix matrix2 = new Matrix(columns, lines, true);
        List<Matrix> list = new ArrayList<>();
        list.add(matrix1);
        list.add(matrix2);
        return list;
    }

    public static void runStuff(PC pc) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.runProduce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.runConsume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}