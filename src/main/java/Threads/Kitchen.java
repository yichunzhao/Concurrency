package Threads;

public class Kitchen {

  private static Runnable chefOliver =
      () -> {
        System.out.println("Olivia started & waiting for sausage to thaw...");

        // simulating Olivia chopping whatever.
        try {
          Thread.sleep(7000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        System.out.println("Olivia is done cutting sausage.");
      };

  public static void main(String[] args) throws InterruptedException {
    // main thread.
    System.out.println("Barron started and requesting Olivia's help");

    // Barron asking  Oliver to help
    Thread oliver = new Thread(chefOliver);
    System.out.println("...Oliver thread state: " + oliver.getState());
    oliver.start();
    System.out.println("...Oliver thread state: " + oliver.getState());
    System.out.println("Oliver start");

    System.out.println("Barron continue cooking the soup.");
    Thread.sleep(500);
    System.out.println("...Oliver thread state: " + oliver.getState());

    // Barron waiting for oliver finishing the chopping task, and he may continue.
    oliver.join();
    System.out.println("...Oliver thread state: " + oliver.getState());
    System.out.println("...Barron thread state: " + Thread.currentThread().getState());

    System.out.println("Barron and Oliver together finished the task.");
  }
}
