package ProduceConsumer;

import java.util.ArrayList;
import java.util.List;

public class PC {

    private final static Integer capacity = 1;
    private List<String> list = new ArrayList<>();
    private List<Integer> numbers = new ArrayList<>();
    private Integer k;
    private Integer currentPos;
    private Integer sum;
    private List<Integer> result = new ArrayList<>();

    public PC(List<Integer> numbers, Integer k, Integer currentPos, List<Integer> result) {
        this.numbers = numbers;
        this.k = k;
        this.currentPos = currentPos;
        this.result = result;
        this.sum = result.get(currentPos);
    }

    public void runProducer() throws InterruptedException {
        synchronized (this) {
            while (list.size() == capacity)
                wait();
            for (Integer pos = currentPos + 1; pos < currentPos + k; pos++)
                sum += numbers.get(pos);
            list.add("1");
            notify();
            Thread.sleep(100);
        }
    }

    public void runConsume() throws InterruptedException {
        synchronized (this) {
            while (list.size() == 0)
                wait();
            result.add(sum);
            list.remove(0);
            notify();
            Thread.sleep(100);
        }
    }
}