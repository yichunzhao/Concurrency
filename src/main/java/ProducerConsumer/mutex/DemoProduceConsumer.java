package ProducerConsumer.mutex;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    void dequeue() throws InterruptedException;

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

    private final List<T> totalProduced = new ArrayList<>();
    private final List<T> totalConsumed = new ArrayList<>();

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
    public void dequeue() throws InterruptedException {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                queueIsNotEmpty.await();
            }

            T polled = queue.poll();
            queueIsNotFull.signalAll();
            totalConsumed.add(polled);
            log.info(" consumed: " + polled.toString());
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
class Producer<T> implements Runnable {
    private final AccessingQueue<T> queue;
    private final T seq;

    public Producer(AccessingQueue<T> queue, T seq) {
        this.queue = queue;
        this.seq = seq;
    }

    @Override
    public void run() {
        try {
            this.queue.enqueue(seq);
            Thread.sleep(20);

        } catch (InterruptedException e) {
            log.error("producer", e);
        }
    }
}

@Slf4j
class Consumer<T> implements Runnable {
    private final AccessingQueue<T> queue;
    private final T seq;

    public Consumer(AccessingQueue<T> queue, T seq) {
        this.queue = queue;
        this.seq = seq;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                queue.dequeue();
            } catch (InterruptedException e) {
                log.error("consumer error: ", e);
            }
        }
    }
}

public class DemoProduceConsumer {
    private static final int PROD_SIZE = 100;

    public static void main(String[] args) throws InterruptedException {
        AccessingQueue<Integer> queue = new MyBlockingQueue<>(20);

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        IntStream.range(0, PROD_SIZE)
                .forEach(i -> service.submit(new Producer<>(queue, i)));

        Thread consumer = new Thread(new Consumer<>(queue, 1));
        consumer.start();

        //Initiates an orderly shutdown in which previously submitted tasks are executed,
        // but no new tasks will be accepted. Invocation has no additional effect if already shut down.
        service.shutdown();

        Thread.sleep(3000);

        consumer.interrupt();

        System.out.println("total consumed is equal? " + (queue.getTotalConsumed().size() == PROD_SIZE));
        System.out.println("total produced is equal? " + (queue.getTotalProduced().size() == PROD_SIZE));

        System.out.println("total consumed :" + queue.getTotalConsumed().size());
        System.out.println("total produced :" + queue.getTotalProduced().size());

        System.exit(0);
    }
}
