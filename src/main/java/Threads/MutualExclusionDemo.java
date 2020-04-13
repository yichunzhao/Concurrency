package Threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

class ShopperImproved implements Runnable {
  static int garlicCount = 0;
  static Lock pencil = new ReentrantLock();

  @Override
  public void run() {

    IntStream.range(0, 5)
        .forEach(
            i -> {
              // put a protection exactly on the critical section.
              pencil.lock();
              garlicCount++; // it may be polluted.
              pencil.unlock();
              try {
                // simulating  a heavy job here, but it is not a critical section
                // at this moment, the locker can be released, then anther thread my access the
                // critical section.
                System.out.println("busy thinking : " + Thread.currentThread().getName());
                Thread.sleep(500);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }
}

public class MutualExclusionDemo {

  public static void main(String[] args) throws InterruptedException {
    Thread barron = new Thread(new ShopperImproved());
    Thread olivia = new Thread(new ShopperImproved());

    barron.setName("Barron-Thread-");
    olivia.setName("Olivia-Thread-");

    barron.start();
    olivia.start();

    barron.join();
    olivia.join();

    System.out.println("Garlic count: " + ShopperImproved.garlicCount);
  }
}
