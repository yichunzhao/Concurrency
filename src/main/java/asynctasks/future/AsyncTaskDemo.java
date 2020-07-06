package asynctasks.future;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.stream.IntStream;


@AllArgsConstructor
@Builder
@ToString
class TaskResult {
    private String result;
}

@Slf4j
class AsyncTask implements Callable<TaskResult> {

    @Override
    public TaskResult call() throws Exception {
        log.info(Thread.currentThread().getName() + " is carrying the async task.");
        Thread.sleep(3000);
        return TaskResult.builder().result("Your trip plan.").build();
    }
}

/**
 * In order to reduce working load, one thread may assign a task to another thread, and get the result from a
 * placeholder. This assigned task is an async task.
 */
@Slf4j
public class AsyncTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        AsyncTask asyncTask = new AsyncTask();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<TaskResult> resultFuture = executorService.submit(asyncTask);

        IntStream.rangeClosed(0, 10).forEach(i -> {
            log.info("Main thread is free and then do something else: ###" + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                log.error("Thread exception: ", e);
            }

        });

        log.info("Task is done: " + resultFuture.get());

        executorService.shutdown();

    }
}
