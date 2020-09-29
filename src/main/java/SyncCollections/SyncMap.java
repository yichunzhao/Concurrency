package SyncCollections;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SyncMap {
    // having a lock
    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> synchronisedMap = Collections.synchronizedMap(map);

        Thread t1 = new Thread(() -> synchronisedMap.put(1, "one"));
        Thread t2 = new Thread(() -> synchronisedMap.put(2, "two"));
        Thread t3 = new Thread(() -> synchronisedMap.put(3, "three"));
        Thread t4 = new Thread(() -> synchronisedMap.put(4, "four"));
        Thread t5 = new Thread(() -> synchronisedMap.put(5, "five"));

        Arrays.asList(t1, t2, t3, t4, t5).forEach(thread -> {
            try {
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(synchronisedMap.toString());

    }
}
