package textdecorators.util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import textdecorators._exceptions.EmptyInputFileException;
import textdecorators._exceptions.InvalidWordException;

public class InputDetails implements FileDisplayInterface, StdoutDisplayInterface {

  private final String inputFile;
  private final ArrayList<ArrayList<String>> reference;
  private final String misspelledWordsFile;
  private final String keywordsFile;
  private final String outputFile;
  private ArrayList<ArrayList<String>> resultBuffer;

  public InputDetails(
      String inputFile, String misspelledWordsFile, String keywordsFile, String outputFile) {
    this.inputFile = inputFile;
    this.misspelledWordsFile = misspelledWordsFile;
    this.keywordsFile = keywordsFile;
    this.outputFile = outputFile;
    this.resultBuffer = new ArrayList<>();
    this.reference = new ArrayList<>();
  }

  public void processFiles() throws IOException, EmptyInputFileException, InvalidWordException {
    FileProcessor inputFP = new FileProcessor(inputFile);
    int count = 0;
    String line = inputFP.poll();

    if (line == null) throw new EmptyInputFileException();

    while (line != null) {
      String[] sentences = line.split("\\.\\B");
      for (String sentence : sentences) {
        String[] words = sentence.split("\\s\\b");

        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> temp1 = new ArrayList<>();

        for (String word : words) {
          if (word.matches("[a-zA-Z0-9,.\\s]*")) {
            temp.add(word);
            temp1.add(word);

          } else {
            throw new InvalidWordException(
                "[ Line Number "
                    + count
                    + " ] -> [ "
                    + word
                    + " ] Please Ensure Input File contains Valid Lines");
          }
        }
        reference.add(temp);
        resultBuffer.add(temp1);
      }
      line = inputFP.poll();
    }
  }

  public ArrayList<ArrayList<String>> getResult() {
    return resultBuffer;
  }

  public void setResult(ArrayList<ArrayList<String>> result) {
    this.resultBuffer = result;
  }

  public void printResults() {
    for (ArrayList<String> sentence : resultBuffer) {
      for (String word : sentence) {
        System.out.print(" " + word);
      }
    }
  }

  public ArrayList<ArrayList<String>> getReference() {
    return reference;
  }

  public String getMisspelledWordsFile() {
    return misspelledWordsFile;
  }

  public String getKeywordsFile() {
    return keywordsFile;
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
      for (ArrayList<String> sentence : resultBuffer) {
        for (String word : sentence) {
          if (word.endsWith(".")) {
            System.out.print(word);
          } else {
            System.out.print(word + " ");
          }
        }
      }
      System.out.println();
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
   * @throws ArithmeticException on divide by zero error
   * @throws InvalidPathException on invalid file path
   * @throws FileNotFoundException if cannot create and open the file e.g. if directory exists with
   *     same name
   */
  @Override
  public void writeToFile()
      throws ArithmeticException, InvalidPathException, IOException, FileNotFoundException,
          EmptyInputFileException {

    FileWriter output_file = null;
    try {
      output_file = new FileWriter(outputFile);
      for (ArrayList<String> sentence : resultBuffer) {
        for (String word : sentence) {
          if (word.endsWith(".")) {
            output_file.write(word);
          } else {
            output_file.write(word + " ");
          }
        }
      }
    } finally {
      if (output_file != null) {
        output_file.close();
      }
    }
  }
}
