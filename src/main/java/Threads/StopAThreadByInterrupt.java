package Threads;


import java.util.Random;

public class StopAThreadByInterrupt {

    private static Random randomNumberGen = new Random(100);

    public static void main(String[] args) {

        Thread task = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {
                //do something here
                System.out.println(" " + randomNumberGen.nextInt());
            }
        }
        );

        //start the thread
        task.start();

        //Sleep for a while
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Java thread can only be stopped by an interrupt
        //it cannot be killed.
        task.interrupt();
    }


}
