import ProduceConsumer.PC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static Domain.AppUtils.randomNumber;

public class Main {
    private static Integer k;
    private static Integer n;
    //    private static List<Integer> numbers = new ArrayList<>();
//    private static List<Integer> results = new ArrayList<>();
    private static Integer[][] matrix;

    public static void main(String[] args) throws IOException, InterruptedException {
        n = readInteger("\n\tn = ");
        k = readInteger("\tk = ");

        matrix = new Integer[n][n];
        for (Integer i = 0; i < n; i++)
            for (Integer j = 0; j < n; j++)
                if (i == j)
                    matrix[i][i] = randomNumber();
                else matrix[i][j] = 0;

        String str = printMatrix("\n\tInitial Matrix:\n");
        System.out.println(str);
        for (Integer i = 0; i < n - k + 1; i++) {
            PC pc = new PC(matrix, n, k, i, 0);
            runPC(pc);
        }

        Integer line = 0;
        Integer column = 1;
        Integer trials = 0;
        while (trials < n /2) {
            if (column < n) {
                if (matrix[line][column] == 0) {
                    PC pc = new PC(matrix, n, k, line, column);
                    runPC(pc);
                    line++;
                    column++;
                } else {
                    column++;
                }
            } else {
                line = 0;
                column = 1;
            }
            trials++;
        }

        str = printMatrix("\n\tResult:\n");
        System.out.println(str);

//        for (Integer i = 0; i < n; i++)
//            numbers.add(randomNumber());
//
//        printList("\n\t\tNumbers: ", numbers);
//        results.add(numbers.get(0));
//        for (Integer i = 0; i < n - k + 1; i++) {
//            PC pc = new PC(numbers, k, i, results);
//            runPC(pc);
//        }
//        printList("\t\tResult: ", results);
    }


    private static String printMatrix(String str) {

        for (Integer i = 0; i < n; i++)
            for (Integer j = 0; j < n; j++) {
                str += String.valueOf(matrix[i][j]) + " ";
                if (j == n - 1)
                    str += "\n";
            }
        return str;
    }

    private static void printList(String string, List<Integer> list) {
        System.out.print(string);
        System.out.println(list.toString());
    }

    private static Integer readInteger(String string) throws IOException {
        System.out.print(string);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer intNumber = Integer.valueOf(reader.readLine());
        return intNumber;
    }

    private static void runPC(PC pc) {
        Thread t1 = new Thread(() -> {
            try {
                pc.runProducer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                pc.runConsume();
            } catch (InterruptedException e) {
                e.printStackTrace();
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