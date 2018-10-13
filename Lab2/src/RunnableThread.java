import Domain.Matrix;

public class RunnableThread implements Runnable {

    private Thread t;
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix matrix3;
    private Integer fromLineToLine;
    private Integer startLine;
    private String operation;
    private Integer col;

    RunnableThread(Matrix matrix1, Matrix matrix2, Matrix matrix3, Integer startLine, Integer fromLineToLine, String operation,
                   Integer col) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.matrix3 = matrix3;
        this.startLine = startLine;
        this.fromLineToLine = fromLineToLine;
        this.operation = operation;
        this.col = col;
    }

    @Override
    public void run() {
        if (operation.equals("+")) {
            synchronized (matrix3) {
                if (this.col == -1)
                    while (startLine < matrix1.getNumberLines()) {
                        addMatrix(matrix1, matrix2, startLine, col);
                        startLine += fromLineToLine;
                    }
                else addMatrix(matrix1, matrix2, startLine, col);
            }
        } else if (operation.equals("-")) {
            synchronized (matrix3) {
                if (this.col == -1)
                    while (startLine < matrix1.getNumberLines()) {
                        differenceMatrix(matrix1, matrix2, startLine, col);
                        startLine += fromLineToLine;
                    }
                else differenceMatrix(matrix1, matrix2, startLine, col);
            }
        } else if (operation.equals("*")) {
            synchronized (matrix3) {
                if (this.col == -1)
                    while (startLine < matrix1.getNumberLines()) {
                        multiplyMatrix(matrix1, matrix2, startLine, col);
                        startLine += fromLineToLine;
                    }
                else multiplyMatrix(matrix1, matrix2, startLine, col);
            }
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() throws InterruptedException {
        if (t == null) {
            t = new Thread(this);
            synchronized (matrix3) {
                t.start();
            }
            t.join();
        }
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public synchronized void addMatrix(Matrix m1, Matrix m2, Integer line, Integer col) {
        if (col == -1)
            for (Integer c = 0; c < m1.getNumberColumns(); c++)
                matrix3.setElement(line, c, m1.getElement(line, c) + m2.getElement(line, c));
        else {
            matrix3.setElement(line, col, m1.getElement(line, col) + m2.getElement(line, col));
        }
    }

    public synchronized void differenceMatrix(Matrix m1, Matrix m2, Integer line, Integer col) {
        if (col == -1)
            for (Integer c = 0; c < m1.getNumberColumns(); c++)
                matrix3.setElement(line, c, m1.getElement(line, c) - m2.getElement(line, c));
        else matrix3.setElement(line, col, m1.getElement(line, col) - m2.getElement(line, col));
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
