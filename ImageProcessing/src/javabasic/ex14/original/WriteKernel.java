package javabasic.ex14.original;

import java.io.OutputStreamWriter;

public class WriteKernel {
  public static void main(String[] argv) {
    float[] data = new float [25] ;
    float coeff = 0.04f;
    for (int i = 0; i < 25; ++i)
      data[i] = coeff;
    StandardKernel kernel = new StandardKernel(5, 5, data, 2);
    kernel.write(new OutputStreamWriter (System.out));
  }
}