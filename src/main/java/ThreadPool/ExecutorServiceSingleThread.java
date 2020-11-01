package ThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author YNZ
 */
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
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(1000);
            return "callable task";
        });

        while (!future.isDone()) {
            System.out.println("wait for the result ready....");
        }

        String result = future.get();
        System.out.println("Result: " + result);
    }
}
