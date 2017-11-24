package javabasic.ex14.original;

/***************************************************************************
  OperationException.java
***************************************************************************/
/* Exception thrown by classes performing image processing operations. */

public class OperationException extends Exception {
  public OperationException() {}
  public OperationException(String message) { super(message); }
}
