package textdecorators;

import java.util.ArrayList;
import textdecorators.util.InputDetails;

/* decorator that adds BEGIN_SENTENCE__ and __END_SENTENCE to all the sentences. works
 * on internal 2D arraylist */
public class SentenceDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;

  /**
   * constructor for SentenceDecorator class
   *
   * @param atd AbstractTextDecorator to pass control forward
   * @param id instance of input details class
   */
  public SentenceDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
  }

  /** method that prefixes and suffixes each sentence with BEGIN_SENTENCE AND END_SENTENCE */
  @Override
  public void processInputDetails() {
    ArrayList<ArrayList<String>> results = id.getResult();
    for (int i = 0; i < results.size(); i++) {
      ArrayList<String> sentence = results.get(i);
      String firstWord = sentence.get(0);
      firstWord = "BEGIN_SENTENCE__" + firstWord;
      sentence.set(0, firstWord);
      String lastWord = sentence.get(sentence.size() - 1);
      lastWord = lastWord + "__END_SENTENCE.";
      sentence.set((sentence.size() - 1), lastWord);
    }

    if (null != atd) {
      atd.processInputDetails();
    }
  }

  /**
   * toString method for debugging
   *
   * @return String of debugging information
   */
  @Override
  public String toString() {
    return "SentenceDecorator{" + "atd=" + atd + ", id=" + id + '}';
  }
}
