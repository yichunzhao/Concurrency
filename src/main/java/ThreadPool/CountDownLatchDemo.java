package ThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {

  public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(3);

    ExecutorService service = Executors.newFixedThreadPool(3);

    service.execute(new DependentService(countDownLatch));
    service.execute(new DependentService(countDownLatch));
    service.execute(new DependentService(countDownLatch));

    countDownLatch.await();

    System.out.println("3 tasks have been done...");

    service.shutdown();
  }

  private static class DependentService implements Runnable {

    private CountDownLatch latch;

    public DependentService(CountDownLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println(Thread.currentThread().getName() + " is done...");

      latch.countDown();
    }
  }
}
