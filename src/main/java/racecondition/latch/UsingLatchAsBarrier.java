package racecondition.latch;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *  Similar to the Cyclic barrier, but using counting down a number.
 */

public class UsingLatchAsBarrier {

    public static void main(String[] args) throws InterruptedException {

        ShoppingList shoppingList = new ShoppingList();

        List<Thread> threads = new ArrayList<>(10);

        IntStream.rangeClosed(1,5).forEach(i -> threads.add(new Thread(new Shopper(shoppingList,"oliver"))));
        IntStream.rangeClosed(1,5).forEach(i->threads.add(new Thread(new Shopper(shoppingList,"barron"))));

        threads.forEach(thread -> thread.start());

        for(Thread t: threads){
            t.join();
        }

        System.out.println("total bags of chips: " + shoppingList.getAmountOfChips());

    }
}
