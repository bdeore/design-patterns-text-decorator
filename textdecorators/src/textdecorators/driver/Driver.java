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

    try {
      System.out.println("hello world!");

      InputDetails inputD = new InputDetails(args[0], args[1], args[2], args[3]);
      inputD.processFiles();

      AbstractTextDecorator sentenceDecorator = new SentenceDecorator(null, inputD);
      AbstractTextDecorator spellCheckDecorator =
          new SpellCheckDecorator(sentenceDecorator, inputD);
      AbstractTextDecorator keywordDecorator = new KeywordDecorator(spellCheckDecorator, inputD);
      AbstractTextDecorator mostFrequentWordDecorator =
          new MostFrequentWordDecorator(keywordDecorator, inputD);

      mostFrequentWordDecorator.processInputDetails();

      inputD.printResults();

      inputD.write();
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
}
