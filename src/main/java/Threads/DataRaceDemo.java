package Threads;

import java.util.stream.IntStream;

/** Two shoppers adding items to a shared notepad. */
class Shopper implements Runnable {
  static int garlicCount;

  public Shopper() {}

  @Override
  public void run() {
    IntStream.range(0, 10).forEach(i -> garlicCount++);
  }
}

public class DataRaceDemo {
  public static void main(String[] args) throws InterruptedException {
    Thread barron = new Thread(new Shopper());
    Thread olivia = new Thread(new Shopper());

    barron.start();
    olivia.start();

    //main thread waits barron making his task done.
    barron.join();
    //main thread waits olivia making her task done.
    olivia.join();

    System.out.println("Garlic count: " + Shopper.garlicCount);
  }
}
