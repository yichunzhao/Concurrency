package synchronisation.semaphore;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * I build a pool having a pool having 10 threads, it is able to handle 10 cellphone runnable at the same time.
 * and then I use a semaphore to setup a checking point and allowing only 3 runnable that is able to connect to the
 * transmitter.
 * <p>
 * Then in each round there is 3 tasks that can be carried out.
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
    private static final int numOfCellPhone = 10;

    public static void main(String[] args) throws InterruptedException {
        //transmitter accept 3 cellphone's connections.
        Semaphore transmitter = new Semaphore(3);

        ExecutorService executorService = Executors.newFixedThreadPool(numOfCellPhone);

        IntStream.rangeClosed(0, numOfCellPhone).forEach(i -> {
            CellPhone cellPhone = new CellPhone(i, transmitter);
            executorService.submit(cellPhone);
        });

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        }
        log.info("xxx done");

    }
}
