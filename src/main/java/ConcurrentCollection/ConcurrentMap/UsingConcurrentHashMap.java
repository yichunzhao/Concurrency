package ConcurrentCollection.ConcurrentMap;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Lock-free
 *
 * <p>
 * ConcurrentMap -> Map interface;
 * <p>
 * It uses a group of locks 16 in all, each guarding a subset of the hash table. It is optimized for multi-core CPUs.
 * This is different from a synchronised map, where the whole map is locked by one lock for a single operation.
 * <p>
 * Read operation is always thread-safe, even without any exclusion protection; Write operations can be operated on
 * different sections of the map.
 *
 * <p>
 * It provides "atomic get-and-maybe-set" method
 * map.computeIfAbsent(key, k->mappingFunction(k)).
 * <p>
 * V value = map.get(key);
 * if(value==null) {
 * value = mappingFunc.apply(key);
 * if(value!=null) map.put(key,value);
 * }
 * return value;
 *
 * <p>
 * value putIfAbsent(key, value); if map without key-value, putting key-value
 * boolean remove(object key, object value)
 * value replace(key, value)
 * boolean replace(key,oldValue,newValue)
 * <p>
 * ConcurrentHashMap is its implementation.
 * <p>
 * These operations remove or replace a k-v only if the key is present; or add key-value pair is absent.
 * Making these operation atomic without using synchronization, or lock.
 */

@Slf4j
public class UsingConcurrentHashMap {
    private static final ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
    private static final Map<String, Integer> map = new HashMap<>();

    static {
        concurrentMap.putIfAbsent("Lenovo", 1300);
        map.putIfAbsent("Lenovo", 1300);
        map.put("Dell", 2300);
        map.put("Mac", 6999);
    }

    /**
     * the following method is not atomic; so it is not thread safe.
     */
    //when this method carried out by several threads simultaneously, a data-race could happen
    static void manipulateMap(String key, Integer value) {
        //if key is absent in the map
        if (concurrentMap.containsKey(key)) {//read is an atomic step.
            //what between these two atomic steps? fx: the key-value pair may be removed by anther thread.
            concurrentMap.put(key, value);//write is an atomic step.
            log.info(concurrentMap.toString());
        }
    }

    /**
     * So these two steps should be in one atomic step.
     * solution: using atomic method replace()
     */
    static void safeManipulateMap(String key, Integer value) {
        concurrentMap.replace(key, value);
        log.info(concurrentMap.toString());
    }

    // lock free
    public static void main(String... args) throws InterruptedException {
        log.info(concurrentMap.toString());

        //if the key-value existed, only return current value; value won't be updated
        Integer y = concurrentMap.putIfAbsent("Lenovo", 1200);
        System.out.println("y= " + y);
        log.info(concurrentMap.toString());

        //DELL is not existed before; returned y = null; the returned value is older value
        Integer x = concurrentMap.putIfAbsent("DELL", 2300);
        System.out.println("x= " + x);
        log.info(concurrentMap.toString());

        //updating existing key-value pair
        //it returns old value, and updating the old value into new value.
        Integer p = concurrentMap.replace("Lenovo", 1200);
        log.info("P= " + p);
        log.info(concurrentMap.toString());

        //replacing existing value, if the old-value = expected
        boolean b = concurrentMap.replace("Lenovo", 1200, 1900);
        log.info("b= " + b);
        log.info(concurrentMap.toString());

        concurrentMap.replace("Lenovo", 4500);
        log.info(concurrentMap.toString());

        //modify a map entry while traverse it.
        //this should cause a ConcurrentModificationException
        //the element "Lenovo" is removed, but also triggering an exception.
        log.info("map example::::::" + map.toString());
        try {
            map.forEach((k, v) -> {
                if (k.equals("Lenovo")) map.remove("Lenovo");
            });
        } catch (Exception e) {
            log.error("removing element as travers elements: ", e);
        }

        //it won't cause the error as working on the concurrent map
        log.info("concurrent map: " + concurrentMap.toString());
        concurrentMap.forEach((k, v) -> {
            if (k.equals("Lenovo")) concurrentMap.remove("Lenovo");
        });
        log.info("removing from concurrent map: " + concurrentMap.toString());

    }
}
