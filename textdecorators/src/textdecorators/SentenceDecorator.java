package textdecorators;

import java.util.ArrayList;
import textdecorators.util.InputDetails;

public class SentenceDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;

  public SentenceDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
  }

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
}
