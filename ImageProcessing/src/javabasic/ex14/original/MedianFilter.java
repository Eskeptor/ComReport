package javabasic.ex14.original; /***************************************************************************
  MedianFilter.java : This program performs median filtering on an image, using the specified
  neighbourhood dimensions.

  Example of use:  java MedianFilter input.jpg output.jpg 3 3
***************************************************************************/
import java.awt.image.*;

public class MedianFilter {
  public static void main(String[] argv) {
    if (argv.length > 3) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        int w = Integer.parseInt(argv[2]);
        int h = Integer.parseInt(argv[3]);
        BufferedImage inputImage = input.decodeAsBufferedImage();
        BufferedImageOp op = new MedianFilterOp(w, h);
        IntervalTimer timer = new IntervalTimer();
        timer.start();
        BufferedImage outputImage = op.filter(inputImage, null);
        System.out.println("Median filtering finished [" + timer.stop() + " sec]");
        output.encode(outputImage);
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println(
       "usage: java MedianFilter <infile> <outfile> <width> <height>");
      System.exit(1);
    }
  }
}