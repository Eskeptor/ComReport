package javabasic.ex14.original; /***************************************************************************
  HistogramException.java
***************************************************************************/

/* Exception thrown if a Histogram cannot be computed from a BufferedImage. */
public class HistogramException extends OperationException {
  public HistogramException() {}
  public HistogramException(String message) { super(message); }
}
