package ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author YNZ
 */

class MonitoringTask implements Runnable {

    @Override
    public void run() {

        System.out.println("do monitoring on the target ...");

        try {
            Thread.sleep(10);
            System.out.println(Thread.currentThread().getName() + " running ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ScheduledThreadPool {

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        //schedule a task to run after 10 sec.
        executor.schedule(MonitoringTask::new, 5, TimeUnit.SECONDS);

        //schedule a task  to run periodically every 16 sec.
        executor.scheduleAtFixedRate(new MonitoringTask(), 1, 10, TimeUnit.SECONDS);

        //schedule to repeat a task periodically, with a 15 delay as the pre-task completes
        executor.scheduleWithFixedDelay(MonitoringTask::new, 5, 10, TimeUnit.SECONDS);


        executor.awaitTermination(160, TimeUnit.SECONDS);
        executor.shutdown();

    }

}
