package BlockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * creating a handmade blocking queue.
 * <p>
 * allowing one thread to put an element in the queue; meanwhile, allowing another thread polling one.
 * <p>
 * when Q is full, the producer is blocked; when Q is empty, the consumer is blocked.
 * <p>
 * ReentrantLock is an explicit lock similar to implicit lock provided by synchronized keyword.
 * <p>
 * features:  fairness; the thread doesn't need to block infinitely after trying a certain amount time; ability to
 * interrupt thread while waiting for Lock; try lock time out
 */
interface AccessingQueue<T> {

    void enqueue(T t) throws InterruptedException;

    T dequeue() throws InterruptedException;

    List<T> getTotalProduced();

    List<T> getTotalConsumed();
}

@Slf4j
class MyBlockingQueue<T> implements AccessingQueue<T> {

    private Queue<T> queue = new LinkedList<>();

    // if the queue is full, blocking the producer.
    private ReentrantLock tailLock = new ReentrantLock();

    // if the queue is empty, blocking the consumer.
    private ReentrantLock headLock = new ReentrantLock();

    private List<T> totalProduced = new ArrayList<>();
    private List<T> totalConsumed = new ArrayList<>();

    @Override
    public void enqueue(T t) throws InterruptedException {
        tailLock.tryLock();
        try {
            //if queue is full wait
            while (queue.size() == 20) {
                tailLock.wait();
                headLock.notifyAll();
            }

            queue.offer(t);
            totalProduced.add(t);
        } finally {
            tailLock.unlock();
        }
    }

    // if the queue is empty, blocking the consumer.
    @Override
    public T dequeue() throws InterruptedException {
        headLock.tryLock();
        try {
            while (queue.isEmpty()) {
                headLock.wait();
                tailLock.notifyAll();
            }

            T polled = queue.poll();
            totalConsumed.add(polled);

            return polled;
        } finally {
            headLock.unlock();
        }
    }

    @Override
    public List<T> getTotalProduced() {
        return new ArrayList<>(totalProduced);
    }

    @Override
    public List<T> getTotalConsumed() {
        return new ArrayList<>(totalConsumed);
    }
}

class Producer implements Runnable {
    private AccessingQueue<Integer> queue;
    private Integer seq;


    public Producer(AccessingQueue queue, Integer seq) {
        this.queue = queue;
        this.seq = seq;
    }

    @Override
    public void run() {
        try {
            this.queue.enqueue(seq);
            System.out.println("current thread: " + Thread.currentThread().getName() + " produce: " + seq);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Consumer implements Runnable {
    private AccessingQueue<Integer> queue;
    private Integer seq;

    public Consumer(AccessingQueue queue, Integer seq) {
        this.queue = queue;
        this.seq = seq;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer number = queue.dequeue();
                System.out.println("current thread: " + Thread.currentThread().getName() + " consumed: " + number);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

public class DemoBlockingQueue {
    private static final int PROD_SIZE = 300;

    public static void main(String[] args) throws InterruptedException {

        AccessingQueue<Integer> accessingQueue = new MyBlockingQueue<>();

        ExecutorService service = Executors.newFixedThreadPool(100);
        ExecutorService consumerService = Executors.newFixedThreadPool(10);

        IntStream.rangeClosed(1, PROD_SIZE)
                .forEach(i -> service.submit(new Producer(accessingQueue, i)));

        IntStream.rangeClosed(1, 3)
                .forEach(i -> consumerService.submit(new Consumer(accessingQueue, i)));

        service.awaitTermination(2000, TimeUnit.MILLISECONDS);
        service.shutdown();

        System.out.println("the executor shuts down? " + service.isShutdown());

        System.out.println("total consumed is equal to 300? " + (accessingQueue.getTotalConsumed().size() == PROD_SIZE));
        System.out.println("total produced is equal to 300? " + (accessingQueue.getTotalProduced().size() == PROD_SIZE));

        System.out.println("total consumed :" + accessingQueue.getTotalConsumed().size());
        System.out.println("total produced :" + accessingQueue.getTotalProduced().size());
        System.exit(1);
    }
}
