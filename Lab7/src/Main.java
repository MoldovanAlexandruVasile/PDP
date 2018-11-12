import ProduceConsumer.PC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static Domain.AppUtils.randomNumber;

public class Main {
    private static Integer k;
    private static Integer n;
    private static List<Integer> numbers = new ArrayList<>();
    private static List<Integer> results = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        n = readInteger("\n\tn = ");
        k = readInteger("\tk = ");
        for (Integer i = 0; i < n; i++)
            numbers.add(randomNumber());
        printList("\n\t\tNumbers: ", numbers);
        results.add(numbers.get(0));
        for (Integer i = 0; i < n - k + 1; i++) {
            PC pc = new PC(numbers, k, i, results);
            runPC(pc);
        }
        printList("\t\tResult: ", results);
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
