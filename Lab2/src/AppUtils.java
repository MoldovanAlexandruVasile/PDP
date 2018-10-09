package Domain;

public class AppUtils {

    public static Integer randomNumber() {
        Integer lowBound = 1;
        Integer highBound = 5;
        Integer range = highBound - lowBound + lowBound;
        Integer rand = (int) (Math.random() * range) + lowBound;
        return rand;
    }

    public static void separate() {
        System.out.println("\n======================================================================\n");
    }

    public static Matrix addMatrix(Matrix m1, Matrix m2) {
        Matrix newMatrix = new Matrix(m1.getNumberLines(), m1.getNumberColumns(), false);
        for (Integer l = 0; l < m1.getNumberLines(); l++)
            for (Integer c = 0; c < m1.getNumberColumns(); c++)
                newMatrix.setElement(l, c, m1.getElement(l, c) + m2.getElement(l, c));
        return newMatrix;
    }

    public static Matrix differenceMatrix(Matrix m1, Matrix m2) {
        Matrix newMatrix = new Matrix(m1.getNumberLines(), m1.getNumberColumns(), false);
        for (Integer l = 0; l < m1.getNumberLines(); l++)
            for (Integer c = 0; c < m1.getNumberColumns(); c++)
                newMatrix.setElement(l, c, m1.getElement(l, c) - m2.getElement(l, c));
        return newMatrix;
    }

    public static Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        Matrix newMatrix = new Matrix(m1.getNumberLines(), m2.getNumberColumns(), false);
        for (int i = 0; i < m1.getNumberLines(); i++) {
            for (int j = 0; j < m2.getNumberColumns(); j++) {
                for (int k = 0; k < m1.getNumberColumns(); k++)
                    newMatrix.setElement(i, j, newMatrix.getElement(i,j) + m1.getElement(i, k) * m2.getElement(k, j));
            }
        }
        return newMatrix;
    }
}
