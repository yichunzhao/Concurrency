

package CollectingToResult;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author YNZ
 */
public class StreamToArray {
    //collecting to an array
    public static void main(String... args) {

        List<String> strList = Stream.of(Pattern.compile("\\s").split("Java Generics and Collections"))
                .collect(Collectors.toList());

        String[] strArray = Stream.of(Pattern.compile("\\s").split("Java Generics and Collections"))
                .toArray(s -> new String[s]);

        System.out.println(Arrays.toString(strArray));

        String[] sortedStrArray = Stream.of(Pattern.compile("\\s").split("Java Generics and Collections"))
                .sorted(String::compareToIgnoreCase).toArray(String[]::new);
        System.out.println(Arrays.toString(sortedStrArray));


    }
}
