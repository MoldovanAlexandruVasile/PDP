import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        File fileImage = new File("input.jpg");
        BufferedImage img = ImageIO.read(fileImage);
        Filters filters = new Filters(img);
        Integer width = img.getWidth();
        Integer height = img.getHeight();

        List<RunnableThread> threads = new ArrayList<>();
        for (Integer i = 0; i < 4; i++) {
            RunnableThread runnableThread;
            if (i.equals(0))
                runnableThread = new RunnableThread(filters, 0, 0, height / 2, width / 2, "BLUE");
            else if (i.equals(1))
                runnableThread = new RunnableThread(filters, height / 2, 0, height, width / 2, "NEGATIVE");
            else if (i.equals(2))
                runnableThread = new RunnableThread(filters, height / 2, width / 2, height, width, "RED");
            else
                runnableThread = new RunnableThread(filters, 0, width / 2, height / 2, width, "GREEN");
            threads.add(runnableThread);
        }
        for (RunnableThread runnableThread : threads)
            runnableThread.start();
        fileImage = new File("output.jpg");
        ImageIO.write(filters.getImg(), "jpg", fileImage);
    }
}
