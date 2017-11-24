package javabasic.ex14.original;

/***************************************************************************
  GaussianKernel.java
***************************************************************************/
/* A Kernel for Gaussian blurring. */
public class GaussianKernel extends StandardKernel {
  /* Creates a Gaussian kernel with a default standard deviation of 1.0. */
  public GaussianKernel() { this(1.0f);  }
  /* Creates a Gaussian kernel with the specified standard deviation.
   * param sigma : standard deviation   */
  public GaussianKernel(float sigma) {
    super(getSize(sigma), getSize(sigma), createKernelData(sigma));
  }
  /* Computes kernel size for a given standard deviation.
   * param sigma : standard deviation
   * return kernel size, in pixels.   */
  public static int getSize(float sigma) {
    int radius = (int) Math.ceil(4.0f*sigma);
    return 2*radius+1;
  }

  /* Creates an array of samples from a 2D Gaussian function with the given standard deviation.
   * param sigma : standard deviation
   * return array of samples.   */
  public static float[] createKernelData(float sigma) {
    int n = (int) Math.ceil(4.0f*sigma);
    int size = 2*n+1;
    float[] data = new float[size*size];

    double r, s = 2.0*sigma*sigma;
    float norm = 0.0f;
    int i = 0;
    for (int y = -n; y <= n; ++y)
      for (int x = -n; x <= n; ++x, ++i) {
        r = Math.sqrt(x*x + y*y);
        data[i] = (float) Math.exp(-r*r/s);
        norm += data[i];
      }

    for (i = 0; i < size*size; ++i) 
      data[i] /= norm;

    return data;
  }

  /* Creates a GaussianKernel and writes its coefficients to standard output.*/
  public static void main(String[] argv) {
    float sigma = 1.0f;
    if (argv.length > 0)
      sigma = Float.valueOf(argv[0]).floatValue();
    StandardKernel kernel = new GaussianKernel(sigma);
    kernel.write(new java.io.OutputStreamWriter(System.out));
  }
}