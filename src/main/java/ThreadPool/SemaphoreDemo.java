package ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreDemo {

  public static void main(String[] args) throws InterruptedException {

    // only 3 thread can acquire permits at the same time.
    // restrict and limit to use limited resources
    Semaphore semaphore = new Semaphore(3);

    ExecutorService executorService = Executors.newFixedThreadPool(50);

    IntStream.rangeClosed(0, 1000).forEach(i -> executorService.execute(new Task(i, semaphore)));

    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.MINUTES);
  }

  public static class Task implements Runnable {
    private int i;
    private Semaphore semaphore;

    public Task(int i, Semaphore semaphore) {
      this.i = i;
      this.semaphore = semaphore;
    }

    @Override
    public void run() {

      try {
        semaphore.acquire();
        System.out.print(i + " ");

        semaphore.release();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
