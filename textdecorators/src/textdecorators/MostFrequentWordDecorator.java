package textdecorators;

import textdecorators.util.InputDetails;

public class MostFrequentWordDecorator extends AbstractTextDecorator {
  private final AbstractTextDecorator atd;
  private final InputDetails id;

  public MostFrequentWordDecorator(AbstractTextDecorator atd, InputDetails id) {
    this.atd = atd;
    this.id = id;
  }

  @Override
  public void processInputDetails() {
    // Decorate input details.

    // Forward to the next decorator, if any.
    if (null != atd) {
      atd.processInputDetails();
    }
  }
}
