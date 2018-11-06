
public class RunnableThread implements Runnable {

    private Thread t;
    private Polynomial p1;
    private Polynomial p2;
    private Polynomial p3;
    private Integer coeff;

    RunnableThread(Polynomial p1, Polynomial p2, Polynomial p3, Integer coeff) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.coeff = coeff;
    }

    @Override
    public void run() {
        synchronized (p3) {
            multiply(p1, p2, coeff);
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void multiply(Polynomial p1, Polynomial p2, Integer coef) {
        for (Integer j = 0; j < p2.getCoefs().size(); j++) {
            Integer pos = coef + j;
            p3.addCoefAtIndex(pos,p1.getCoefs().get(coef), p2.getCoefs().get(j));
        }
    }

    public void start() throws InterruptedException {
        if (t == null) {
            t = new Thread(this);
            synchronized (p3) {
                t.start();
            }
            t.join();
        }
    }
}
