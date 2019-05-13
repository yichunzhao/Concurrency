package SyncCollections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class SyncSet {

    public static void main(String[] args) throws InterruptedException {
        Set<Integer> set = new HashSet<>();
        Set<Integer> synchronisedSet = Collections.synchronizedSet(set);

        new Thread(() -> synchronisedSet.add(3)).start();
        new Thread(() -> synchronisedSet.add(4)).start();
        new Thread(() -> synchronisedSet.add(5)).start();

        Thread.sleep(500);
        System.out.println(synchronisedSet);

        //if not synchronised what will be?
        Set<Integer> opt = new HashSet<>();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> opt.add(i)).start());

        new Thread(() -> System.out.println(opt.contains(4))).start();

        Thread.sleep(500);
        System.out.println(opt);
    }
}
