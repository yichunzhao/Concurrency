package racecondition.latch;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Shopper implements Runnable {
    private ShoppingList shoppingList;
    private String shopperName;

    private static CountDownLatch latch = new CountDownLatch(5);
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
            latch.countDown();

        } else {
            //wait for barrier breaking.
            try {
                latch.await();
            } catch (InterruptedException e) {
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


