import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Integer> number1List = new ArrayList<>();
    private static List<Integer> number2List = new ArrayList<>();
    private static List<Integer> number3List = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("\n\n");
        String number1 = readNumber();
        String number2 = readNumber();
        Boolean isFirstLower = false;
        Integer digitsBigger;
        if (number1.length() < number2.length()) {
            isFirstLower = true;
            digitsBigger = number2.length() - number1.length();
        } else {
            digitsBigger = number1.length() - number2.length();
        }

        if (isFirstLower) {
            for (Integer i = 0; i < digitsBigger; i++)
                number1List.add(0);
            for (Integer i = 0; i < number1.length(); i++)
                number1List.add(Character.getNumericValue(number1.charAt(i)));
            for (Integer i = 0; i < number2.length(); i++)
                number2List.add(Character.getNumericValue(number2.charAt(i)));
        } else {
            for (Integer i = 0; i < digitsBigger; i++)
                number2List.add(0);
            for (Integer i = 0; i < number2.length(); i++)
                number2List.add(Character.getNumericValue(number2.charAt(i)));
            for (Integer i = 0; i < number1.length(); i++)
                number1List.add(Character.getNumericValue(number1.charAt(i)));
        }

        for (Integer i = 0; i < number1List.size(); i++)
            number3List.add(0);
        BinaryTree tree = new BinaryTree();
        Integer remainder = 0;
        for (Integer i = number1List.size() - 1; i >= 0; i--) {
            RunnableThread runnableThread = new RunnableThread(number1List, number2List, number3List, remainder, i);
            tree.add(runnableThread);
        }
        tree.traverseLevelOrder();

//        Integer remainder = 0;
//        for (Integer i = number1List.size() - 1; i >= 0; i--) {
//            RunnableThread runnableThread = new RunnableThread(number1List, number2List, number3List, remainder, i);
//            runnableThread.start();
//            remainder = runnableThread.getRemainder();
//        }

        String number = "";
        for (Integer i = 0; i < number3List.size(); i++)
            number += number3List.get(i);
        System.out.println("\n\t\tResult: " + number);
    }

    private static String readNumber() throws IOException {
        System.out.print("Number: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String number = reader.readLine();
        return number;
    }
}
