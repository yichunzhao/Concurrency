package Threads;

/** @author YNZ */
public class PrintThreadsInSequence {
  public static int flag;
  static Object lock = new Object();

  static {
    flag = 1;
  }

  public static void main(String... args) {
    // print A
    new Thread(
            () -> {
              synchronized (lock) {
                while (flag != 1) {
                  try {
                    lock.wait();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
                System.out.print("A");

                flag = 2;
                lock.notify();
              }
            })
        .start();

    // print B
    new Thread(
            () -> {
              synchronized (lock) {
                while (flag != 2) {
                  try {
                    lock.wait();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
                System.out.print("B");

                flag = 3;
                lock.notify();
              }
            })
        .start();

    // print C
    new Thread(
            () -> {
              synchronized (lock) {
                while (flag != 3) {
                  try {
                    lock.wait();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }

                System.out.print("C");
                flag = 4;
                lock.notify();
              }
            })
        .start();

    // print D
    new Thread(
            () -> {
              synchronized (lock) {
                // waiting for flag = 4
                while (flag != 4) {
                  try {
                    lock.wait();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
                System.out.println("D");
                flag = 5;
                lock.notify();
              }
            })
        .start();
  }
}
