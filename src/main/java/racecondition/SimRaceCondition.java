package racecondition;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * race condition is different from data race. it is caused by incorrect ordering of operations on the shared the state
 * stored in the memory. The state may be inconsistent due to different ordering of independent operations.
 */


class ShoppingList {
    private int amountOfChips = 1;
    private Lock lock = new ReentrantLock();

    public int getAmountOfChips() {
        return amountOfChips;
    }

    public void setAmountOfChips(int amountOfChips) {
        lock.lock();
        this.amountOfChips = amountOfChips;
        lock.unlock();
    }
}

@AllArgsConstructor
abstract class Shopper implements Runnable {
    protected ShoppingList shoppingList;
}


class Oliver extends Shopper {

    public Oliver(ShoppingList shoppingList) {
        super(shoppingList);
    }

    @Override
    public void run() {
        System.out.println("oliver add 3 extra chips.");
        int currentAmount = shoppingList.getAmountOfChips();
        shoppingList.setAmountOfChips(currentAmount += 3);
    }
}

class Barron extends Shopper {

    public Barron(ShoppingList shoppingList) {
        super(shoppingList);
    }

    @Override
    public void run() {
        System.out.println("Barron double the amount of chips.");
        int currentAmount = shoppingList.getAmountOfChips();
        shoppingList.setAmountOfChips(currentAmount * 2);
    }
}

public class SimRaceCondition {

    //simulating a race condition.
    public static void main(String[] args) {
        ShoppingList shoppingList = new ShoppingList();


        List<Thread> threads = new ArrayList<>();

        IntStream.rangeClosed(1, 5).forEach(i -> threads.add(new Thread(new Oliver(shoppingList))));
        IntStream.rangeClosed(1, 5).forEach(i -> threads.add(new Thread(new Barron(shoppingList))));
        threads.forEach(thread -> thread.start());

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("At the end of simulation amount of chip bags: " + shoppingList.getAmountOfChips());

    }


}
