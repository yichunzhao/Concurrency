package SyncCollections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SyncMap {
  // having a lock
  public static void main(String[] args) throws InterruptedException {
    Map<Integer, String> map = new HashMap<>();
    Map<Integer, String> synchronisedMap = Collections.synchronizedMap(map);

    new Thread(() -> synchronisedMap.put(1, "one")).start();
    new Thread(() -> synchronisedMap.put(2, "two")).start();
    new Thread(() -> synchronisedMap.put(3, "three")).start();
    new Thread(() -> synchronisedMap.put(4, "four")).start();
    new Thread(() -> synchronisedMap.put(5, "five")).start();
    new Thread(() -> System.out.println(synchronisedMap.get(5))).start();

    Thread.sleep(500);
    System.out.println(synchronisedMap.toString());
  }
}
