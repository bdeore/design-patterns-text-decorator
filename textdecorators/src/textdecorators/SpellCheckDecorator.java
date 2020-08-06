package textdecorators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import textdecorators._exceptions.EmptyInputFileException;
import textdecorators._exceptions.InvalidWordException;
import textdecorators.util.FileProcessor;
import textdecorators.util.InputDetails;

/* decorator that checks for words with spelling mistakes. misspelled words from misspelled.txt
 * file are stored in a hashset to enable constant time lookup. also prefixes and suffixes words
 * with SPELLCHECK on match */
public class SpellCheckDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;
  private final Set<String> misspelledWords;

  /**
   * constructor for SpellCheckDecorator class
   *
   * @param atd AbstractTextDecorator to pass control forward
   * @param id instance of input details class
   */
  public SpellCheckDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
    this.misspelledWords = new HashSet<>();
  }

  /** method to find misspelled words and prefix, suffix them with SPELLCHECK */
  @Override
  public void processInputDetails() {

    makeSpellcheckSet();
    updateResults();

    if (null != atd) {
      atd.processInputDetails();
    }
  }

  /**
   * method to update resultBuffer in InputDetails class. traverses whole arraylist and checks if
   * word is contained in misspelledWords hashset. if found, prefixes and suffixes the word.
   */
  private void updateResults() {
    ArrayList<ArrayList<String>> reference = id.getReference();
    ArrayList<ArrayList<String>> results = id.getResult();
    for (int i = 0; i < results.size(); i++) {
      ArrayList<String> sentence = reference.get(i);
      ArrayList<String> modifiedSentence = results.get(i);

      for (int j = 0; j < sentence.size(); j++) {
        if (misspelledWords.contains(sentence.get(j).toLowerCase())) {
          String word = modifiedSentence.get(j);
          word = "SPELLCHECK_" + word + "_SPELLCHECK";
          modifiedSentence.set(j, word);
        }
      }
    }
  }

  /**
   * method to make hashset containing misspelled words. file name for misspelled.txt file is read
   * in from input details class instance.
   */
  private void makeSpellcheckSet() {
    try {
      FileProcessor keywordsFP = new FileProcessor(id.getMisspelledWordsFile());
      int count = 0;
      String line = keywordsFP.poll();

      if (line == null) throw new EmptyInputFileException();

      while (line != null) {
        count++;
        if (line.matches("[a-zA-Z0-9,.\\s]*")) {
          misspelledWords.add(line.toLowerCase());
        } else {
          throw new InvalidWordException(
              "[ Line Number "
                  + count
                  + " ] -> [ "
                  + line
                  + " ] Please Ensure Input File contains Valid Lines");
        }
        line = keywordsFP.poll();
      }

    } catch (EmptyInputFileException | IOException | InvalidWordException e) {
      System.out.println(e);
      System.out.println("Terminating Program");
      System.exit(1);
    }
  }

  /** utility method to print all the misspelled words in hashset. useful for debugging */
  public void printSpellcheckMap() {
    System.out.println();
    for (String keyword : this.misspelledWords) System.out.println(keyword);
  }

  /**
   * toString method for debugging
   *
   * @return String of debugging information
   */
  @Override
  public String toString() {
    return "SpellCheckDecorator{"
        + "atd="
        + atd
        + ", id="
        + id
        + ", misspelledWords="
        + misspelledWords
        + '}';
  }
}
