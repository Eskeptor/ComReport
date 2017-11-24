package javabasic.ex14.original;

import java.awt.image.BufferedImage;

public class Convert {
  public static void main(String[] argv) {
    if (argv.length > 1) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        BufferedImage image = input.decodeAsBufferedImage();
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        output.encode(image);
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java Convert <infile> <outfile>");
      System.exit(1);
    }
  }
}