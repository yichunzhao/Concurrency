package Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
  // a mutex, avoid deadlock.
  private static ReentrantLock lock = new ReentrantLock();

  public static void main(String[] args) {

    Thread th1 = new Thread(() -> accessResource());
    Thread th2 = new Thread(() -> accessResource());
    Thread th3 = new Thread(() -> accessResource());
    Thread th4 = new Thread(() -> accessResource());

    th1.start();
    th2.start();
    th3.start();
    th4.start();
  }

  // replacing the synchronise
  public static void accessResource() {
    // lock the resource, only one thread may access.
    boolean lockAcquired = false;
    try {
      lockAcquired = lock.tryLock(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // access the resource here

    if (lockAcquired) {
      try {
        // access resource

      } finally {
        lock.unlock();
      }

    } else {

    }
  }
}
