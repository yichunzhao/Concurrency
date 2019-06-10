package Threads;

class Sing extends Thread {

  @Override
  public void run() {
    System.out.println("sing and dance... ");
  }
}

public class SingAndDance {

  public static void main(String[] args) {

    Thread sing = new Sing();
    // Thread is a runnable, so one thread can be carried in another thread.
    Thread newThread = new Thread(sing);

    // however only current thread is created, but not Sing thread.
    // so it prints once.
    newThread.start();
  }
}
