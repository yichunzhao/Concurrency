package synchronisation.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Serving board
 */
public class ServingBoard {

    private BlockingQueue<SoupBowl> soupReadyLine = new ArrayBlockingQueue<>(5);

    public void addSoapBowlToSeasonPipeLine(SoupBowl soupBowl) {
        soupReadyLine.add(soupBowl);
    }

    public SoupBowl pickUpOneBowlSoup() throws InterruptedException {
        return soupReadyLine.take();
    }

}
