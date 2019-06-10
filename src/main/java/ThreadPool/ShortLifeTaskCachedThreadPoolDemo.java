package ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ShortLifeTaskCachedThreadPoolDemo {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService service = Executors.newCachedThreadPool();
    IntStream.rangeClosed(0, 100).forEach(i -> service.execute(new LightTask()));
    service.awaitTermination(10, TimeUnit.SECONDS);
    service.shutdown();
  }

  private static class LightTask implements Runnable {

    @Override
    public void run() {

      System.out.println("current thread: " + Thread.currentThread().getName());
    }
  }
}
