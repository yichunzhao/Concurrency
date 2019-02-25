package Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author YNZ
 */
public class TreeMapUsing {

    public static void main(String... vars) {

        Map<String, Integer> hashMap = Stream.of(null, "Dell", "Lenovo", "Acer", "Hp", "Asus")
                .collect(Collectors.toMap(s -> s, v -> 1, (o, n) -> n, HashMap::new));

        System.out.println("hashMap: ");
        System.out.println(hashMap);

        Map<String, Integer> linkedHashMap = Stream.of(null, "Dell", "Lenovo", "Acer", "Hp", "Asus")
                .collect(Collectors.toMap(s -> s, v -> 1, (o, n) -> n, LinkedHashMap::new));

        System.out.println("linkedMap: ");
        System.out.println(linkedHashMap);

        Map<String, Integer> treeMap = new TreeMap<>();
        //map write time complexity o(1)a
        //map put without ordering
        treeMap.put("dell", 1);
        treeMap.put("lenovo", 1);  //the same hashcode; ->get the same index;
        treeMap.put("lenovo", 888);  //duplicated key will be overwritten
        treeMap.put("acer", 1);
        treeMap.put("hp", 1);
        treeMap.put("asus", 1);

        System.out.println("treeMap: ");
        System.out.println(treeMap);

        //map get time complexity o(1), the worst case o(n) or o(log(n)) in java 8
        System.out.println("tree: " + treeMap.toString());
        System.out.println("get a key/value: " + treeMap.get("lenovo"));

        //
        long num = Stream.of(Pattern.compile("\\s")
                .split("A processor's time is usually time-sliced")).count();
        System.out.println("num = " + num);

        Map<String, Integer> strLength = Stream.of(Pattern.compile("\\s")
                .split("A processor's time is usually time-sliced"))
                .collect(Collectors.toMap(x -> x, x -> x.length()));

        System.out.println("collected string map: " + strLength);

        Map<String, String> countries = Stream.of(Locale.getAvailableLocales())
                .collect(Collectors.toMap(
                        l -> l.getCountry(), //key mapper
                        l -> l.getDisplayCountry(), //value mapper
                        (o, n) -> o, //bi function
                        LinkedHashMap::new)); //supplier

        System.out.print(countries.toString());


    }
}
