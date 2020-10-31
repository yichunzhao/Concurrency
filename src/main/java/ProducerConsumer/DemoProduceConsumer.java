package ProducerConsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
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
    private final int maxSize;

    public MyBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    private final Queue<T> queue = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition queueIsNotFull = lock.newCondition();
    private final Condition queueIsNotEmpty = lock.newCondition();

    private final List<T> totalProduced = Collections.synchronizedList(new ArrayList<>());
    private final List<T> totalConsumed = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void enqueue(T t) throws InterruptedException {
        lock.lock();

        try {
            //wait until queue is not full.
            while (queue.size() >= maxSize) {
                queueIsNotFull.await();
            }

            if (queue.offer(t)) {
                totalProduced.add(t);
                log.info("produce: " + t.toString());
            }

            //waking up one thread to add more;
            queueIsNotEmpty.signalAll();

        } finally {
            lock.unlock();
        }
    }


    // if the queue is empty, blocking the consumer.
    @Override
    public T dequeue() throws InterruptedException {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                queueIsNotEmpty.await();
            }

            T polled = queue.poll();
            queueIsNotFull.signalAll();
            totalConsumed.add(polled);
            log.info(" consumed: " + polled.toString());
            return polled;
        } finally {
            lock.unlock();
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

@Slf4j
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
            Thread.sleep(20);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Slf4j
class Consumer implements Runnable {
    private AccessingQueue<Integer> queue;
    private Integer seq;

    public Consumer(AccessingQueue queue, Integer seq) {
        this.queue = queue;
        this.seq = seq;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                queue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

public class DemoProduceConsumer {
    private static final int PROD_SIZE = 100;

    public static void main(String[] args) throws InterruptedException {
        AccessingQueue<Integer> queue = new MyBlockingQueue<>(20);

        IntStream.range(0, PROD_SIZE)
                .forEach(i -> new Thread(new Producer(queue, i)).start());

        Thread consumer = new Thread(new Consumer(queue, 1));
        consumer.start();

        System.out.println("waiting for task done.... ");
        Thread.sleep(1000);
        consumer.interrupt();

        System.out.println("total consumed is equal? " + (queue.getTotalConsumed().size() == PROD_SIZE));
        System.out.println("total produced is equal? " + (queue.getTotalProduced().size() == PROD_SIZE));

        System.out.println("total consumed :" + queue.getTotalConsumed().size());
        System.out.println("total produced :" + queue.getTotalProduced().size());

        System.exit(1);
    }
}
