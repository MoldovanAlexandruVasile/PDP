package Domain;

public class AppUtils {

    public static Integer randomNumber() {
        Integer lowBound = 1;
        Integer highBound = 10;
        Integer range = highBound - lowBound + lowBound;
        Integer rand = (int) (Math.random() * range) + lowBound;
        return rand;
    }
}
