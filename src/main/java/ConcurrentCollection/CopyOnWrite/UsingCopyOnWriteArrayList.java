package ConcurrentCollection.CopyOnWrite;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class UsingCopyOnWriteArrayList {
    private static final List<Integer> integers = new CopyOnWriteArrayList<>();

    static {
        IntStream.rangeClosed(0, 1000).forEach(i -> integers.add(i));
    }

    public static void main(String[] args) {



    }
}
