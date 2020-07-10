package Threads;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@AllArgsConstructor
class Task implements Runnable {
    private String taskClass;

    @Override
    public void run() {

        //log.info(taskClass + " current thread name: " + Thread.currentThread().getName());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error("Thread exception: " + e);
        }
    }
}

/**
 * ???
 */
@Slf4j
public class ComparingThreadPoolWithThreads {
    private static final int TASK_NUM = 100;

    public static void main(String[] args) throws InterruptedException {
        int coreNum = Runtime.getRuntime().availableProcessors();
        log.info("Number of CPU cores: " + coreNum);

        log.info("Implementing 100 tasks in a thread pool.");

        ExecutorService executorService = Executors.newFixedThreadPool(coreNum);

        Instant start = Instant.now();

        IntStream.rangeClosed(0, TASK_NUM).forEach(t -> {

            executorService.submit(new Task("Pool"));
        });


        executorService.shutdown();

        Instant end = null;

        while (executorService.isTerminated()) ;
        {
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
            end = Instant.now();
            log.info("Thread pool used time: " + Duration.between(start, end).toMillis());
        }

        log.info("+++++++++Implementing 100 tasks in threads+++++++");

        List<Thread> threads = new ArrayList<>(50);

        start = Instant.now();

        IntStream.rangeClosed(0, TASK_NUM).forEach(t -> {
            Thread th = new Thread(new Task("Threads"));
            threads.add(th);
        });

        threads.forEach(thread -> {
            thread.start();
        });

        //wait all thread accomplish their tasks.

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).join();
            if (i == (threads.size() - 1)) {
                end = Instant.now();
                log.info("Threads used time: " + Duration.between(start, end).toMillis());
            }


        }

    }
}
