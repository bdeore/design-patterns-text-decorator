package textdecorators.util;

import java.nio.file.InvalidPathException;
import textdecorators._exceptions.EmptyInputFileException;

/** interface for standard out IO */
public interface StdoutDisplayInterface {

  void write() throws ArithmeticException, InvalidPathException, EmptyInputFileException;
}
