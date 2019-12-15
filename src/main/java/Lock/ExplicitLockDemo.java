package Lock;
/**
 * Locks(explicit lock): java.util.concurrent.locks. synchronize modifier is an implicit lock. Using
 * explicit lock vs implicit lock. Explicit lock: (1) providing a fairness config. so that longest
 * waiting thread gets the lock first.(by default); otherwise, config via the constructor. (2)
 * explicit lock offers a try lock method. if cannot achieve the lock and may time out, get back. 3)
 * explicit can be lock many times and unlock many times. 4) an explicit lock can be locked in one
 * method, and unlocked in another method.
 *
 * <p>for an implicit (synchronized) method or block, once a thread try to achieve its lock, then it
 * cannot quit.
 *
 * <p>why the ReentrantLock is called Reentrant? once a thread achieve the lock it may re-use this
 * lock in a recursive call. that is the reason.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Task1 implements Runnable {
  private Resources resources;

  public Task1(Resources resources) {
    this.resources = resources;
  }

  @Override
  public void run() {
    resources.writeToMap("1", Double.valueOf(3.14));
  }
}

class Task2 implements Runnable {
  private Resources resources;
  private String key = "1";

  public Task2(Resources resources) {
    this.resources = resources;
  }

  @Override
  public void run() {
    resources.readFromMap(key);
  }
}

class Resources {
  // lock can be fair lock or unfair lock.
  private static final Lock myLock = new ReentrantLock();

  // resource to be protected.
  private Map<String, Number> map = new HashMap<>();

  public void writeToMap(String key, Number value) {
    // acquiring an instance monitor and lock it.
    myLock.lock();
    map.putIfAbsent(key, value);

    // unlock to allow other thread to access the resource.
    myLock.unlock();
    System.out.println("task1 write value= " + value.toString());
  }

  public Number readFromMap(String key) {
    Number result;

    // only one thread is allowed to read and write at a moment.
    myLock.lock();
    result = map.get(key);
    myLock.unlock();

    System.out.println(
        result != null ? "task2 read result= " + result.toString() : "there is no value yet");
    return result;
  }
}

/** read operation can be concurrent; but write operation cannot be. */
public class ExplicitLockDemo {

  public static void main(String[] args) {
    // the common resource
    Resources myResource = new Resources();

    // workers(thread in a new state)
    Thread thread1 = new Thread(new Task1(myResource));
    Thread thread2 = new Thread(new Task2(myResource));

    // put thread in ready state.
    thread1.start();
    thread2.start();

    // thread scheduler pick up threads and make it runnable.

  }
}
