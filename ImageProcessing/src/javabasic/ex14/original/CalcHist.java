package javabasic.ex14.original; /***************************************************************************
  CalcHist.java  :  This program computes the histogram of an image loaded from a file
  specified on the command line and writes the histogram to a new file
  (also specified on the command line).  If a third filename is
  specified, then the cumulative histogram is written to that file.
***************************************************************************/
import java.awt.image.BufferedImage;
import java.io.FileWriter;

public class CalcHist {
  public static void main(String[] argv) {
    if (argv.length > 1) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        BufferedImage image = input.decodeAsBufferedImage();
        Histogram histogram = new Histogram(image);
        FileWriter histFile = new FileWriter(argv[1]);
        histogram.write(histFile);
        if (argv.length > 2) {
          FileWriter cumHistFile = new FileWriter(argv[2]);
          histogram.writeCumulative(cumHistFile);
        }
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println(
       "usage: java CalcHist <imageFile> <histFile> [<cumHistFile>]");
      System.exit(1);
    }
  }
}
