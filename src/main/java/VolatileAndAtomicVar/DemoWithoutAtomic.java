package VolatileAndAtomicVar;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author YNZ
 */

@Data
class Storage {
    private Integer counter = 10;

    public void addOne() {
        counter++;
    }

    public void sellOne() {
        counter--;
    }

}


public class DemoWithoutAtomic {
    private static Storage storage = new Storage();

    private static Runnable addTask = () -> storage.addOne();
    private static Runnable sellTask = () -> storage.sellOne();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(12);
        System.out.println("counter init.: " + storage.getCounter());
        executorService.submit(addTask);
        executorService.submit(addTask);
        executorService.submit(addTask);
        executorService.submit(sellTask);
        executorService.submit(sellTask);

        Thread.sleep(1000);

        System.out.println("counter end: " + storage.getCounter());
        executorService.shutdown();

    }

}
