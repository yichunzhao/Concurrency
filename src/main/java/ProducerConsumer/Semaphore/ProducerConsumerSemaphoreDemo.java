package ProducerConsumer.Semaphore;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Using Semaphore to implement a blocking-queue, and demo a producer and consumer pattern.
 * <p>
 * A blocking queue allows one thread to putting int it, while another one thread to taking from it.
 * <p>
 * When the queue is empty, the thread taking from it is blocked; and when the queue is full, the thread putting into
 * it is blocked.
 */

class MyQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int maxSize;

    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    //private Semaphore
}

public class ProducerConsumerSemaphoreDemo {

    public static void main(String[] args) {

    }
}
