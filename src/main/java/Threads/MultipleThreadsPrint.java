package Threads;

import java.util.stream.IntStream;

/** @author YNZ */
class PrinterA implements Runnable {
  @Override
  public void run() {
    IntStream.rangeClosed(0, 10).forEach(i -> System.out.print("A: " + i + " "));
  }
}

class PrinterB implements Runnable {
  @Override
  public void run() {
    IntStream.rangeClosed(0, 10).forEach(i -> System.out.print("B: " + i + " "));
  }
}

public class MultipleThreadsPrint {

  public static void main(String... args) {
    Thread thA = new Thread(new PrinterA());
    Thread thB = new Thread(new PrinterB());
    thA.start();
    thB.start();
  }
}
