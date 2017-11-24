package javabasic.ex14.original;

import java.awt.image.BufferedImage;
import com.sun.image.codec.jpeg.*;

public class JPEGQuantTable {

  // row indices of zigzag path through quantisation table

  public static final int[] row = {
    0, 0, 1, 2, 1, 0, 0, 1, 2, 3, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4,
    5, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4,
    3, 2, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 4, 5, 6, 7, 7, 6,
    5, 6, 7, 7
  };

  // column indices of zigzag path through quantisation table

  public static final int[] column = {
    0, 1, 0, 0, 1, 2, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1,
    0, 0, 1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4,
    5, 6, 7, 7, 6, 5, 4, 3, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 5, 6,
    7, 7, 6, 7
  };

  // Returns a set of JPEG parameters

  public static JPEGEncodeParam getParameters() {
    BufferedImage img = new BufferedImage(8, 8, BufferedImage.TYPE_BYTE_GRAY);
    return JPEGCodec.getDefaultJPEGEncodeParam(img);
  }

  // Prints zigzag-ordered quantisation values as a 2D matrix

  public static void printAsMatrix(int[] data) {
    int[][] table = new int[8][8];
    for (int i = 0; i < data.length; ++i)
      table[row[i]][column[i]] = data[i];
    System.out.println();
    for (int v = 0; v < 8; ++v) {
      for (int u = 0; u < 8; ++u)
        System.out.print(StringTools.rightJustify(table[v][u], 4));
      System.out.println();
    }
    System.out.println();
  }

  public static void main(String[] argv) {
    if (argv.length > 0) {
      float quality = Math.max(0.0f, Math.min(1.0f,
        Float.valueOf(argv[0]).floatValue()));
      JPEGEncodeParam parameters = getParameters();
      parameters.setQuality(quality, true);
      printAsMatrix(parameters.getQTable(0).getTable());
      System.exit(0);
    }
    else {
      System.err.println("usage: java QuantTable <quality>");
      System.exit(1);
    }
  }

}
