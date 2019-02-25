package List;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author YNZ
 */
public class DuplicateElementInList {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(1);

        System.out.println(numbers);

        List<Integer> numberList = Stream.of(1, 2, 3).collect(toList());
        numberList.forEach(System.out::println);
    }
}
