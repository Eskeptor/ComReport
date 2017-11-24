package javabasic.ex14.original; /***************************************************************************
  MakeSinusoid.java  : This program generates synthetic images in which grey level has a
  sinusoidal variation horizontally, vertically or in both directions simultaneously. Six command 
  line parameters must be specified: an output filename; image width (a square image is generated); 
  the number of horizontal cycles; the number of vertical cycles; the amplitude (between 1 and 127); 
  and the phase.

  Example of use:  java MakeSinusoid sine.pgm 100 5 0 75 0
***************************************************************************/
import java.awt.image.*;

public class MakeSinusoid2 {
  public static double parseDouble(String s) {
    return Double.valueOf(s).doubleValue();
  }

  public static void main(String[] argv) {
    if (argv.length > 5) {
      try {
        ImageEncoder output = ImageFile.createImageEncoder(argv[0]);
        int n = Integer.parseInt(argv[1]);
        double u = parseDouble(argv[2]);
        double v = parseDouble(argv[3]);
        double a = Math.max(0, Math.min(127, parseDouble(argv[4])));
        double phi = parseDouble(argv[5]);
        BufferedImage image =  new BufferedImage(n, n, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = image.getRaster();
        double twoPi = 2.0*Math.PI;
        int value;
        for (int y = 0; y < image.getHeight(); ++y)
          for (int x = 0; x < image.getWidth(); ++x) {
            value = (int) Math.round(a*Math.sin((twoPi*(u*x+v*y)/(n/2-1))+phi));
            raster.setSample(x, y, 0, 128+value);
          }
        output.encode(image);
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java MakeSinusoid " +
       "<imagefile> <width> <u> <v> <amplitude> <phase>");
      System.exit(1);
    }
  }
}