import java.util.List;

public class RunnableThread implements Runnable {

    private Thread t;
    private List<Integer> number1;
    private List<Integer> number2;
    private List<Integer> number3;
    private Integer remainder;
    private Integer sumPos;

    public RunnableThread(List<Integer> number1, List<Integer> number2, List<Integer> number3, Integer remainder, Integer sumPos) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.remainder = remainder;
        this.sumPos = sumPos;
    }

    @Override
    public void run() {
        synchronized (this) {
            Integer first = number1.get(sumPos);
            Integer second = number2.get(sumPos);
            Integer sum = first + second + remainder;
            if (sum >= 10) {
                sum = sum % 10;
                setRemainder(1);
            } else setRemainder(0);
            number3.set(sumPos, sum);
        }
    }

    void start() throws InterruptedException {
        if (t == null) {
            t = new Thread(this);
            synchronized (this) {
                t.start();
            }
            t.join();
        }
    }

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(Integer remainder) {
        this.remainder = remainder;
    }
}
