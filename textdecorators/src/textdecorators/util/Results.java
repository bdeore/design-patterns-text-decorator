package textdecorators.util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Vector;
import textdecorators._exceptions.EmptyInputFileException;

/**
 * Results Class: maintains a result buffer(vector of string objects) which is updated by State
 * classes through store() method. implements two overrides of write() method declared in
 * interfaces. when write() method without parameters is called, output is printed out on console.
 * when write(output_filename) is called, appropriate file is created and written to. write() method
 * exceptions are handled by calling code.
 */
public class Results implements FileDisplayInterface, StdoutDisplayInterface {

  private Vector<String> resultBuffer;

  /** parameterized constructor */
  public Results() {
    this.resultBuffer = new Vector<String>();
  }

  /**
   * method to write output and metrics on standard out. exceptions are handled by calling code in
   * Driver class.
   *
   * @throws ArithmeticException on divide by zero error
   * @throws InvalidPathException on invalid file path
   */
  @Override
  public void write() throws ArithmeticException, InvalidPathException {
    try {
      System.out.println("\nOutput: ");
      System.out.println("-----------------------------------");
      for (String line : resultBuffer) {
        System.out.println(line);
      }
      System.out.println("-----------------------------------");
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("(Class Results) Terminating Program");
      // e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * method to write output and metrics on standard out. exceptions are handled by calling code in
   * Driver class.
   *
   * @param output_filename - name of the output file
   * @throws ArithmeticException on divide by zero error
   * @throws InvalidPathException on invalid file path
   * @throws FileNotFoundException if cannot create and open the file e.g. if directory exists with
   *     same name
   */
  @Override
  public void write(String output_filename)
      throws ArithmeticException, InvalidPathException, IOException, FileNotFoundException,
          EmptyInputFileException {

    FileWriter output_file = null;
    try {
      output_file = new FileWriter(output_filename);
      for (String line : resultBuffer) {
        output_file.write(line + "\n");
      }
    } finally {
      if (output_file != null) {
        output_file.close();
      }
    }
  }

  /**
   * public method to store rotated string in the results buffer
   *
   * @param line - rotated line passed in WordRotator class
   */
  public void store(String line) {
    resultBuffer.add(line);
  }

  /**
   * getter for resultBuffer
   *
   * @return vector of rotated string objects
   */
  public Vector<String> getResultBuffer() {
    return resultBuffer;
  }

  /**
   * method to clear buffer (this simplified variable management in driver code)
   *
   * <p>deletes the old vector and creates new one
   */
  public void clearBuffer() {
    this.resultBuffer = new Vector<>();
  }

  /**
   * toString() method for debugging
   *
   * @return string
   */
  @Override
  public String toString() {
    return "\nResults : \n" + "resultBuffer => " + resultBuffer;
  }
}
