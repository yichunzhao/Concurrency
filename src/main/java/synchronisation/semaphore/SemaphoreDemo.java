package synchronisation.semaphore;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Semaphore is used to controlled amount of visitors to visit the limited resources.
 *
 * I build a pool having 10 threads, able to handle 10 cellphones at the same time.
 * and then I use a semaphore to setup a checking point and allowing only 10 runnable that is able to connect to the
 * transmitter.
 * <p>
 * Then in each round there is only 10 cellphones that can be connected.
 *
 */

@AllArgsConstructor
@Slf4j
class CellPhone implements Runnable {
    private int seq;
    private Semaphore semaphore;

    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(10);
            log.info("transmitter accept the cellphone request..." + seq);
        } catch (InterruptedException e) {
            log.error("Thread exception: ", e);
        } finally {
            log.info("done transmitting!!!");
            semaphore.release();
        }
    }
}

@Slf4j
public class SemaphoreDemo {
    private static final int numOfCellPhone = 100;
    private static final int numOfSemaphore = 10;

    public static void main(String[] args) throws InterruptedException {
        //transmitter accept 10 cellphone's connections.
        Semaphore transmitter = new Semaphore(numOfSemaphore);

        ExecutorService executorService = Executors.newFixedThreadPool(numOfCellPhone);

        IntStream.rangeClosed(0, numOfCellPhone).forEach(i -> {
            CellPhone cellPhone = new CellPhone(i, transmitter);
            executorService.submit(cellPhone);
        });

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        }
        log.info("at the end of main thread");

    }
}
