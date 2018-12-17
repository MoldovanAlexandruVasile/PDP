import mpi.MPI;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Polynomial polynomial1;
    private static Polynomial polynomial2;
    private static Polynomial polynomial3;

    public static void main(String[] args) throws Exception {
        initPolynoms();
        System.out.println("\n");
        printPolynom(polynomial1, "\tA(X) = ");
        printPolynom(polynomial2, "\tB(X) = ");

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int tag = 10, peer = (rank == 0) ? 1 : 0;
        if (rank == 0) {
            for (Integer no = 0; no < polynomial3.getDegree(); no++) {
                Object[] sendObjectArray = new Object[1];
                sendObjectArray[0] = no;
                MPI.COMM_WORLD.Send(sendObjectArray, 0, 1, MPI.OBJECT, peer, tag);
            }
        } else if (rank == 1) {
            for (Integer no = 0; no < polynomial3.getDegree(); no++) {
                Object[] recvObjectArray = new Object[1];
                MPI.COMM_WORLD.Recv(recvObjectArray, 0, 1, MPI.OBJECT, peer, tag);
                Integer getNo = (Integer) recvObjectArray[0];
                polynomial3.addCoefAtIndex(getNo, polynomial1.getCoefs().get(getNo), polynomial2.getCoefs().get(getNo));
            }
        }
        printPolynom(polynomial3, "\tC(X) = ");
        MPI.Finalize();
    }

    private static void initPolynoms() throws Exception {
        List<Integer> coefs = new ArrayList<>();
        List<Integer> coefs2 = new ArrayList<>();
        Integer degree = randomNumber(2, 10);
        Integer degree2 = randomNumber(2, 10);
        Boolean first = true;
        if (degree > degree2)
            first = false;
        Integer zeros = 0;
        Integer zeros2 = 0;
        if (first) {
            zeros = degree2 - degree;
            for (Integer no = 0; no < zeros; no++)
                coefs.add(0);
        } else {
            zeros2 = degree - degree2;
            for (Integer no = 0; no < zeros2; no++)
                coefs2.add(0);
        }

        for (Integer no = 0; no <= degree; no++)
            coefs.add(randomNumber(-5, 10));
        polynomial1 = new Polynomial(coefs, degree + zeros);
        Integer i = degree;

        for (Integer no = 0; no <= degree2; no++)
            coefs2.add(randomNumber(-5, 10));
        polynomial2 = new Polynomial(coefs2, degree2 + zeros2);
        Integer j = degree2;

        Integer size = i + j;
        coefs = new ArrayList<>();
        for (Integer no = 0; no <= size; no++)
            coefs.add(0);
        polynomial3 = new Polynomial(coefs, size);
    }

    private static void printPolynom(Polynomial polynomial, String string) {
        polynomial.printPolynom(string);
    }

    public static Integer randomNumber(Integer low, Integer up) {
        Integer lowBound = low;
        Integer highBound = up;
        Integer range = highBound - lowBound + lowBound;
        Integer rand = (int) (Math.random() * range) + lowBound;
        return rand;
    }

    private static void linearMultiplication() {
        for (int i = 0; i <= polynomial1.getDegree(); i++)
            for (int j = 0; j <= polynomial2.getDegree(); j++)
                polynomial3.addCoefAtIndex(i + j, polynomial1.getCoefs().get(i), polynomial2.getCoefs().get(j));
    }

    private static void linearMultiplicationKaratsuba() {
        List<Integer> firstP1 = new Karatsuba().getFirstHalf(polynomial1.getCoefs());
        List<Integer> firstP2 = new Karatsuba().getFirstHalf(polynomial2.getCoefs());
        List<Integer> secondP1 = new Karatsuba().getSecondHalf(polynomial1.getCoefs());
        List<Integer> secondP2 = new Karatsuba().getSecondHalf(polynomial2.getCoefs());
        for (int i = 0; i < firstP1.size(); i++)
            for (int j = 0; j < firstP2.size(); j++)
                polynomial3.addCoefAtIndex(i + j, firstP1.get(i), firstP2.get(j));
        for (int i = 0; i < secondP1.size(); i++)
            for (int j = 0; j < secondP2.size(); j++) {
                polynomial3.addCoefAtIndex(i + j, secondP1.get(i), secondP2.get(j));
            }
    }
}
