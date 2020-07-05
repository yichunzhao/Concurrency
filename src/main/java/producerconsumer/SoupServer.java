package producerconsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoupServer implements Runnable {
    private static int counter = 0;
    private ServingBoard servingBoard;


    public SoupServer(ServingBoard servingBoard) {
        this.servingBoard = servingBoard;
    }

    @Override
    public void run() {

        while (true) {
            if (counter >= 100) break;
            SoupBowl soupBowl = new SoupBowl();
            soupBowl.addSoup();

            counter++;

            soupBowl.setSeqNum(counter);
            servingBoard.addSoapBowlToSeasonPipeLine(soupBowl);

            log.info("Put a soup bowl into the pipe line." + counter + "#");
        }

        //sending an empty soup bowl as a signal.
        SoupBowl emptyBowl = new SoupBowl();
        servingBoard.addSoapBowlToSeasonPipeLine(emptyBowl);

    }
}
