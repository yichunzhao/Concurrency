package VolatileAndAtomicVar.AtomicVar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YNZ
 */
class Inventory {
    private AtomicInteger counter = new AtomicInteger(10);

    public int addOne() {
        return counter.incrementAndGet();
    }

    public int sellOne() {
        return counter.decrementAndGet();
    }

    public int getCounter() {
        return this.counter.get();
    }
}

public class DemoAtomicInteger {
    private static Inventory inventory = new Inventory();
    private static Runnable sellOne = () -> System.out.println("inventory sell one item " + inventory.sellOne());
    private static Runnable addOne = () -> System.out.println("add one item inventory : " + inventory.addOne());

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println("original counter value : " + inventory.getCounter());

        executorService.submit(addOne);
        executorService.submit(addOne);
        executorService.submit(addOne);
        executorService.submit(sellOne);
        executorService.submit(sellOne);

        executorService.shutdown();
        if (executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
            System.out.println("counter current value : " + inventory.getCounter());
        }
    }
}
