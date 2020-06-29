package racecondition.barrier;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

class ShoppingList {
    private int amountOfChips = 1;

    public int getAmountOfChips() {
        return amountOfChips;
    }

    public void setAmountOfChips(int amountOfChips) {
        this.amountOfChips = amountOfChips;
    }
}

class Shopper implements Runnable {
    private ShoppingList shoppingList;
    private String shopperName;

    private static CyclicBarrier barrier = new CyclicBarrier(10);
    private static Lock lock = new ReentrantLock();


    public Shopper(ShoppingList shoppingList, String shopperName) {
        this.shoppingList = shoppingList;
        this.shopperName = shopperName;
    }

    @Override
    public void run() {

        if (shopperName.contains("oliver")) {

            lock.lock();

            try {
                int currentAmount = shoppingList.getAmountOfChips() + 3;
                shoppingList.setAmountOfChips(currentAmount);

                System.out.println(shopperName + "add 3 chip bags");

            } finally {
                lock.unlock();
            }

            //set barrier, and wait
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

        } else {
            //wait for barrier breaking.
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            lock.lock();
            try {
                int currentAmount = shoppingList.getAmountOfChips() * 2;
                shoppingList.setAmountOfChips(currentAmount);
                System.out.println(shopperName + " double chip bags");

            } finally {
                lock.unlock();
            }
        }

    }
}


public class UsingBarrierToSolveRaceCondition {
    public static void main(String[] args) throws InterruptedException {
        ShoppingList shoppingList = new ShoppingList();

        List<Thread> threads = new ArrayList<>();

        IntStream.rangeClosed(1, 5).forEach(i -> threads.add(new Thread(new Shopper(shoppingList, "oliver"))));
        IntStream.rangeClosed(1, 5).forEach(i -> threads.add(new Thread(new Shopper(shoppingList, "barron"))));
        threads.forEach(thread -> thread.start());

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("total chip amount: " + shoppingList.getAmountOfChips());

    }
}
