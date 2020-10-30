package Threads;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * using thread.interrupt() to stop a running thread.
 * <p>
 * Thread.sleep()
 * Thread.currentThread()
 */
@Slf4j
public class StopAThreadByInterrupt {

    private static final Random randomNumberGen = new Random();

    public static void main(String[] args) {

        Thread task =
                new Thread(
                        () -> {
                            while (!Thread.currentThread().isInterrupted()) {
                                // do something here
                                log.info(" " + randomNumberGen.nextInt());
                            }
                        });

        // start the thread
        task.start();

        // Sleep for a while
        try {
            log.info("sleep for a while");
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Java thread can only be stopped by an interrupt
        // it cannot be killed.
        task.interrupt();
    }
}
