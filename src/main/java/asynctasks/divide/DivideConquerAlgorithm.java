package asynctasks.divide;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

class RecursiveSum extends RecursiveTask<Long> {
    private int low, high;

    public RecursiveSum(int low, int high) {
        this.low = low;
        this.high = high;
    }

    @Override
    protected Long compute() {

        if (high - low < 100_000) {
            return LongStream.rangeClosed(low, high).sum();
        } else {
            int mid = (high + low) / 2;

            //divide into left and right sub-problem.
            RecursiveSum left = new RecursiveSum(low, mid);
            RecursiveSum right = new RecursiveSum(mid + 1, high);
            left.fork();//left fork and implemented

            return right.compute() + left.join();
        }

    }
}

/**
 * Divide and Conquer Algorithms
 * A big task can be divided into independent sub-tasks and carried out in separate threads.
 * A big task can be divided recursively until a minimum size.
 * if "base-case" meaning that the problem is small enough, then go ahead to solve it.
 * else partition problem into "left" and "right" sub-problems.
 */
@Slf4j
public class DivideConquerAlgorithm {
    public static void main(String[] args) {

        Instant start = Instant.now();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Long total = pool.invoke(new RecursiveSum(0, 1_000_000_000));
        Instant end = Instant.now();

        pool.shutdown();
        log.info("Total sum is : " + total);
        log.info("Time cost: "+ Duration.between(start,end).toMillis());
    }
}
