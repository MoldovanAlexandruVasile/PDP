import Domain.Matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static Domain.AppUtils.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.print("Operation you want to perform: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String operation = reader.readLine();
        Integer threads = 0;
        Integer lines = randomNumber();
        Integer columns = randomNumber();
        Matrix matrix1 = null;
        Matrix matrix2 = null;
        if (operation.equals("+") || operation.equals("-")) {
            matrix1 = new Matrix(lines, columns, true);
            matrix2 = new Matrix(lines, columns, true);
            threads = lines * columns;
        } else if (operation.equals("*")) {
            matrix1 = new Matrix(lines, columns, true);
            matrix2 = new Matrix(columns, lines, true);
            threads = lines * lines;
        } else System.out.println("Error: invalid operation sign.");
        System.out.println("\nNumber of possible threads: " + threads);
        System.out.println("Matrix 1:\n");
        matrix1.printMatrix();
        separate();
        System.out.println("Matrix 2:\n");
        matrix2.printMatrix();
        separate();
        if (operation.equals("+")) {
            System.out.println("Adding:");
            Matrix matrix3 = addMatrix(matrix1, matrix2);
            matrix3.printMatrix();
        } else if (operation.equals("-")) {
            System.out.println("Subtract:");
            Matrix matrix3 = differenceMatrix(matrix1, matrix2);
            matrix3.printMatrix();
        }
        else if (operation.equals("*")) {
            System.out.println("Multiply:");
            Matrix matrix3 = multiplyMatrix(matrix1, matrix2);
            matrix3.printMatrix();
        }
    }
}
