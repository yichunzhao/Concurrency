package Threads;

/**
 * @author YNZ
 */
public class ThreadsPrintInSeq {
    Object objectA = new Object();
    Object objectB = new Object();
    Object objectC = new Object();

    public static void main(String... args) {

        Thread th1 = new Thread(() -> System.out.println("A"));

        Thread th2 = new Thread(() -> System.out.println("B"));

        Thread th3 = new Thread(() -> System.out.println("C"));

        th1.start();
        th2.start();
        th3.start();
    }
}
