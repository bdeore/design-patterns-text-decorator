package textdecorators.util;

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
  private ArrayList<ArrayList<String>> result;

  public InputDetails(
      String inputFile, String misspelledWordsFile, String keywordsFile, String outputFile) {
    this.inputFile = inputFile;
    this.misspelledWordsFile = misspelledWordsFile;
    this.keywordsFile = keywordsFile;
    this.outputFile = outputFile;
    this.result = new ArrayList<>();
    this.reference = new ArrayList<>();
  }

  public void processFiles() throws IOException, EmptyInputFileException, InvalidWordException {
    FileProcessor inputFP = new FileProcessor(inputFile);
    int count = 0;
    String line = inputFP.poll();

    if (line == null) throw new EmptyInputFileException();

    while (line != null) {
      String[] sentences = line.split("[.]");
      for (String sentence : sentences) {
        String[] words = sentence.split("(\\s)");

        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> temp1 = new ArrayList<>();

        for (String word : words) {
          if (word.matches("[a-zA-Z0-9,.]*")) {
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
        result.add(temp1);
      }
      line = inputFP.poll();
    }
  }

  public ArrayList<ArrayList<String>> getResult() {
    return result;
  }

  public void setResult(ArrayList<ArrayList<String>> result) {
    this.result = result;
  }

  public void printResults() {
    for (ArrayList<String> sentence : result) {
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

  @Override
  public void write(String output_filename)
      throws ArithmeticException, InvalidPathException, IOException {}

  @Override
  public void write() throws ArithmeticException, InvalidPathException {}
}
