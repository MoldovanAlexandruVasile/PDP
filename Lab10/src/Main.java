import mpi.MPI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("\n");

        File fileImage = new File("input.jpg");
        BufferedImage img = ImageIO.read(fileImage);
        Filters filters = new Filters(img);
        Integer width = img.getWidth();
        Integer height = img.getHeight();

        MPI.Init(args);
        Integer rank = MPI.COMM_WORLD.Rank();
        Integer tag = 10, peer = (rank == 0) ? 1 : 0;
        if (rank == 0) {
            sendMPJBlueFilterCoordinates(width, height, peer, tag);
            sendMPJGreenFilterCoordinates(width, height, peer, tag);
            sendMPJNegativeFilterCoordinates(width, height, peer, tag);
            sendMPJRedFilterCoordinates(width, height, peer, tag);
        } else if (rank == 1) {
            recvMPJBlueFilterCoordinates(filters, peer, tag);
            recvMPJGreenFilterCoordinates(filters, peer, tag);
            recvMPJNegativeFilterCoordinates(filters, peer, tag);
            recvMPJRedFilterCoordinates(filters, peer, tag);
        }

        fileImage = new File("output.jpg");
        ImageIO.write(filters.getImg(), "jpg", fileImage);
        MPI.Finalize();
    }

    private static void sendMPJBlueFilterCoordinates(Integer width, Integer height, Integer peer, Integer tag) {
        Object[] sendBlueFilterArray = new Object[4];
        sendBlueFilterArray[0] = 0;
        sendBlueFilterArray[1] = 0;
        sendBlueFilterArray[2] = height / 2;
        sendBlueFilterArray[3] = width / 2;
        MPI.COMM_WORLD.Send(sendBlueFilterArray, 0, 4, MPI.OBJECT, peer, tag);
    }

    private static void recvMPJBlueFilterCoordinates(Filters filters, Integer peer, Integer tag) {
        Object[] recvBlueFilterArray = new Object[4];
        MPI.COMM_WORLD.Recv(recvBlueFilterArray, 0, 4, MPI.OBJECT, peer, tag);
        Integer param1 = (Integer) recvBlueFilterArray[0];
        Integer param2 = (Integer) recvBlueFilterArray[1];
        Integer param3 = (Integer) recvBlueFilterArray[2];
        Integer param4 = (Integer) recvBlueFilterArray[3];
        filters.applyBlueFilter(param1, param2, param3, param4);
    }

    private static void sendMPJGreenFilterCoordinates(Integer width, Integer height, Integer peer, Integer tag) {
        Object[] sendBlueFilterArray = new Object[4];
        sendBlueFilterArray[0] = 0;
        sendBlueFilterArray[1] = width / 2;
        sendBlueFilterArray[2] = height / 2;
        sendBlueFilterArray[3] = width;
        MPI.COMM_WORLD.Send(sendBlueFilterArray, 0, 4, MPI.OBJECT, peer, tag);
    }

    private static void recvMPJGreenFilterCoordinates(Filters filters, Integer peer, Integer tag) {
        Object[] recvGreenFilterArray = new Object[4];
        MPI.COMM_WORLD.Recv(recvGreenFilterArray, 0, 4, MPI.OBJECT, peer, tag);
        Integer param1 = (Integer) recvGreenFilterArray[0];
        Integer param2 = (Integer) recvGreenFilterArray[1];
        Integer param3 = (Integer) recvGreenFilterArray[2];
        Integer param4 = (Integer) recvGreenFilterArray[3];
        filters.applyGreenFilter(param1, param2, param3, param4);
    }

    private static void sendMPJNegativeFilterCoordinates(Integer width, Integer height, Integer peer, Integer tag) {
        Object[] sendNegativeFilterArray = new Object[4];
        sendNegativeFilterArray[0] = height / 2;
        sendNegativeFilterArray[1] = 0;
        sendNegativeFilterArray[2] = height;
        sendNegativeFilterArray[3] = width / 2;
        MPI.COMM_WORLD.Send(sendNegativeFilterArray, 0, 4, MPI.OBJECT, peer, tag);
    }

    private static void recvMPJNegativeFilterCoordinates(Filters filters, Integer peer, Integer tag) {
        Object[] recvNegativeFilterArray = new Object[4];
        MPI.COMM_WORLD.Recv(recvNegativeFilterArray, 0, 4, MPI.OBJECT, peer, tag);
        Integer param1 = (Integer) recvNegativeFilterArray[0];
        Integer param2 = (Integer) recvNegativeFilterArray[1];
        Integer param3 = (Integer) recvNegativeFilterArray[2];
        Integer param4 = (Integer) recvNegativeFilterArray[3];
        filters.applyNegativeFilter(param1, param2, param3, param4);
    }

    private static void sendMPJRedFilterCoordinates(Integer width, Integer height, Integer peer, Integer tag) {
        Object[] sendRedFilterArray = new Object[4];
        sendRedFilterArray[0] = height / 2;
        sendRedFilterArray[1] = width / 2;
        sendRedFilterArray[2] = height;
        sendRedFilterArray[3] = width;
        MPI.COMM_WORLD.Send(sendRedFilterArray, 0, 4, MPI.OBJECT, peer, tag);
    }

    private static void recvMPJRedFilterCoordinates(Filters filters, Integer peer, Integer tag) {
        Object[] recvRedFilterArray = new Object[4];
        MPI.COMM_WORLD.Recv(recvRedFilterArray, 0, 4, MPI.OBJECT, peer, tag);
        Integer param1 = (Integer) recvRedFilterArray[0];
        Integer param2 = (Integer) recvRedFilterArray[1];
        Integer param3 = (Integer) recvRedFilterArray[2];
        Integer param4 = (Integer) recvRedFilterArray[3];
        filters.applyRedFilter(param1, param2, param3, param4);
    }
}
