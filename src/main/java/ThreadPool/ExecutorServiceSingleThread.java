package ThreadPool;

import java.util.concurrent.*;

/** @author YNZ */
public class ExecutorServiceSingleThread {

  public static void main(String... args) throws ExecutionException, InterruptedException {
    // Create a single thread worker and an unbound queue.
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> System.out.println("Hello World"));

    // ScheduledExecutorService -> ExecutorService -> Executor.
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(() -> System.out.println("Hello XXX"));

    // Submit asynchronous tasks(callable), to the queue. If you don’t want
    // your Callable to return a value, you can create it using Callable<Void>.
    Future<String> future = executorService.submit(() -> "callable task");

    if (future.isDone()) {
      String result = future.get();
      System.out.println("Result: " + result);
    }
  }
}
