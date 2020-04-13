package Threads;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class ShopperUpdated implements Runnable {
  static AtomicInteger garlicCount = new AtomicInteger(0);

  @Override
  public void run() {
    IntStream.range(0, 10_000_000).forEach(i -> garlicCount.incrementAndGet());
  }
}

public class AtomicVariableDemo {

  public static void main(String[] args) throws InterruptedException {
    Thread barron = new Thread(new ShopperUpdated());
    Thread olivia = new Thread(new ShopperUpdated());

    barron.start();
    olivia.start();

    // main thread waits both child to finish
    barron.join();
    olivia.join();

    System.out.println("current Garlic count :" + ShopperUpdated.garlicCount.get());
  }
}
