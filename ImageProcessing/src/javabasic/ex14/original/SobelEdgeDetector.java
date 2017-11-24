package javabasic.ex14.original; /***************************************************************************
  SobelEdgeDetector.java : This program detects edges by computing gradient magnitude using
  the Sobel kernels and then (optionally) thresholding the result.

  Examples of use:
    java SobelEdgeDetector input.png output.png
    java SobelEdgeDetector input.png output.png 150
***************************************************************************/
import java.awt.image.*;

public class SobelEdgeDetector {
  public static void main(String[] argv) {
    if (argv.length > 1) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        int threshold = -1;
        if (argv.length > 2)
          threshold = Integer.parseInt(argv[2]);
        BufferedImage inputImage = input.decodeAsBufferedImage();
        SobelEdgeOp edgeOp = new SobelEdgeOp(threshold);
        output.encode(edgeOp.filter(inputImage, null));
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println(
       "usage: java SobelEdgeDetector <infile> <outfile> [<threshold>]");
      System.exit(1);
    }
  }
}