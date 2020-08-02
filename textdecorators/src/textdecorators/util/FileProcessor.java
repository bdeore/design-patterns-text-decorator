package textdecorators.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * FileProcessor is a utility to be used to read in the contents of the input file.
 *
 * <p>DO NOT ALTER THIS FILE.
 *
 * @author Pradyumna Kaushik
 */
public final class FileProcessor {

  private BufferedReader reader;

  /**
   * Constructs a FileProcessor that can stream the contents of the provided input file line by
   * line.
   *
   * @throws InvalidPathException On invalid path string.
   * @throws SecurityException On not having necessary read permissions to the input file.
   * @throws FileNotFoundException On input file not found.
   * @throws IOException On any I/O errors while reading lines from input file.
   */
  public FileProcessor(String inputFilePath)
      throws InvalidPathException, SecurityException, FileNotFoundException, IOException {

    if (!Files.exists(Paths.get(inputFilePath))) {
      throw new FileNotFoundException("invalid input file or input file in incorrect location");
    }

    reader = new BufferedReader(new FileReader(new File(inputFilePath)));
  }

  /**
   * Retrieves and returns the next line in the input file.
   *
   * @return String The next line read from the input file.
   * @throws IOException On error encountered when reading from input file.
   */
  public String poll() throws IOException {
    return reader.readLine();
  }

  /**
   * Close the buffered reader instance.
   *
   * @throws IOException On error encountered when closing the buffered reader.
   */
  public void close() throws IOException {
    reader.close();
  }
}
