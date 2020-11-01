package ConcurrentCollection;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * When traversing a list, meanwhile modifying it; it causes ConcurrentModificationException
 * <p>
 * case one: it doesn't throw any exception
 * case two: it does.
 * case 3: it doesn't.
 * case 4: it does.
 * easy and simple way using removeIf
 */
@Slf4j
public class ConcurrentModificationExceptionDemo {

    public static void main(String[] args) {

        List<String> cities = new ArrayList<>(Arrays.asList("Copenhagen", "Pairs", "Berlin", "Beijing", "Shanghai"));
        log.info(cities.toString());

        //case 1
        for (String city : cities) {
            log.info("current city: " + city);
            if (city.equals("Beijing")) {
                log.info("remove action");
                cities.remove(city);
            }
        }
        log.info(cities.toString());

        //case 2
        cities.add("Los Angeles");
        try {
            for (String city : cities) {
                log.info("current city: " + city);
                if (city.contains(" ")) {
                    log.info("remove action:");
                    cities.remove(city);
                }
            }
        } catch (Exception e) {
            log.error("invoking contain and remove: ", e);
        }
        log.info(cities.toString());

        //case 3
        log.info("case 3");
        for (Iterator<String> it = cities.iterator(); it.hasNext(); ) {
            if (it.next().contains(" ")) it.remove();
        }
        log.info(cities.toString());

        //case 4
        log.info("case 4");
        try {
            for (Iterator<String> it = cities.iterator(); it.hasNext(); ) {
                String city = it.next();
                if (city.equals("Shanghai")) cities.remove(city);
            }
        } catch (Exception e) {
            log.info("case 4: ", e);
        }
        log.info(cities.toString());

        //case 5
        log.info("case 5");
        cities.add("my city?");
        log.info(cities.toString());
        cities.removeIf(city -> city.contains("?"));
        log.info(cities.toString());
    }
}
