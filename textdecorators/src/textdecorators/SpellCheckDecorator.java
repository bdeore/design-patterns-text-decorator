package textdecorators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import textdecorators._exceptions.EmptyInputFileException;
import textdecorators._exceptions.InvalidWordException;
import textdecorators.util.FileProcessor;
import textdecorators.util.InputDetails;

public class SpellCheckDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;
  private final Set<String> misspelledWords;

  public SpellCheckDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
    this.misspelledWords = new HashSet<>();
  }

  @Override
  public void processInputDetails() {

    makeKeywordSet();
    updateResults();

    if (null != atd) {
      atd.processInputDetails();
    }
  }

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

  private void makeKeywordSet() {
    try {
      FileProcessor keywordsFP = new FileProcessor(id.getMisspelledWordsFile());
      int count = 0;
      String line = keywordsFP.poll();

      if (line == null) throw new EmptyInputFileException();

      while (line != null) {
        count++;
        if (line.matches("[a-zA-Z0-9,\\s]*")) {
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

  public void printKeywordMap() {
    System.out.println();
    for (String keyword : this.misspelledWords) System.out.println(keyword);
  }
}
