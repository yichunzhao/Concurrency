package producerconsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoupServingProducerConsumerDemo {
    public static void main(String[] args) {

        ServingBoard servingBoard = new ServingBoard();
        SoupServer soupServer = new SoupServer(servingBoard);
        SoupConsumer soupConsumer = new SoupConsumer(servingBoard);

        SoupConsumer anotherSoupConsumer = new SoupConsumer(servingBoard);

        Thread soupConsumerThread = new Thread(soupConsumer);
        Thread soupProducerThread = new Thread(soupServer);
        Thread anotherSoupConsumerThread = new Thread(anotherSoupConsumer);

        soupConsumerThread.start();
        anotherSoupConsumerThread.start();
        soupProducerThread.start();

        try {
            soupConsumerThread.join();
            soupProducerThread.join();
            anotherSoupConsumerThread.join();
        } catch (InterruptedException e) {
            log.error("Thread error:", e);
        }

        log.info("+++++++++++++ End +++++++++++++++");

    }
}
