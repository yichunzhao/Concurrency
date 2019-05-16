package ConCurHashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author YNZ
 */
public class UsingConcurrentHashMap {
    //lock free
    public static void main(String... args) throws InterruptedException {

        final ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        new Thread(() -> concurrentMap.put("Acer", 1), "th3"
        ).start();

        new Thread(() -> {
            concurrentMap.put("Dell", 1);
            concurrentMap.put("Sony", 2);
        }, "th1"
        ).start();

        new Thread(() -> {
            concurrentMap.put("Lenovo", 1);
            concurrentMap.put("Dell", 1677);
        }, "th2"
        ).start();

        new Thread(() -> {
            concurrentMap.put("Thinkpad", 1);
            concurrentMap.put("Aser", 1);
        }, "th3"
        ).start();

        new Thread(() -> {
            System.out.println(concurrentMap.get("Dell"));
        }, "th4"
        ).start();



        Thread.sleep(100);

        System.out.println(concurrentMap.toString());

    }

}
