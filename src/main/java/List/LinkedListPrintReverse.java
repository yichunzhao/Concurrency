package List;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author YNZ
 */
public class LinkedListPrintReverse {
    public static void main(String... args) {
        LinkedList<Integer> listNum = Stream.of(1, 2, 3, 67, 99, 129).collect(Collectors.toCollection(LinkedList::new));
        Collections.reverse(listNum);
        listNum.forEach(System.out::println);

        int r = Collections.binarySearch(listNum,99);
        System.out.println(r);

    }
}
