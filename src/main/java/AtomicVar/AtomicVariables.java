package AtomicVar;

/**
 * @author YNZ
 */
class Counter {
    private int i = 0;
}

public class AtomicVariables {

    private static int c = 0;

    public static void increase() {
        c++;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 150; i++) {
            new Thread(() -> {
                increase();
            }).start();

            System.out.println("th " + String.valueOf(i) + " : " + i);

        }


    }

}
