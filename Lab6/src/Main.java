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
        while (true) {
            initPolynoms();
            System.out.println("\n");
            printPolynom(polynomial1, "\tA(X) = ");
            printPolynom(polynomial2, "\tB(X) = ");
            Integer option = whichToRun();
            if (option == 1) {
                for (Integer coef = 0; coef < polynomial1.getCoefs().size(); coef++) {
                    RunnableThread runnableThread = new RunnableThread(polynomial1, polynomial2, polynomial3, coef);
                    runnableThread.start();
                }
            } else if (option == 2) {
                times();
            } else if (option == 3) {
                try {
                    Karatsuba k = new Karatsuba(polynomial1.getCoefs(), polynomial2.getCoefs(), polynomial1.getCoefs().size());
                    k.start();
                    k.join();
                    polynomial3.setCoefs(k.getResult());
                } catch (InterruptedException | IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (option == 4) {
                timeKaratsuba();
            } else if (option == 0)
                break;
            printPolynom(polynomial3, "\n\tA(X)*B(X) = ");
        }
    }

    private static void initPolynoms() throws Exception {
        List<Integer> coefs = new ArrayList<>();
        Integer degree = readPolynomDegree("\n\tRead A degree: ");
        for (Integer no = 0; no <= degree; no++)
            coefs.add(randomNumber());
        polynomial1 = new Polynomial(coefs, degree);
        Integer i = degree;

        coefs = new ArrayList<>();
        degree = readPolynomDegree("\tRead B degree: ");
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
        System.out.print("\n\t1. Classic threads.");
        System.out.print("\n\t2. Classic sequencial.");
        System.out.print("\n\t3. Karatsuba threads.");
        System.out.print("\n\t4. Karatsuba sequencial.");
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

    private static void times() {
        for (int i = 0; i <= polynomial1.getDegree(); i++)
            for (int j = 0; j <= polynomial2.getDegree(); j++)
                polynomial3.addCoefAtIndex(i + j, polynomial1.getCoefs().get(i), polynomial2.getCoefs().get(j));
    }

    private static void timeKaratsuba() {
        List<Integer> firstP1 = getFirstHalf(polynomial1.getCoefs());
        List<Integer> firstP2 = getFirstHalf(polynomial2.getCoefs());
        List<Integer> secondP1 = getSecondHalf(polynomial1.getCoefs());
        List<Integer> secondP2 = getSecondHalf(polynomial2.getCoefs());
        for (int i = 0; i < firstP1.size(); i++)
            for (int j = 0; j < firstP2.size(); j++)
                polynomial3.addCoefAtIndex(i + j, firstP1.get(i), firstP2.get(j));
        for (int i = 0; i < secondP1.size(); i++)
            for (int j = 0; j < secondP2.size(); j++) {
                polynomial3.addCoefAtIndex(i + j, secondP1.get(i), secondP2.get(j));
            }
    }

    private static List<Integer> getFirstHalf(List<Integer> pol) {
        Integer newSize = pol.size() / 2;
        List<Integer> halfPolynomial = new ArrayList<>(newSize);
        for (Integer i = 0; i < newSize; i++) {
            halfPolynomial.add(pol.get(i));
        }
        return halfPolynomial;
    }

    private static List<Integer> getSecondHalf(List<Integer> pol) {
        Integer originalSize = pol.size();
        Integer newSize = pol.size() / 2;
        List<Integer> halfPolynomial = new ArrayList<>(newSize);
        for (Integer i = newSize; i < originalSize; i++) {
            halfPolynomial.add(pol.get(i));
        }
        return halfPolynomial;
    }
}
