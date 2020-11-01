package ThreadPool;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** @author YNZ */
class BookRepository {
  private AtomicInteger counter = new AtomicInteger(0);

  public void newSale() {
    counter.incrementAndGet();
  }

  public void deSale() {
    counter.decrementAndGet();
  }

  public int getCounter() {
    return counter.get();
  }
}

class Task implements Runnable {

  private String name;
  private BookRepository bookRepository;

  public Task(String name, BookRepository bookRepository) {
    this.name = name;
    this.bookRepository = bookRepository;
  }

  @Override
  public void run() {
    bookRepository.newSale();

    System.out.println("task  : " + name + " counter=" + bookRepository.getCounter());
  }
}

public class UsingExecutorService {
  // pool size selection depends on task cpu intensive or not; on this case select pool size rer.
  // to cpu numbers
  private static int numberOfCpu = Runtime.getRuntime().availableProcessors();

  public static void main(String... args) throws InterruptedException {
    BookRepository bookRepository = new BookRepository();

    Instant start;
    Instant end;

    // thread pool using blocking queue.

    // ExecutorService interface
    ExecutorService executorService;

    start = Instant.now();

    executorService = Executors.newFixedThreadPool(numberOfCpu);

    for (int i = 0; i < 100; i++) {
      executorService.execute(new Task(String.valueOf(i), bookRepository));
    }
    // commit tasks to executor to implement.

    executorService.awaitTermination(1000, TimeUnit.MICROSECONDS);

    end = Instant.now();

    System.out.println("cached thread pool time cost : " + Duration.between(start, end));

    Thread.sleep(1000);

    start = Instant.now();

    ExecutorService cachedExecutor = Executors.newCachedThreadPool();

    for (int j = 0; j < 100; j++) {
      cachedExecutor.execute(new Task(String.valueOf(j), bookRepository));
    }

    cachedExecutor.awaitTermination(1000, TimeUnit.MICROSECONDS);

    end = Instant.now();

    System.out.println("cached thread pool time cost : " + Duration.between(start, end));
  }
}
