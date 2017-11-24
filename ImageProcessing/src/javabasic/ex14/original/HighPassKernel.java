package javabasic.ex14.original;

/***************************************************************************
  HighPassKernel.java
***************************************************************************/
public class HighPassKernel extends StandardKernel {
  private static final float[] data = { -1.0f, -1.0f, -1.0f,  -1.0f,  8.0f, -1.0f,  -1.0f, -1.0f, -1.0f  };
  /* Constructs a 3x3 kernel for high pass filtering. */
  public HighPassKernel() {
    super(3, 3, data, 0);
  }

  /* Creates a HighPassKernel and prints it to standard output. */
  public static void main(String[] argv) {
    StandardKernel kernel = new HighPassKernel();
    kernel.write(new java.io.OutputStreamWriter(System.out));
  }
}