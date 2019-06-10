package Factory;

/** @author YNZ */
interface Logger {
  public void info(String info);
}

class AsyncLogger implements Logger {
  @Override
  public void info(String info) {

    System.out.println("Async logger .... " + info);
  }
}

abstract class LoggerFactory {
  abstract Logger getLogger();

  public Logger getInstance() {
    return getLogger();
  }
}

class AsyncLoggerFactory extends LoggerFactory {

  @Override
  Logger getLogger() {
    return new AsyncLogger();
  }
}

public class UsingLogger {
  public static void main(String... strings) {

    AsyncLoggerFactory asyncLoggerFactory = new AsyncLoggerFactory();
    asyncLoggerFactory.getInstance().info("xxxxxx");
  }
}
