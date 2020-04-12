package Threads;

public class KitchenDemonDemo {

  private static Runnable kitchenCleaner =
      () -> {
        while (true) {
          System.out.println("Kitchen Cleaner is cleaning the kitchen.");

          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      };

  public static void main(String[] args) throws InterruptedException {
    // Main thread
    System.out.println("Main thread: " + Thread.currentThread().getName() + " start.");

    // Cleaner clean the kitchen
    Thread oliver = new Thread(kitchenCleaner);

    // all threads by default are non-demon threads.
    // when main thread is terminated, damon is also terminated.
    // however, for a non-damon thread, main thread is terminated, it may still in running.
    oliver.setDaemon(true);
    oliver.start();

    Thread.sleep(600);
    System.out.println("Barron is cooking ....");

    Thread.sleep(600);
    System.out.println("Barron is cooking ....");

    Thread.sleep(600);
    System.out.println("Barron is cooking ....");

    Thread.sleep(600);
    System.out.println("Barron is done ....");
  }
}
