package ConcurrentCollection.CopyOnWrite;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList is a List; a thread-safe collection.
 * CopyOnWriteSet is a Set; a thread-safe collection.
 * <p>
 * By using copy-on-write(COW) principle, without using traditional synchronisation or locking, it is still able to achieve
 * thread safety. It means when a resource invoker intend to modify the resource, it will get a copy of the resource.
 * <p>
 * CopyOnWriteArray is internally backup by an array, but it is read-only; a modification happens on a fresh copy of
 * the internal array. If it has fives threads add new elements into this list, at the same time it will create five
 * copy of the internal array. So if having a lot of adding operations, it could use a lot of resources.
 * <p>
 * removing via iterator is not supported, for it iterates on a copy. it throws java.lang.UnsupportedOperationException
 */

@Slf4j
public class UsingCopyOnWriteArrayList {
    private static final List<Integer> integers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 9});

    public static void main(String[] args) {

        log.info(integers.toString());

        Iterator<Integer> it = integers.iterator();
        integers.add(250);

        while (it.hasNext()) {
            System.out.printf("%d ", it.next());
        }
        System.out.println();
        //we won't see 250; for working a snapshot of internal array.

        Iterator<Integer> it1 = integers.iterator();
        while (it1.hasNext()) {
            System.out.printf("%d ", it1.next());
        }
        System.out.printf("");
        //we see 250

        //iterator remove is not supported;
        Iterator<Integer> it2 = integers.iterator();
        try {
            while (it2.hasNext()) {
                if (it2.next().equals(250)) it2.remove();
            }
        } catch (Exception e) {
            log.error("iterate remove:", e);
        }

    }
}
