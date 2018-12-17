package ProduceConsumer;

import java.util.ArrayList;
import java.util.List;

public class PC {
    private Integer[][] matrix;
    private Integer n, k, currentLine;
    private Integer sum = 0;
    private Integer last = 0;
    private Integer column = 0;

    private final static Integer capacity = 1;
    private List<String> list = new ArrayList<>();
    private List<Integer> summedElems = new ArrayList<>();
//    private List<Integer> numbers = new ArrayList<>();
//    private Integer k;
//    private Integer currentLine;
//    private Integer sum;
//    private List<Integer> result = new ArrayList<>();

//    public PC(List<Integer> numbers, Integer k, Integer currentLine, List<Integer> result) {
//        this.numbers = numbers;
//        this.k = k;
//        this.currentLine = currentLine;
//        this.result = result;
//        this.sum = result.get(currentLine);
//    }

    public PC(Integer[][] matrix, Integer n, Integer k, Integer currentLine, Integer column) {
        this.matrix = matrix;
        this.n = n;
        this.k = k;
        this.currentLine = currentLine;
        this.column = column;
    }

    public void runProducer() throws InterruptedException {
        synchronized (this) {
            while (list.size() == capacity)
                wait();
            if (column == 0)
                for (Integer i = currentLine; i < currentLine + k; i++) {
                    sum += matrix[i][i];
                    last = i;
                }
            else {
                List<Integer> elems = new ArrayList<>();
                Integer auxColumn = column - 1;
                for (Integer i = 0; i < n - column + 1; i++) {
                    elems.add(matrix[i][auxColumn]);
                    auxColumn++;
                }
                if (elems.size() <= k) {
                    for (Integer i = 0; i < elems.size() - k; i++) {
                        sum = 0;
                        for (Integer j = i; j < i + k - 1; j++) {
                            sum += elems.get(i);
                        }
                        summedElems.add(sum);
                    }
                } else {
                    for (Integer i = 0; i < elems.size() - k; i++)
                        summedElems.add(sum);
                }
            }
            list.add("1");
            notify();
            Thread.sleep(100);
        }
    }

    public Integer[][] getMatrix() {
        return matrix;
    }

    public void runConsume() throws InterruptedException {
        synchronized (this) {
            while (list.size() == 0)
                wait();
            if (column == 0)
                matrix[currentLine][last] = sum;
            else {
                for (Integer i = 0; i < summedElems.size(); i++) {
                    matrix[currentLine][column] = summedElems.get(i);
                    currentLine++;
                    column++;
                }
            }
            list.remove(0);
            notify();
            Thread.sleep(100);
        }
    }

//    public void runProducer() throws InterruptedException {
//        synchronized (this) {
//            while (list.size() == capacity)
//                wait();
//            for (Integer pos = currentLine + 1; pos < currentLine + k; pos++)
//                sum += numbers.get(pos);
//            list.add("1");
//            notify();
//            Thread.sleep(100);
//        }
//    }
//
//    public void runConsume() throws InterruptedException {
//        synchronized (this) {
//            while (list.size() == 0)
//                wait();
//            result.add(sum);
//            list.remove(0);
//            notify();
//            Thread.sleep(100);
//        }
//    }
}