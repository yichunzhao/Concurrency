package Threads;

/**
 * When designer is doing his job,  having finished his job; then notify developer start to develop.
 *
 * @author YNZ
 */


class Developer implements Runnable {

    Designer designer;

    boolean devIsReady = false;

    public Developer(Designer designer) {
        this.designer = designer;
    }

    @Override
    public void run() {
        //achieve designer  monitor and lock it.
        synchronized (designer) {

            try {
                designer.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (this.designer.designIsReady) System.out.println("Developer developing ... ");
            confirmReady();
        }

    }

    synchronized public void confirmReady() {
        this.devIsReady = true;
        notify();
    }
}

class Designer implements Runnable {

    boolean designIsReady = false;

    @Override
    public void run() {
        System.out.println("Designer start to design ... ");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Designer finish designing ... ");
        confirmReady();
    }

    synchronized public void confirmReady() {
        this.designIsReady = true;
        notify();
    }
}

class Tester implements Runnable {
    Developer developer;

    public Tester(Developer developer) {
        this.developer = developer;
    }

    @Override
    public void run() {

        //achieve lock from developer in order to call wait and notify
        synchronized (developer) {

            try {
                developer.wait();
                if (developer.devIsReady) System.out.println("Tester start to test");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Tester finish the test");
        }

    }

}

public class ThreadNotify {
    //making designer run first, then developer start to run.

    public static void main(String... args) {
        Designer designer = new Designer();
        Developer developer = new Developer(designer);
        Tester tester = new Tester(developer);

        Thread th1 = new Thread(developer);
        th1.start();
        Thread th2 = new Thread(designer);
        th2.start();

        Thread th3 = new Thread(tester);
        th3.start();

    }


}
