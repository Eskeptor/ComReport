package javabasic.ex14.original; /***************************************************************************
  Convolve.java : This program convolves an image with a kernel read from a file.  In addition 
  to filenames for the input image, output image and kernel, three integer parameters must be 
  specified.  The first is 0 if no normalisation of the kernel is required, or a non-zero value if
  kernel coefficients should be normalised on input.
  The next parameter specifies border processing behaviour:
    1 = no processing of border pixels (resulting in a black border)
    2 = copying of border pixels from the input image
    3 = reflected indexing
    4 = circular indexing
  The final parameter specifies how convolution output is rescaled:
    1 = no rescaling (data truncated to a 0-255 range)
    2 = rescale maximum only (symmetrically about zero if necessary)
    3 = rescale such that minimum becomes 0 and maximum becomes 255
***************************************************************************/
import java.awt.image.*;
import java.io.*;

public class Convolve {
  public static void main(String[] argv) {
    if (argv.length > 5) {
      try {
        // Parse command line arguments
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        Reader kernelInput = new FileReader(argv[2]);
        boolean normaliseKernel = (Integer.parseInt(argv[3]) != 0);
        int borderStrategy =  Math.max(1, Math.min(4, Integer.parseInt(argv[4])));
        int rescaleStrategy =  Math.max(1, Math.min(3, Integer.parseInt(argv[5])));

        // Load image and kernel
        BufferedImage inputImage = input.decodeAsBufferedImage();
        Kernel kernel = StandardKernel.createKernel(kernelInput, normaliseKernel);

        // Create convolution operator and convolve image
        ConvolutionOp convOp = new ConvolutionOp(kernel, borderStrategy, ConvolutionOp.SINGLE_PASS, rescaleStrategy);
        IntervalTimer timer = new IntervalTimer();
        timer.start();
        BufferedImage outputImage = convOp.filter(inputImage, null);
        System.out.println("Convolution finished [" + timer.stop() + " sec]");

        // Write results to output file
        output.encode(outputImage);
        System.exit(0);

      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java Convolve " +
       "<infile> <outfile> <kernel> <norm> <border> <rescale>");
      System.exit(1);
    }
  }
}
