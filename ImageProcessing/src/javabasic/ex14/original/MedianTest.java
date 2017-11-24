package javabasic.ex14.original; /***************************************************************************
  MedianTest.java
***************************************************************************/
import java.awt.image.*;

public class MedianTest {
  public static void main(String[] argv) {
    if (argv.length > 2) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        int w = Integer.parseInt(argv[1]);
        int h = Integer.parseInt(argv[2]);
        int median = (w*h/2)+1;
        BufferedImage inputImage = input.decodeAsBufferedImage();
        BufferedImageOp op = new RankFilterOp(median, w, h);
        IntervalTimer timer = new IntervalTimer();
        timer.start();
        BufferedImage outputImage = op.filter(inputImage, null);
        System.out.println("RankFilterOp   : " + timer.stop() + " sec");
        op = new MedianFilterOp(w, h);
        timer.start();
        outputImage = op.filter(inputImage, null);
        System.out.println("MedianFilterOp : " + timer.stop() + " sec");
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java MedianTest <infile> <width> <height>");
      System.exit(1);
    }
  }
}