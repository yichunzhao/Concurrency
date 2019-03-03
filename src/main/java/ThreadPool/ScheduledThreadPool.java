package ThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author YNZ
 */

class MonitoringTask implements Runnable {

    @Override
    public void run() {

        System.out.println("do monitoring on the target ...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}

public class ScheduledThreadPool {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new MonitoringTask(), 1, 5, TimeUnit.SECONDS);

        countDownLatch.await(6, TimeUnit.SECONDS);
        future.cancel(true);

    }

}
