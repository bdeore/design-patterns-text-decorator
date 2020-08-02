package textdecorators._exceptions;

/** user-defined exception class - this exception is thrown if input file is empty */
public class EmptyInputFileException extends Exception {

  public EmptyInputFileException() {
    super("Input File Is Empty");
  }

  public EmptyInputFileException(String message) {
    System.out.println("EmptyInputFileException: " + message + " File Is Empty");
  }
}
