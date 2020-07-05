package producerconsumer;


public class SoupServingProducerConsumerDemo {
    public static void main(String[] args) {

        ServingBoard servingBoard = new ServingBoard();
        SoupServer soupServer = new SoupServer(servingBoard);
        SoupConsumer soupConsumer = new SoupConsumer(servingBoard);

        Thread soupConsumerThread = new Thread(soupConsumer);
        Thread soupProducerThread = new Thread(soupServer);

        soupConsumerThread.start();
        soupProducerThread.start();


    }
}
