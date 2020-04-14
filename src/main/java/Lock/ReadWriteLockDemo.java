package Lock;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

class CalendarUser implements Runnable {

  private static final DayOfWeek[] WEEKDAYS = DayOfWeek.values();

  private static int today = 0;

  private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  private static Lock readMarker = readWriteLock.readLock();
  private static Lock writeMarker = readWriteLock.writeLock();

  private String name;

  public CalendarUser(String name) {
    this.name = name;
  }

  @Override
  public void run() {

    while (today < WEEKDAYS.length - 1) {
      if (this.name.contains("writer")) {
        writeMarker.lock();
        today = (today + 1) % 7;
        writeMarker.unlock();

        System.out.println(this.name + " updated date to " + WEEKDAYS[today]);

      } else { // reader- check to see what today is
        readMarker.lock();
        System.out.println(this.name + " sees that today is " + WEEKDAYS[today]);
        readMarker.unlock();
      }
    }
  }
}

public class ReadWriteLockDemo {
  public static void main(String[] args) {
    Instant start = Instant.now();

    // simulating 10 readers to read the Calendar to what day today
    IntStream.range(0, 10).forEach(i -> new Thread(new CalendarUser("reader-" + i)).start());

    // two others to change the day of today.
    IntStream.range(0, 2).forEach(i -> new Thread(new CalendarUser("writer-" + i)).start());

    Instant end = Instant.now();

    System.out.println("time cost: " + Duration.between(start, end).toMillis());
  }
}
