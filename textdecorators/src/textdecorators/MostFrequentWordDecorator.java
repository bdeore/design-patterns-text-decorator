package textdecorators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import textdecorators.util.InputDetails;

public class MostFrequentWordDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;
  private final Map<String, Integer> wordFrequency;

  public MostFrequentWordDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
    this.wordFrequency = new HashMap<>();
  }

  @Override
  public void processInputDetails() {
    String mostFrequentWord = findMostFrequentWord();
    updateResults(mostFrequentWord);

    if (null != atd) {
      atd.processInputDetails();
    }
  }

  private void updateResults(String mostFrequentWord) {
    ArrayList<ArrayList<String>> reference = id.getReference();
    ArrayList<ArrayList<String>> results = id.getResult();

    for (int i = 0; i < reference.size(); i++) {
      ArrayList<String> sentence = reference.get(i);
      ArrayList<String> modifiedSentence = results.get(i);

      for (int j = 0; j < sentence.size(); j++) {
        String word = sentence.get(j);
        String newWord = modifiedSentence.get(j);
        if (word.toLowerCase().equals(mostFrequentWord.toLowerCase())) {
          newWord = "MOST_FREQUENT_" + newWord + "_MOST_FREQUENT";
          modifiedSentence.set(j, newWord);
        }
      }
    }
  }

  private String findMostFrequentWord() {
    ArrayList<ArrayList<String>> sentences = id.getReference();

    for (ArrayList<String> sentence : sentences) {
      for (String word : sentence) {
        word = word.toLowerCase();
        if (!wordFrequency.containsKey(word)) wordFrequency.put(word, 1);
        else {
          int frequency = wordFrequency.get(word);
          wordFrequency.put(word, ++frequency);
        }
      }
    }

    int mostFrequentCount = (Collections.max(wordFrequency.values()));
    String mostFrequentWord = null;

    for (String key : wordFrequency.keySet()) {
      if (wordFrequency.get(key) == mostFrequentCount) {
        mostFrequentWord = key;
        break;
      }
    }
    return mostFrequentWord;
  }

  public void printFrequencyMap() {
    for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }
}
