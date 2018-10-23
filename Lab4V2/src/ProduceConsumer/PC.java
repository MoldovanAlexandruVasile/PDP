package ProduceConsumer;

import Domain.Matrix;

import java.util.ArrayList;
import java.util.List;

import static Domain.AppUtils.log;

public class PC {
    private List<String> list = new ArrayList<>();
    private Integer capacity = 1;
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix auxResult;
    private Matrix matrix3;
    private Matrix finalResult;
    private Integer fromLineToLine;
    private Integer startLine;
    private Integer col;
    private String name;

    public PC(String name, Matrix matrix1, Matrix matrix2, Matrix auxResult, Matrix matrix3, Matrix finalResult,
              Integer startLine, Integer fromLineToLine,
              Integer col) {
        this.name = name;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.auxResult = auxResult;
        this.matrix3 = matrix3;
        this.finalResult = finalResult;
        this.startLine = startLine;
        this.fromLineToLine = fromLineToLine;
        this.col = col;
    }

    public void runProduce() throws InterruptedException {
        Integer startLineLocal = startLine;
        synchronized (this) {
            while (list.size() == capacity)
                wait();
            if (this.col == -1) {
                while (startLineLocal < matrix1.getNumberLines()) {
                    multiplyProduceMatrix(matrix1, matrix2, startLineLocal, col);
                    list.add("1");
                    log("Produce: " + name);
                    startLineLocal += fromLineToLine;
                    notify();
                    Thread.sleep(100);
                }
            } else {
                multiplyProduceMatrix(matrix1, matrix2, startLine, col);
                list.add("1");
                log("Produce: " + name);
                notify();
                Thread.sleep(100);
            }
        }
    }

    public void runConsume() throws InterruptedException {
        Integer startLineLocal = startLine;
        synchronized (this) {
            while (list.size() == 0)
                wait();
            if (this.col == -1) {
                while (startLineLocal < auxResult.getNumberLines()) {
                    multiplyConsumeMatrix(auxResult, matrix3, startLineLocal, col);
                    log("Consume: " + name);
                    startLineLocal += fromLineToLine;
                    list.remove(0);
                    notify();
                    Thread.sleep(100);
                }
            } else {
                multiplyConsumeMatrix(auxResult, matrix3, startLine, col);
                log("Consume: " + name);
                list.remove(0);
                notify();
                Thread.sleep(100);
            }
        }
    }


    public synchronized void multiplyProduceMatrix(Matrix m1, Matrix m2, Integer line, Integer col) {
        synchronized (auxResult) {
            if (col == -1)
                for (int j = 0; j < m2.getNumberColumns(); j++)
                    for (int k = 0; k < m1.getNumberColumns(); k++)
                        auxResult.setElement(line, j, auxResult.getElement(line, j) + m1.getElement(line, k) * m2.getElement(k, j));
            else
                for (int j = 0; j < m2.getNumberColumns(); j++)
                    auxResult.setElement(line, j, auxResult.getElement(line, j) + m1.getElement(line, col) * m2.getElement(col, j));
        }
    }

    public synchronized void multiplyConsumeMatrix(Matrix m1, Matrix m2, Integer line, Integer col) {
        synchronized (finalResult) {
            if (col == -1)
                for (int j = 0; j < m2.getNumberColumns(); j++)
                    for (int k = 0; k < m1.getNumberColumns(); k++)
                        finalResult.setElement(line, j, finalResult.getElement(line, j) + m1.getElement(line, k) * m2.getElement(k, j));
            else
                for (int j = 0; j < m2.getNumberColumns(); j++)
                    finalResult.setElement(line, j, finalResult.getElement(line, j) + m1.getElement(line, col) * m2.getElement(col, j));
        }
    }
}