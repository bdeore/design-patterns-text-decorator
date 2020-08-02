package textdecorators.util;

/** Singleton class for logging debug information. maintains a buffer for storing debug messages */
public class MyLogger {
  private static Results uniqueInstance;

  private MyLogger() {}

  public static synchronized Results getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new Results();
    }
    return uniqueInstance;
  }
}
