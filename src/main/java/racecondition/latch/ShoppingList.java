package racecondition.latch;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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


