package Domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static Integer randomNumber() {
        Integer lowBound = 1;
        Integer highBound = 10;
        Integer range = highBound - lowBound + lowBound;
        Integer rand = (int) (Math.random() * range) + lowBound;
        return rand;
    }

    public static void separate() {
        System.out.println("\n======================================================================\n");
    }

    public static void log(String s) {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println("[ " + timeStamp + " ] " + s);
    }
}
