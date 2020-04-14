package Lock;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Acquiring lock in a Non-blocking way, rather than a blocking way
 *
 * <p>without using try lock, time case 6409 ms when using try lock, time cost 2410 ms.
 */
class Shopper implements Runnable {

  private static Lock lock = new ReentrantLock(); // fairness default false

  private static int itemsOnNotepad = 0;

  private int itemsToAdd = 0; // items this shopper is waiting to add.

  private String name;

  public Shopper(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public void run() {
    while (itemsOnNotepad <= 20) {
      if (itemsToAdd > 0 && lock.tryLock()) { // add items to shared notepad
        // lock.lock();
        itemsOnNotepad += itemsToAdd;
        System.out.println(this.getName() + " added " + itemsToAdd + "items to notepad.");
        itemsToAdd = 0;
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      } else { // look for other things to buy
        try {
          Thread.sleep(100); // time spent for searching.
          itemsToAdd++;
          System.out.println(this.getName() + " found something to buy.");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}

public class TryLockDemo {

  private static Runnable shopper = () -> {};

  public static void main(String[] args) throws InterruptedException {

    Instant start = Instant.now();
    Thread barron = new Thread(new Shopper("Barron"));
    Thread olivia = new Thread(new Shopper("Olivia"));

    barron.start();
    olivia.start();

    barron.join();
    olivia.join();

    Instant end = Instant.now();
    System.out.println("time cost: " + Duration.between(start, end).toMillis());
  }
}
