package liveness;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * After a thread acquired lock, it cannot release it due to exceptional reasons. It causes all the
 * rest threads blocked. The following code aims to simulate this scenario.
 *
 * <p>Adding try catch block and always unlock in the finally block.
 */
public class AbandonedLockDemo {

  private static class Philosopher implements Runnable {

    private static int sushiAmount = 60;

    private final Lock chopStick1;

    private final Lock chopStick2;

    private final String name;

    public Philosopher(Lock chopStick1, Lock chopStick2, String name) {
      this.chopStick1 = chopStick1;
      this.chopStick2 = chopStick2;
      this.name = name;
    }

    public static int getSushiAmount() {
      return sushiAmount;
    }

    @Override
    public void run() {
      while (sushiAmount > 0) {
        // put protection on the critical section.
        chopStick1.lock();
        chopStick2.lock();

        // if there is still one on the plate, then picking up. Otherwise, do nothing.
        try {
          if (sushiAmount > 0) sushiAmount--;

          System.out.println(this.name + " is taking one Sushi. remaining: " + sushiAmount);

          if (sushiAmount == 10) throw new IllegalStateException("a simulated exception happens");
        } catch (IllegalStateException e) {
          e.printStackTrace();
        } finally {
          chopStick2.unlock();
          chopStick1.unlock();
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Lock chopStickA = new ReentrantLock();
    Lock chopStickB = new ReentrantLock();

    // there are two philosophers want to eat sushi.
    Thread barron = new Thread(new Philosopher(chopStickA, chopStickB, "Barron"));
    Thread olivia = new Thread(new Philosopher(chopStickA, chopStickB, "Olivia"));
    barron.setName("Thread-barron");
    olivia.setName("Thread-olivia");

    barron.start();
    olivia.start();

    barron.join();
    olivia.join();

    System.out.println("sushi left: " + Philosopher.getSushiAmount());
  }
}
