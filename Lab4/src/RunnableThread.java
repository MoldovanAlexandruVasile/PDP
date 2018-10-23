import Domain.Matrix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class RunnableThread implements Runnable {

    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix matrix3;
    private Integer fromLineToLine;
    private Integer startLine;
    private Integer col;
    private String name;
    private Integer millisec;

    RunnableThread(Integer millisec, String name, Matrix matrix1, Matrix matrix2, Matrix matrix3, Integer startLine, Integer fromLineToLine,
                   Integer col) {
        this.name = name;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.matrix3 = matrix3;
        this.startLine = startLine;
        this.fromLineToLine = fromLineToLine;
        this.col = col;
        this.millisec = millisec;
    }

    @Override
    public void run() {
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        Date start = new Date();
        long startMS = System.currentTimeMillis();
        System.out.println("Start time for task name - " + name + " = " + ft.format(start));
        synchronized (matrix3) {
            if (this.col == -1)
                while (startLine < matrix1.getNumberLines()) {
                    multiplyMatrix(matrix1, matrix2, startLine, col);
                    startLine += fromLineToLine;
                }
            else multiplyMatrix(matrix1, matrix2, startLine, col);
        }
        Date finish = new Date();
        long finishMS = System.currentTimeMillis() - startMS;
        System.out.println("Completed - " + name + " -> " + ft.format(finish));
        System.out.println("Time took - " + name + " -> " + finishMS);
    }

    public String getName() {
        return name;
    }

    public synchronized void multiplyMatrix(Matrix m1, Matrix m2, Integer line, Integer col) {
        if (col == -1)
            for (int j = 0; j < m2.getNumberColumns(); j++)
                for (int k = 0; k < m1.getNumberColumns(); k++)
                    matrix3.setElement(line, j, matrix3.getElement(line, j) + m1.getElement(line, k) * m2.getElement(k, j));
        else
            for (int j = 0; j < m2.getNumberColumns(); j++)
                matrix3.setElement(line, j, matrix3.getElement(line, j) + m1.getElement(line, col) * m2.getElement(col, j));
    }
}
