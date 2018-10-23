import Domain.Matrix;
import ProduceConsumer.Consumer;
import ProduceConsumer.Producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static Domain.AppUtils.log;
import static Domain.AppUtils.randomNumber;
import static Domain.AppUtils.separate;

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
                Producer producer = new Producer(1000, "Produce 1", matrix1, matrix2, auxResult, 0, 1, -1);
                Consumer consumer = new Consumer(1000, "Consume 1", auxResult, matrix3, finalResult, 0, 1, -1);
                producer.start();
                consumer.start();
            } else if (threads >= matrix1.getNumberLines() && threads < matrix1.getNumberLines() * matrix1.getNumberColumns()) {
                System.out.println("Second branch\nCreating a thread for each line of the matrix.");
                System.out.println("Number of created threads: " + matrix1.getNumberLines() + "\n");
                for (Integer l = 0; l < matrix1.getNumberLines(); l++) {
                    String name = "Produce " + String.valueOf(l);
                    Producer producer = new Producer(1000, name, matrix1, matrix2, auxResult, l, 1, -1);
                    name = "Consume " + String.valueOf(l);
                    Consumer consumer = new Consumer(1000, name, auxResult, matrix3, finalResult, l, 1, -1);
                    producer.start();
                    consumer.start();
                }
            } else if (threads >= matrix1.getNumberLines() * matrix1.getNumberColumns()) {
                System.out.println("Third branch.\nCreating a thread for each value of the matrix.");
                System.out.println("Number of created threads: " + matrix1.getNumberLines() * matrix1.getNumberColumns() + "\n");
                for (Integer l = 0; l < matrix1.getNumberLines(); l++)
                    for (Integer c = 0; c < matrix1.getNumberColumns(); c++) {
                        String name = "Produce " + String.valueOf(l) + " " + String.valueOf(c);
                        Producer producer = new Producer(1000, name, matrix1, matrix2, auxResult, l, 1, c);
                        name = "Consume " + String.valueOf(l) + " " + String.valueOf(c);
                        Consumer consumer = new Consumer(1000, name, auxResult, matrix3, finalResult, l, 1, c);
                        producer.start();
                        consumer.start();
                    }
            } else {
                System.out.println("Fourth branch\nCreating a thread that iterates the from K to K lines.");
                threads = checkNumberOfThreadsThatCanBeUsed(threads);
                System.out.println("Number of created threads: " + threads + "\n");
                Integer fromLineToLine = checkNumberOfThreadsThatCanBeUsed(threads);
                for (Integer startFrom = 0; startFrom < threads; startFrom++) {
                    String name = "Produce " + String.valueOf(startFrom);
                    Producer producer = new Producer(1000, name, matrix1, matrix2, auxResult, startFrom, fromLineToLine, -1);
                    name = "Consume " + String.valueOf(startFrom);
                    Consumer consumer = new Consumer(1000, name, auxResult, matrix3, finalResult, startFrom, fromLineToLine, -1);
                    producer.start();
                    consumer.start();
                }
            }
        } else log("Cannot create 0 threads to execute this !");

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
}
