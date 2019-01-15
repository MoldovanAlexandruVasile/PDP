public class RunnableThread implements Runnable {

    private Thread t;
    private final Filters filters;
    private Integer param1;
    private Integer param2;
    private Integer param3;
    private Integer param4;
    private String type;

    RunnableThread(Filters filters, Integer param1, Integer param2, Integer param3, Integer param4, String type) {
        this.filters = filters;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.type = type;
    }

    @Override
    public void run() {
        synchronized (filters) {
            if (type.equals("BLUE")) {
                filters.applyBlueFilter(param1, param2, param3, param4);
            } else if (type.equals("RED")) {
                filters.applyRedFilter(param1, param2, param3, param4);
            } else if (type.equals("GREEN")) {
                filters.applyGreenFilter(param1, param2, param3, param4);
            } else if (type.equals("NEGATIVE")) {
                filters.applyNegativeFilter(param1, param2, param3, param4);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() throws InterruptedException {
        if (t == null) {
            t = new Thread(this);
            synchronized (filters) {
                t.start();
            }
            t.join();
        }
    }
}
