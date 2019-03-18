package VolatileAndAtomicVar;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YNZ
 */

@Data
class Inventory {
    private AtomicInteger counter = new AtomicInteger(10);

    public int addOne() {
        return counter.incrementAndGet();
    }

    public int sellOne() {
        return counter.decrementAndGet();
    }
}

public class DemoAtomicInteger {
    private static Inventory inventory = new Inventory();
    private static Runnable sellOne = () -> inventory.sellOne();
    private static Runnable addOne = () -> inventory.addOne();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println("counter current value : " +inventory.getCounter().get());

        executorService.submit(addOne);
        executorService.submit(addOne);
        executorService.submit(addOne);
        executorService.submit(sellOne);
        executorService.submit(sellOne);

        Thread.sleep(1560);

        System.out.println("counter current value : " +inventory.getCounter().get());

        executorService.shutdown();

    }
}
