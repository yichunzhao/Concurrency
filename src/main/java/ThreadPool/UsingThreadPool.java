package ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/** @author YNZ */
class HeavyTask implements Runnable {

  private Integer id;

  public HeavyTask(Integer id) {
    this.id = id;
  }

  @Override
  public void run() {
    System.out.println("running task :" + id);
    try {
      Thread.sleep(1200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("task " + id.toString() + " is done");
  }
}

public class UsingThreadPool {

  public static void main(String... args) {

    ExecutorService executorService = Executors.newFixedThreadPool(6);

    IntStream.rangeClosed(0, 5).forEach(i -> executorService.submit(new HeavyTask(i)));

    System.out.println("tasks are submitted");

    executorService.shutdown();

    try {
      executorService.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
