package ProduceConsumer;

import java.util.ArrayList;
import java.util.List;

public class PC {

    private final static Integer capacity = 1;
    private List<String> list = new ArrayList<>();
    private List<Integer> numbers = new ArrayList<>();
    private Integer position;
    private Integer sum;
    private List<Integer> result = new ArrayList<>();

    public PC(List<Integer> numbers, Integer position, List<Integer> result) {
        this.numbers = numbers;
        this.position = position;
        this.result = result;
        this.sum = 0;
    }

    public void runProducer() throws InterruptedException {
        synchronized (this) {
            while (list.size() == capacity)
                wait();
            for (Integer pos = 0; pos <= position; pos++)
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