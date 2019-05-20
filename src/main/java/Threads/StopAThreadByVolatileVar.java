package Threads;

import java.util.Random;

public class StopAThreadByVolatileVar {

    private static volatile boolean exit = false;

    private static Random r = new Random(10);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {

            while (!exit) {
                System.out.println(r.nextInt());
            }
        }).start();

        //Wait for 2 sec.
        Thread.sleep(2000);

        //Stop the thread by exit flag.
        exit = true;

    }
}
