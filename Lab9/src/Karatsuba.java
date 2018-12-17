import java.util.ArrayList;
import java.util.List;

public class Karatsuba extends Thread {
    private List<Integer> pol1;
    private List<Integer> pol2;
    private List<Integer> result;
    private Integer nrThreadsAllowed;

    Karatsuba(){}

    Karatsuba(List<Integer> pol1, List<Integer> pol2, Integer nrThreadsAllowed) {
        this.pol1 = pol1;
        this.pol2 = pol2;
        this.nrThreadsAllowed = nrThreadsAllowed;
        if (pol1.size() != pol2.size()) equalize();
        result = new ArrayList<>(pol1.size() + pol2.size());
    }

    private void equalize() {
        List<Integer> toComplete;
        List<Integer> other;
        if (pol1.size() > pol2.size()) {
            toComplete = pol2;
            other = pol1;
        } else {
            toComplete = pol1;
            other = pol2;
        }
        Integer start = toComplete.size();
        for (Integer i = start; i < other.size(); i++) {
            toComplete.add(0);
        }
    }

    public List<Integer> getResult() {
        return result;
    }

    public List<Integer> getFirstHalf(List<Integer> polynomial) {
        Integer newSize = polynomial.size() / 2;
        List<Integer> halfPolynomial = new ArrayList<>(newSize);
        for (Integer i = 0; i < newSize; i++) {
            halfPolynomial.add(polynomial.get(i));
        }
        return halfPolynomial;
    }

    public List<Integer> getSecondHalf(List<Integer> polynomial) {
        Integer originalSize = polynomial.size();
        Integer newSize = polynomial.size() / 2;
        List<Integer> halfPolynomial = new ArrayList<>(newSize);
        for (Integer i = newSize; i < originalSize; i++) {
            halfPolynomial.add(polynomial.get(i));
        }
        return halfPolynomial;
    }

    private List<Integer> splitAndSum(List<Integer> list) {
        Integer size = list.size() / 2;
        List<Integer> halfPolynomial = new ArrayList<>(size);
        for (Integer i = 0; i < size; i++) {
            halfPolynomial.add(list.get(i) + list.get(i + size));
        }
        return halfPolynomial;
    }


    private void addToResultFrom(List<Integer> list, Integer index, Integer multiplier) {
        for (Integer i = 0; i < list.size(); i++)
            result.set(i + index, result.get(i + index) + list.get(i) * multiplier);
    }

    private void startNewBranch(Karatsuba thread) {
        if (nrThreadsAllowed > 0) {
            thread.start();
            nrThreadsAllowed--;
        } else {
            thread.run();
        }
    }

    @Override
    public void run() {
        if (pol1.size() > 2 && pol2.size() > 2) {
            List<Integer> pol1firstHalf = getFirstHalf(pol1);
            List<Integer> pol1fSecondHalf = getSecondHalf(pol1);
            List<Integer> pol2firstHalf = getFirstHalf(pol2);
            List<Integer> pol2fSecondHalf = getSecondHalf(pol2);

            Karatsuba thread1 = new Karatsuba(
                    pol1firstHalf,
                    pol2firstHalf,
                    nrThreadsAllowed / 3 + nrThreadsAllowed % 3
            );
            startNewBranch(thread1);
            Karatsuba thread2 = new Karatsuba(
                    pol1fSecondHalf,
                    pol2fSecondHalf,
                    nrThreadsAllowed / 3
            );
            startNewBranch(thread2);
            Karatsuba thread3 = new Karatsuba(
                    splitAndSum(pol1),
                    splitAndSum(pol2),
                    nrThreadsAllowed / 3
            );
            startNewBranch(thread3);
            for (Integer i = 0; i < pol1.size() + pol2.size() - 1; i++) {
                result.add(0);
            }
            try {
                thread1.join();
                addToResultFrom(thread1.getResult(), 0, 1);
                thread2.join();
                addToResultFrom(thread2.getResult(), result.size() - thread2.getResult().size(), 1);
                thread3.join();
                Integer position = (result.size() + 1) / 4;
                addToResultFrom(thread3.getResult(), position, 1);
                addToResultFrom(thread1.getResult(), position, -1);
                addToResultFrom(thread2.getResult(), position, -1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                result.add(pol1.get(0) * pol2.get(0));
                result.add((pol1.get(0) + pol1.get(1)) * (pol2.get(0) + pol2.get(1)) - pol1.get(0) * pol2.get(0) - pol1.get(1) * pol2.get(1));
                result.add(pol1.get(1) * pol2.get(1));
            } catch (IndexOutOfBoundsException e){
            }
        }
    }
}