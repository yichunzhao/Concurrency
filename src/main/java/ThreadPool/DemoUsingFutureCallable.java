package ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class DemoUsingFutureCallable {

  public static void main(String[] args) throws InterruptedException {
    // Thread pool
    ExecutorService service = Executors.newFixedThreadPool(4);

    List<Future> futures = new ArrayList<>();

    IntStream.range(0, 10)
        .forEach(
            i -> {
              Future<Integer> futureResult = service.submit(new MyTask(i));
              futures.add(futureResult);
            });

    //do something else
    Thread.sleep(200);

    System.out.println("future list size: " + futures.size());
    service.shutdown();

    futures.forEach(
        f -> {
          if (f.isDone()) {
              try {
                  System.out.println("result: "+f.get());
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
    Thread.sleep(1);
    return seq;
  }
}
