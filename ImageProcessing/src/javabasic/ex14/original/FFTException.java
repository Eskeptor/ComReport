package javabasic.ex14.original;

/***************************************************************************
  FFTException.java
***************************************************************************/
/* Exception thrown by ImageFFT objects. */
public class FFTException extends OperationException {
  public FFTException() {}
  public FFTException(String message) { super(message); }
}