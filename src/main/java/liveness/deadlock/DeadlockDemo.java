package liveness.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * demo of creating a deadlock
 */
public class DeadlockDemo {
  static class Philosopher implements Runnable {

    private static int sushiAmount = 20_000;

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

        // simulating the time cost to pick up one sushi.
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        // if there is still one on the plate, then picking up. Otherwise, do nothing.
        if (sushiAmount > 0) sushiAmount--;

        System.out.println(this.name + " is taking one Sushi. remaining: " + sushiAmount);

        chopStick2.unlock();
        chopStick1.unlock();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Lock chopStickA = new ReentrantLock();
    Lock chopStickB = new ReentrantLock();

    // there are two philosophers want to eat sushi.
    Thread barron = new Thread(new Philosopher(chopStickA, chopStickB, "Barron"));
    Thread olivia = new Thread(new Philosopher(chopStickB, chopStickA, "Olivia"));

    barron.start();
    olivia.start();

    barron.join();
    olivia.join();

    System.out.println("sushi left: " + Philosopher.getSushiAmount());
  }
}
