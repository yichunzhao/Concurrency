package Threads;

class A implements Runnable {

    public A() {
    }

    @Override
    public void run() {
        System.out.println("A");
    }
}

class B implements Runnable {
    private Thread a;

    public B(Thread a) {
        this.a = a;
    }

    @Override
    public void run() {
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B");
    }
}

class C implements Runnable {
    private Thread b;

    public C(Thread b) {
        this.b = b;
    }

    @Override
    public void run() {
        try {
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("C");

    }
}

public class PrintThreadInSequenceJoin {

    public static void main(String[] args) {

        Thread a = new Thread(new A());
        Thread b = new Thread(new B(a)); //b depends on a
        Thread c = new Thread(new C(b)); //c depends on b

        a.start();
        b.start();
        c.start();
    }


}
