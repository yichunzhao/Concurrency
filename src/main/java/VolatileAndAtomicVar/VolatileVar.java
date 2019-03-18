package VolatileAndAtomicVar;

/**
 * @author YNZ
 */
class WriterA implements Runnable{
    private boolean flag;

    public WriterA(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println("write A working ....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.flag = false;

    }
}

class WriterB implements Runnable{
    private  boolean flag;

    public WriterB(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        while(flag){
            System.out.println("writer B working ...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

public class VolatileVar {
    //shared cache; or common memory
    private static volatile boolean flag = true;

    public static void main(String[] args) {

        Thread th1 = new Thread(new WriterA(flag));
        th1.start();

        Thread th2 = new Thread(new WriterB(flag));
        th2.start();

    }
}
