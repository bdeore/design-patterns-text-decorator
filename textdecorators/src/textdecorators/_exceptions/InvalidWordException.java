package textdecorators._exceptions;

/**
 * user-defined exception class - this exception is thrown if invalid word is found in input file.
 */
public class InvalidWordException extends Exception {

  public InvalidWordException() {
    super("InvalidWordException : Please Ensure Input File contains Valid Lines");
  }

  public InvalidWordException(String message) {
    super(message);
  }
}
