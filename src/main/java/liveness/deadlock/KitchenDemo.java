package liveness.deadlock;

/**
 * Object-monitor-lock;
 * <p>
 * Thread achieving lock first in order to visit the resource;
 * <p>
 * there are lock A and lock B;
 * <p>
 * if thread-1 hold lock A, and thread-2 holds lock B; meanwhile thread-1 asking for lock B, and thread-2 asking for
 * lock A; both threads are in a blocking state, and keep requesting. if one of them gives up, then they may quit
 * from the deadlock state. in another way, they should keep a fixed sequence to use resource.
 * <p>
 */


public class KitchenDemo {

    private static Object spoon = new Object();
    private static Object bowl = new Object();

    public static void main(String[] args) {

        Thread task1 = new Thread(() -> {
            synchronized (spoon) {
                System.out.println("cooker1 asking for spoon...");
                synchronized (bowl) {
                    System.out.println("cooker1 asking for bowl...");
                }
            }
        });

        Thread task2 = new Thread(() -> {
            synchronized (bowl) {
                System.out.println("cooker2 asking for bowl...");
                synchronized (spoon) {
                    System.out.println("cooker2 asking for spoon...");
                }
            }
        });

        task1.start();
        task2.start();
    }

}
