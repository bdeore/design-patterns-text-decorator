package textdecorators.util;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

public class InputDetails implements FileDisplayInterface, StdoutDisplayInterface {

  private final String inputFile;
  private final String misspelledWordsFile;
  private final String keywordsFile;
  private final String outputFile;
  private final List<String> result;

  public InputDetails(
      String inputFile, String misspelledWordsFile, String keywordsFile, String outputFile) {
    this.inputFile = inputFile;
    this.misspelledWordsFile = misspelledWordsFile;
    this.keywordsFile = keywordsFile;
    this.outputFile = outputFile;
    this.result = new ArrayList<>();
  }

  @Override
  public void write(String output_filename)
      throws ArithmeticException, InvalidPathException, IOException {}

  @Override
  public void write() throws ArithmeticException, InvalidPathException {}

  public List<String> getResult() {
    return result;
  }
}
