package textdecorators.driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import textdecorators.AbstractTextDecorator;
import textdecorators.KeywordDecorator;
import textdecorators.MostFrequentWordDecorator;
import textdecorators.SentenceDecorator;
import textdecorators.SpellCheckDecorator;
import textdecorators._exceptions.EmptyInputFileException;
import textdecorators._exceptions.InvalidWordException;
import textdecorators.util.InputDetails;

/** @author Bhagwan Deore */
public class Driver {

  private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 5;

  public static void main(String[] args) {
    /*
     * As the build.xml specifies multiple arguments other than input and output, in case the
     * argument value is not given java takes the default value specified in
     * build.xml. To avoid that, below condition is used
     */
    if ((args.length != 5)
        || (args[0].equals("${input}"))
        || (args[1].equals("${misspelled}"))
        || (args[2].equals("${keywords}"))
        || (args[3].equals("${output}"))
        || (args[4].equals("${debug}"))) {
      System.err.printf(
          "Error: Incorrect number of arguments. Program accepts %d arguments (minimum).",
          REQUIRED_NUMBER_OF_CMDLINE_ARGS);
      System.exit(0);
    }

    try {

      /* class to to store, retrieve and update sentences. names for all the input files are
       * are stored in this class and are accessed by appropriate decorators */
      InputDetails inputD = new InputDetails(args[0], args[1], args[2], args[3]);

      /* utility method that populates the 2D arraylist and does some basic validations */
      inputD.processFiles();

      /* decorator that adds BEGIN_SENTENCE__ and __END_SENTENCE to all the sentences. works
       * on internal 2D arraylist */
      AbstractTextDecorator sentenceDecorator = new SentenceDecorator(null, inputD);

      /* decorator that checks for words with spelling mistakes. misspelled words from misspelled.txt
       * file are stored in a hashset to enable constant time lookup. also prefixes and suffixes words
       * with SPELLCHECK on match */
      AbstractTextDecorator spellCheckDecorator =
          new SpellCheckDecorator(sentenceDecorator, inputD);

      /* decorator that checks for keywords in keywords.txt file. upon reading the file, keywords are
       * stored in a hashset to enable constant time lookup. also prefixes and suffixes words with
       * KEYWORD on match */
      AbstractTextDecorator keywordDecorator = new KeywordDecorator(spellCheckDecorator, inputD);

      /* decorator that determines the most frequent word in the input file. a hashmap is used to
       * to store words as keys and frequency as value. also prefixes and suffixes words with
       * MOST_FREQUENT on match */
      AbstractTextDecorator mostFrequentWordDecorator =
          new MostFrequentWordDecorator(keywordDecorator, inputD);

      /* program execution starts from mostFrequentWordDecorator */
      mostFrequentWordDecorator.processInputDetails();

      /* writes data stored in the buffer to output file*/
      inputD.writeToFile();

      /* writes data stored in the buffer to terminal*/
      // inputD.write();

    } catch (InvalidPathException
        | FileNotFoundException
        | SecurityException
        | ArithmeticException
        | InvalidWordException
        | EmptyInputFileException e) {
      System.out.println(e);
      System.out.println("(Class Driver) Terminating Program");
      System.exit(1);
      // e.printStackTrace();
    } catch (IOException e) {
      System.out.println("IOException occurred in FileProcessor class\n" + e);
      System.exit(1);
      // e.printStackTrace();
    }
  }

  /**
   * toString method for debugging
   *
   * @return String of debugging information
   */
  @Override
  public String toString() {
    return "Driver Class";
  }
}
