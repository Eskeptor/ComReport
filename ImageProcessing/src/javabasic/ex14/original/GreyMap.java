package javabasic.ex14.original; /***************************************************************************
  GreyMap.java  : This program maps grey levels in an image using the specified
  mapping operation:
     lin* = LinearOp
     inv* = InvertOp
     sq*  = SquareRootOp
     log* = LogOp
     exp* = ExpOp
     eq*  = EqualiseOp
  In addition to an operation, lower and upper limits for the mapping
  can be specified.  These will be mapped onto 0 and 255, respectively.
  If no limits are given, the minimum and maximum pixel values from
  the image will be used to compute the mapping.
  Examples of use:
    java GreyMap in.png out.png linear
    java GreyMap in.png out.png log 10 230
***************************************************************************/
import java.awt.image.*;

public class GreyMap {
  // Creates a GreyMapOp object given the name of an operation

  public static GreyMapOp createOp(String name, int low, int high)
   throws OperationException {
    if (name.startsWith("lin"))
      return new LinearOp(low, high);
    else if (name.startsWith("inv"))
      return new InvertOp(low, high);
    else if (name.startsWith("sq"))
      return new SquareRootOp(low, high);
    else if (name.startsWith("log"))
      return new LogOp(low, high);
    else if (name.startsWith("exp"))
      return new ExpOp(low, high);
    else
      throw new OperationException("no such operation");
  }

  // Finds minimum pixel value

  public static int minValue(BufferedImage image) {
    int value, minimum = 255;
    Raster raster = image.getRaster();
    for (int y = 0; y < image.getHeight(); ++y)
      for (int x = 0; x < image.getWidth(); ++x) {
        value = raster.getSample(x, y, 0);
        minimum = Math.min(value, minimum);
      }
    return minimum;
  }


  // Finds maximum pixel value
  public static int maxValue(BufferedImage image) {
    int value, maximum = 255;
    Raster raster = image.getRaster();
    for (int y = 0; y < image.getHeight(); ++y)
      for (int x = 0; x < image.getWidth(); ++x) {
        value = raster.getSample(x, y, 0);
        maximum = Math.max(value, maximum);
      }
    return maximum;
  }

  public static void main(String[] argv) {
    if (argv.length > 2) {
      try {
        // Load input image and set things up for output
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        BufferedImage image = input.decodeAsBufferedImage();
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        // Create the required GreyMapOp
        GreyMapOp op;
        if (argv[2].startsWith("eq"))
          op = new EqualiseOp(new Histogram(image));
        else if (argv.length > 4) {
          int low = Integer.parseInt(argv[3]);
          int high = Integer.parseInt(argv[4]);
          op = createOp(argv[2], low, high);
        }
        else {
          int minimum = minValue(image);
          int maximum = maxValue(image);
          op = createOp(argv[2], minimum, maximum);
        }
        // Process image and write result to output file
        output.encode(op.filter(image, null));
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java GreyMap <infile> <outfile> <op> [<low> <high>]");
      System.exit(1);
    }
  }
}
