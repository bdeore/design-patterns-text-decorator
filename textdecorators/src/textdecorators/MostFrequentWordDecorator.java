package textdecorators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import textdecorators.util.InputDetails;

/* decorator that determines the most frequent word in the input file. a hashmap is used to
 * to store words as keys and frequency as value. also prefixes and suffixes words with
 * MOST_FREQUENT on match */
public class MostFrequentWordDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;
  private final Map<String, Integer> wordFrequency;

  /**
   * constructor for MostFrequentWordDecorator class
   *
   * @param atd AbstractTextDecorator to pass control forward
   * @param id instance of input details class
   */
  public MostFrequentWordDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
    this.wordFrequency = new HashMap<>();
  }

  /** method to find most frequent word and prefix, suffix all occurrences with MOST_FREQUENT */
  @Override
  public void processInputDetails() {
    String mostFrequentWord = findMostFrequentWord();
    updateResults(mostFrequentWord);

    if (null != atd) {
      atd.processInputDetails();
    }
  }

  /**
   * method to update resultBuffer in InputDetails class. traverses whole arraylist and checks if
   * word is the same as most frequent word. if found to be the same, prefixes and suffixes the
   * word.
   */
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

  /**
   * method to find the most frequent word in the input file. makes use of hashmap to keep track of
   * number of times each word occurs in input file.
   *
   * @return String most frequent word
   */
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

  /**
   * utility method to print all the words and their frequency stored in hashmap. useful for
   * debugging.
   */
  public void printFrequencyMap() {
    for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }

  /**
   * toString method for debugging
   *
   * @return String of debugging information
   */
  @Override
  public String toString() {
    return "MostFrequentWordDecorator{"
        + "atd="
        + atd
        + ", id="
        + id
        + ", wordFrequency="
        + wordFrequency
        + '}';
  }
}
