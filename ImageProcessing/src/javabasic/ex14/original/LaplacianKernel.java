package javabasic.ex14.original;

/***************************************************************************
  LaplacianKernel.java
***************************************************************************/
/*  A Kernel to compute the Laplacian of an image. */
public class LaplacianKernel extends StandardKernel {
  private static final float[] data = {  0.0f, -1.0f,  0.0f,  -1.0f,  4.0f, -1.0f,  0.0f, -1.0f,  0.0f  };
  public LaplacianKernel() {  super(3, 3, data, 0); }
  /* Creates a LaplacianKernel and prints it on standard output. */
  public static void main(String[] argv) {
    StandardKernel kernel = new LaplacianKernel();
    kernel.write(new java.io.OutputStreamWriter(System.out));
  }
}