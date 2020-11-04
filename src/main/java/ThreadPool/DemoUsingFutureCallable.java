package ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class DemoUsingFutureCallable {

    public static void main(String[] args) throws InterruptedException {
        // Thread pool
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future> futures = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> {
            Future<Integer> futureResult = service.submit(new MyTask(i));
            futures.add(futureResult);
        });

        System.out.println("we have " + futures.size() + " task-result on the way ");

        service.shutdown();

        //main thread may do something else, while waiting async-task done.
        Thread.sleep(1000);

        futures.forEach(f -> {
            if (f.isDone()) {
                try {
                    System.out.println("result: " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}

class MyTask implements Callable<Integer> {
    private int seq;

    public MyTask(int seq) {
        this.seq = seq;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("carrying out call method: " + seq);
        Thread.sleep(20);
        return seq;
    }
}
