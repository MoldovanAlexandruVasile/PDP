package ProduceConsumer;

import Domain.Matrix;

import static Domain.AppUtils.log;

public class Producer implements Runnable {

    private Thread t;
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix auxResult;
    private Integer fromLineToLine;
    private Integer startLine;
    private Integer col;
    private String name;
    private Integer millisec;

    public Producer(Integer millisec, String name, Matrix matrix1, Matrix matrix2, Matrix auxResult, Integer startLine, Integer fromLineToLine,
                    Integer col) {
        this.name = name;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.auxResult = auxResult;
        this.startLine = startLine;
        this.fromLineToLine = fromLineToLine;
        this.col = col;
        this.millisec = millisec;
    }

    @Override
    public void run() {
        log(name);
        if (this.col == -1) {
            while (startLine < matrix1.getNumberLines()) {
                multiplyMatrix(matrix1, matrix2, startLine, col);
                startLine += fromLineToLine;
            }
        } else multiplyMatrix(matrix1, matrix2, startLine, col);
    }

    public void start() throws InterruptedException {
        if (t == null) {
            t = new Thread(this);
            synchronized (auxResult) {
                t.start();
            }
            t.join();
        }
    }

    public void multiplyMatrix(Matrix m1, Matrix m2, Integer line, Integer col) {
        synchronized (auxResult) {
            if (col == -1)
                for (int j = 0; j < m2.getNumberColumns(); j++) {
                    for (int k = 0; k < m1.getNumberColumns(); k++)
                        auxResult.setElement(line, j, auxResult.getElement(line, j) + m1.getElement(line, k) * m2.getElement(k, j));
                    auxResult.notify();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            else
                for (int j = 0; j < m2.getNumberColumns(); j++)
                    auxResult.setElement(line, j, auxResult.getElement(line, j) + m1.getElement(line, col) * m2.getElement(col, j));
        }
    }
}