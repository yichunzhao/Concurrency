package asynctasks.divide;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

@Slf4j
public class SumByOneProcess {
    public static void main(String[] args) {
        long low = 0;
        long high = 1_000_000_000;

        Instant start = Instant.now();
        LongStream.rangeClosed(low, high).sum();
        Instant end = Instant.now();
        log.info("Time cost: " + Duration.between(start, end).toMillis());
    }
}
