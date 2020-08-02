package textdecorators.util;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import textdecorators._exceptions.EmptyInputFileException;

/** Interface for File IO */
public interface FileDisplayInterface {
  void write(String output_filename)
      throws ArithmeticException, InvalidPathException, IOException, EmptyInputFileException;
}
