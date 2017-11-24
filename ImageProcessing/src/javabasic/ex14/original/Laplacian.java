package javabasic.ex14.original; /***************************************************************************
  Laplacian.java : This program computes the Laplacian of an image, by convolution.  
  The output is shifted and scaled to lie in a 0-255 range, with a filter output of zero 
  represented by a grey level of 128.

  Example of use:   java Laplacian input.jpg output.jpg
***************************************************************************/
import java.awt.image.*;

public class Laplacian {
  public static void main(String[] argv) {
    if (argv.length > 1) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        BufferedImage image = input.decodeAsBufferedImage();
        Kernel kernel = new LaplacianKernel();
        BufferedImageOp op = new ConvolutionOp(
         kernel, NeighbourhoodOp.NO_BORDER_OP,
         ConvolutionOp.SINGLE_PASS, ConvolutionOp.RESCALE_MAX_ONLY);
        IntervalTimer timer = new IntervalTimer();
        timer.start();
        BufferedImage outputImage = op.filter(image, null);
        System.out.println("Laplacian filtering finished [" +
         timer.stop() + " sec]");
        output.encode(outputImage);
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java Laplacian <infile> <outfile>");
      System.exit(1);
    }
  }
}