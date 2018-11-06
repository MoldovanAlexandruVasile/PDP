import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Polynomial polynomial1;
    private static Polynomial polynomial2;
    private static Polynomial polynomial3;

    public static void main(String[] args) throws Exception {
        initPolynoms();
        Integer option = whichToRun();
        System.out.println("\n");
        printPolynom(polynomial1, "\tA(X) = ");
        printPolynom(polynomial2, "\tB(X) = ");

        if (option == 1)
            for (Integer coef = 0; coef < polynomial1.getCoefs().size(); coef++) {
                RunnableThread runnableThread = new RunnableThread(polynomial1, polynomial2, polynomial3, coef);
                runnableThread.start();
            }
        else {
            Karatsuba k = new Karatsuba(polynomial1.getCoefs(), polynomial2.getCoefs(), polynomial1.getCoefs().size());
            k.start();
            try {
                k.join();
            } catch (InterruptedException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            polynomial3.setCoefs(k.getResult());
        }
        printPolynom(polynomial3, "\n\tA(X)*B(X) = ");
    }

    private static void initPolynoms() throws Exception {
        List<Integer> coefs = new ArrayList<>();
        Integer degree = readPolynomDegree("Read A degree: ");
        for (Integer no = 0; no <= degree; no++)
            coefs.add(randomNumber());
        polynomial1 = new Polynomial(coefs, degree);
        Integer i = degree;

        coefs = new ArrayList<>();
        degree = readPolynomDegree("Read B degree: ");
        for (Integer no = 0; no <= degree; no++)
            coefs.add(randomNumber());
        polynomial2 = new Polynomial(coefs, degree);
        Integer j = degree;

        Integer size = i + j;
        coefs = new ArrayList<>();
        for (Integer no = 0; no <= size; no++)
            coefs.add(0);
        polynomial3 = new Polynomial(coefs, size);
    }

    private static Integer readPolynomDegree(String polynom) throws IOException {
        System.out.print(polynom);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer degree = Integer.valueOf(reader.readLine());
        return degree;
    }

    private static Integer whichToRun() throws IOException {
        System.out.print("\n\t 1. Classic.");
        System.out.print("\n\t 2. Karatsuba.");
        System.out.print("\n\t\t Choice: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer degree = Integer.valueOf(reader.readLine());
        return degree;
    }

    private static void printPolynom(Polynomial polynomial, String string) {
        polynomial.printPolynom(string);
    }

    public static Integer randomNumber() {
        Integer lowBound = -5;
        Integer highBound = 10;
        Integer range = highBound - lowBound + lowBound;
        Integer rand = (int) (Math.random() * range) + lowBound;
        return rand;
    }
}
