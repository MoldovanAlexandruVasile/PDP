package ProduceConsumer;

import Domain.Matrix;
import static Domain.AppUtils.log;

public class Consumer implements Runnable {

    private Thread t;
    private Matrix auxResult;
    private Matrix matrix3;
    private Matrix finalResult;
    private Integer fromLineToLine;
    private Integer startLine;
    private Integer col;
    private String name;
    private Integer millisec;

    public Consumer(Integer millisec, String name, Matrix auxResult, Matrix matrix3, Matrix finalResult, Integer startLine, Integer fromLineToLine,
                    Integer col) {
        this.name = name;
        this.auxResult = auxResult;
        this.matrix3 = matrix3;
        this.finalResult = finalResult;
        this.startLine = startLine;
        this.fromLineToLine = fromLineToLine;
        this.col = col;
        this.millisec = millisec;
    }

    @Override
    public void run() {
        log(name);
        if (this.col == -1)
            while (startLine < auxResult.getNumberLines()) {
                multiplyMatrix(auxResult, matrix3, startLine, col);
                startLine += fromLineToLine;
            }
        else multiplyMatrix(auxResult, matrix3, startLine, col);
    }

    public void start() throws InterruptedException {
        if (t == null) {
            t = new Thread(this);
            synchronized (finalResult) {
                t.start();
            }
            t.join();
        }
    }

    public void multiplyMatrix(Matrix m1, Matrix m2, Integer line, Integer col) {
        synchronized (finalResult) {
            if (col == -1)
                for (int j = 0; j < m2.getNumberColumns(); j++) {
                    for (int k = 0; k < m1.getNumberColumns(); k++)
                        finalResult.setElement(line, j, finalResult.getElement(line, j) + m1.getElement(line, k) * m2.getElement(k, j));
                    finalResult.notify();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            else
                for (int j = 0; j < m2.getNumberColumns(); j++)
                    finalResult.setElement(line, j, finalResult.getElement(line, j) + m1.getElement(line, col) * m2.getElement(col, j));
        }
    }
}