package List;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author YNZ
 */
public class ReverseAString {

    public static void main(String[] args) {

        String str = "I love it";
        char[] x = str.toCharArray();

        StringBuilder sb = new StringBuilder();

        for (int j = x.length - 1; j >=0; j--) {

            sb.append(x[j]);
        }

        System.out.print(sb);

        int[] y = new int[]{1,3,43,98,22};
        List<Integer> nums = Arrays.stream(y).boxed().collect(toList());
        Collections.reverse(nums);
        System.out.println(nums);


    }
}
