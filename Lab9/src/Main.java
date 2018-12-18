import mpi.MPI;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Polynomial polynomial1;
    private static Polynomial polynomial2;
    private static Polynomial polynomial3;

    public static void main(String[] args) throws Exception {
        initPolynoms();
        MPI.Init(args);
        System.out.println("\n");

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int tag = 10, peer = (rank == 0) ? 1 : 0;
        if (rank == 0) {
            printPolynom(polynomial1, "\tA(X) = ");
            printPolynom(polynomial2, "\tB(X) = ");
            Object[] sendObjectArray = new Object[1];
            for (Integer i = 0; i < polynomial2.getDegree(); i++) {
                sendObjectArray[0] = i;
                MPI.COMM_WORLD.Send(sendObjectArray, 0, 1, MPI.OBJECT, peer, tag);
            }
            printPolynom(polynomial3, "\nProcess number: " + size + "\tC(X) = ");
        } else if (rank == 1) {
            Object[] recvObjectArray = new Object[1];
            for (Integer i = 0; i < polynomial3.getDegree(); i++) {
                MPI.COMM_WORLD.Recv(recvObjectArray, 0, 1, MPI.OBJECT, peer, tag);
                Integer coef = (Integer) recvObjectArray[0];
                for (Integer j = 0; j < polynomial2.getCoefs().size(); j++) {
                    Integer pos = coef + j;
                    polynomial3.addCoefAtIndex(pos, polynomial1.getCoefs().get(coef), polynomial2.getCoefs().get(j));
//                    Karatsuba k = new Karatsuba(polynomial1.getCoefs(), polynomial2.getCoefs(), polynomial1.getCoefs().size());
//                    k.start();
//                    k.join();
//                    polynomial3.setCoefs(k.getResult());
                }
            }
        }
        MPI.Finalize();
    }

    private static void initPolynoms() throws Exception {
        List<Integer> coefs = new ArrayList<>();
        List<Integer> coefs2 = new ArrayList<>();
        Integer degree = 7;
        Integer degree2 = 7;

        for (Integer no = 0; no <= degree; no++)
            coefs.add(no);
        polynomial1 = new Polynomial(coefs, degree);
        Integer i = degree;

        for (Integer no = 0; no <= degree2; no++)
            coefs2.add(no);
        polynomial2 = new Polynomial(coefs2, degree2);
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
