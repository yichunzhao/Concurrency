package liveness.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * tryLock if cannot get lock, then return at once.
 *
 * Indeed there is no deadlock, for there is one guy give up once to acquire.
 */
public class KitchenDemoTryLock {

    private static Lock spoon = new ReentrantLock();
    private static Lock bowl = new ReentrantLock();

    public static void main(String[] args) {

        Thread task1 = new Thread(() -> {
            if (spoon.tryLock()) {
                System.out.println("cooker1 asking for spoon...");

                if (bowl.tryLock()) {
                    System.out.println("cooker1 asking for bowl...");
                    bowl.unlock();
                }

                spoon.unlock();
            }
        });

        Thread task2 = new Thread(() -> {
            if (bowl.tryLock()) {
                System.out.println("cooker2 asking for bowl...");

                if (spoon.tryLock()) {
                    System.out.println("cooker2 asking for spoon...");
                    spoon.unlock();
                }

                bowl.unlock();
            }
        });

        task1.start();
        task2.start();
    }

}
