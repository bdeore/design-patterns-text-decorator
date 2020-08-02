package textdecorators.driver;

import textdecorators.AbstractTextDecorator;
import textdecorators.KeywordDecorator;
import textdecorators.MostFrequentWordDecorator;
import textdecorators.SentenceDecorator;
import textdecorators.SpellCheckDecorator;
import textdecorators.util.InputDetails;

public class Driver {

  private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 5;

  public static void main(String[] args) {
    /*
     * As the build.xml specifies multiple arguments other than input and output, in case the
     * argument value is not given java takes the default value specified in
     * build.xml. To avoid that, below condition is used
     */
    if ((args.length != 5) || (args[0].equals("${input}")) || (args[1].equals("${output}"))) {
      System.err.printf(
          "Error: Incorrect number of arguments. Program accepts %d arguments (minimum).",
          REQUIRED_NUMBER_OF_CMDLINE_ARGS);
      System.exit(0);
    }

    System.out.println("hello world!");

    InputDetails inputD = new InputDetails(args[0], args[1], args[2], args[3]);

    AbstractTextDecorator sentenceDecorator = new SentenceDecorator(null, inputD);
    AbstractTextDecorator spellCheckDecorator = new SpellCheckDecorator(sentenceDecorator, inputD);
    AbstractTextDecorator keywordDecorator = new KeywordDecorator(spellCheckDecorator, inputD);
    AbstractTextDecorator mostFrequentWordDecorator =
        new MostFrequentWordDecorator(keywordDecorator, inputD);

    mostFrequentWordDecorator.processInputDetails();

    inputD.write();
  }
}
