package asynctasks.future;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


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
 * placeholder. The assigned task is referred as an async task.
 */
@Slf4j
public class AsyncTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        AsyncTask asyncTask = new AsyncTask();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //delegate this task to anther thread. The result is given in a moment of the future
        Future<TaskResult> resultFuture = executorService.submit(asyncTask);
        executorService.shutdown();

        while (!resultFuture.isDone()) {
            log.info("Main thread is free and then do something else: ###");
            //the thread now is free and can do something else, while waiting for the result.
            try {

                //simulating do something else
                Thread.sleep(3000);
                log.info("main thread has done its job...");
            } catch (InterruptedException e) {
                log.error("Thread exception: ", e);
            }
        }

        log.info("Is Task done? " + resultFuture.isDone() + " result: " + resultFuture.get());

    }
}
