package BlockingQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * creating a blocking queue.
 *
 * <p>there is one thread to put an element in the queue; meanwhile another thread polling one.
 */
interface AccessingQueue<T> {

  void enqueue(T t) throws InterruptedException;

  T dequeue() throws InterruptedException;
}

class MyBlockingQueue<T> implements AccessingQueue<T> {

  private Queue<T> queue = new LinkedList<>();

  // if the queue is full, blocking the producer.
  private ReentrantLock lockA = new ReentrantLock();
  private ReentrantLock lockB = new ReentrantLock();

  @Override
  public void enqueue(T t) throws InterruptedException {
    lockA.lock();
    while (queue.size() > 16) {
      this.wait();
    }

    notifyAll();
    queue.offer(t);
    lockA.unlock();
  }

  // if the queue is empty, blocking the consumer.
  @Override
  public T dequeue() throws InterruptedException {
    lockB.lock();

    while (queue.size() == 0) {
      wait(); // current thread wait
    }

    try {
      return queue.poll();
    } finally {
      lockB.unlock();
    }
  }
}

class TaskEnqueue implements Runnable {
  private AccessingQueue<Integer> queue;
  private Integer seq;

  public TaskEnqueue(AccessingQueue queue, Integer seq) {
    this.queue = queue;
    this.seq = seq;
  }

  @Override
  public void run() {
    try {
      this.queue.enqueue(seq);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class TaskDequeue implements Runnable {
  private AccessingQueue<Integer> queue;
  private Integer seq;

  public TaskDequeue(AccessingQueue queue, Integer seq) {
    this.queue = queue;
    this.seq = seq;
  }

  @Override
  public void run() {
    try {
      System.out.println(this.queue.dequeue());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class DemoBlockingQueue {

  public static void main(String[] args) throws InterruptedException {

    AccessingQueue<Integer> accessingQueue = new MyBlockingQueue<>();

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    IntStream.rangeClosed(1, 100)
        .forEach(i -> executorService.submit(new TaskEnqueue(accessingQueue, i)));

    IntStream.rangeClosed(1, 100)
        .forEach(i -> executorService.submit(new TaskDequeue(accessingQueue, i)));

    executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
    executorService.shutdown();
  }
}
