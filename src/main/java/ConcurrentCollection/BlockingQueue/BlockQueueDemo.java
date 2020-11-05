package ConcurrentCollection.BlockingQueue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Concurrently, it allows one to put an item in a queue, meanwhile another to take an item out.
 * <p>
 * Interface BlockingQueue, Implementation: ArrayBlockingQueue
 * <p>
 * It works on the producer-consumer pattern, where one or many thread adding item to the queue, meanwhile
 * a single or many thread consuming them.
 * <p>
 * It blocks adding new elements if the queue has reached its capacity.
 * It blocks removing elements as the queue is empty.
 * <p>
 */

class Request {
    private static int counter;

    {
        counter++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(Integer.toString(counter));
        return sb.append(" request").toString();
    }
}


@Slf4j
@RequiredArgsConstructor
class Client implements Runnable {
    private static volatile boolean flag = true;

    private final BlockingQueue<Request> queue;

    public static void stop() {
        flag = false;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                Request r = new Request();
                queue.put(r);
                log.info("client send request: " + r);
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

@RequiredArgsConstructor
@Slf4j
class Server implements Runnable {
    private static volatile boolean flag = true;

    private final BlockingQueue<Request> queue;

    public static void stop() {
        flag = false;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                Request request = this.queue.take();
                log.info("consumed : " + request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

@Slf4j
public class BlockQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Request> queue = new ArrayBlockingQueue<>(20);

        Client client = new Client(queue);
        Client client1 = new Client(queue);
        Client client2 = new Client(queue);

        Server server = new Server(queue);

        List<Client> clients = Arrays.asList(client, client1, client2);

        ExecutorService clientService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);
        ExecutorService serverService = Executors.newSingleThreadExecutor();

        clients.forEach(clientService::submit);
        serverService.submit(server);

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Client.stop();
        Server.stop();

        clientService.shutdown();
        serverService.shutdown();

        while (!serverService.isShutdown()) {
            log.info("wait serverService shut down");
        }

        while (!serverService.isShutdown()) {
            log.info("wait serverService shut down");
        }

        System.exit(0);
    }
}
