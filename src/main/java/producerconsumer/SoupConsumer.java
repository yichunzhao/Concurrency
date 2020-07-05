package producerconsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoupConsumer implements Runnable {
    private ServingBoard servingBoard;

    public SoupConsumer(ServingBoard servingBoard) {
        this.servingBoard = servingBoard;
    }

    @Override
    public void run() {
        while (true) {
            try {
                log.info("Pick up a soup bowl.");
                SoupBowl picked = servingBoard.pickUpOneBowlSoup();

                if (!picked.isEmpty()) {
                    picked.eatSoup();
                    log.info("Soup:#" + picked.getSeqNum() + " has been consumed.");
                } else {
                    log.info("having no more Soup supply!");
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
