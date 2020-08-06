package textdecorators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import textdecorators._exceptions.EmptyInputFileException;
import textdecorators._exceptions.InvalidWordException;
import textdecorators.util.FileProcessor;
import textdecorators.util.InputDetails;

/* decorator that checks for keywords in keywords.txt file. upon reading the file, keywords are
 * stored in a hashset to enable constant time lookup. also prefixes and suffixes words with
 * KEYWORD on match */
public class KeywordDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;
  private final Set<String> keywords;

  /**
   * constructor for KeywordDecorator class
   *
   * @param atd AbstractTextDecorator to pass control forward
   * @param id instance of input details class
   */
  public KeywordDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
    this.keywords = new HashSet<>();
  }

  /** method to find keywords and prefix, suffix them with KEYWORD */
  @Override
  public void processInputDetails() {

    makeKeywordSet();
    updateResults();

    if (null != atd) {
      atd.processInputDetails();
    }
  }

  /**
   * method to update resultBuffer in InputDetails class. traverses whole arraylist and checks if
   * word is contained in keywords hashset. if found, prefixes and suffixes the word.
   */
  private void updateResults() {
    ArrayList<ArrayList<String>> reference = id.getReference();
    ArrayList<ArrayList<String>> results = id.getResult();
    for (int i = 0; i < results.size(); i++) {
      ArrayList<String> sentence = reference.get(i);
      ArrayList<String> modifiedSentence = results.get(i);

      for (int j = 0; j < sentence.size(); j++) {
        if (keywords.contains(sentence.get(j).toLowerCase())) {
          String word = modifiedSentence.get(j);
          word = "KEYWORD_" + word + "_KEYWORD";
          modifiedSentence.set(j, word);
        }
      }
    }
  }

  /**
   * method to make hashset containing keywords. file name for keyword.txt file is read in from
   * input details class instance.
   */
  private void makeKeywordSet() {
    try {
      FileProcessor keywordsFP = new FileProcessor(id.getKeywordsFile());
      int count = 0;
      String line = keywordsFP.poll();

      if (line == null) throw new EmptyInputFileException();

      while (line != null) {
        count++;
        if (line.matches("[a-zA-Z0-9,.\\s]*")) {
          keywords.add(line.toLowerCase());
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

  /** utility method to print all the keywords in hashset. useful for debugging */
  public void printKeywordMap() {
    System.out.println();
    for (String keyword : this.keywords) System.out.println(keyword);
  }

  /**
   * toString method for debugging
   *
   * @return String of debugging information
   */
  @Override
  public String toString() {
    return "KeywordDecorator{" + "atd=" + atd + ", id=" + id + ", keywords=" + keywords + '}';
  }
}
