package javabasic.ex14.original; /***************************************************************************
  CannyEdgeDetector.java : 
***************************************************************************/
import java.awt.image.*;

public class CannyTest {
  public static void main(String[] argv) {
    if (argv.length > 1) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        BufferedImage inputImage = input.decodeAsBufferedImage();
        //CannyEdgeOp op1 = new CannyEdgeOp(2.0f);
        //CannyEdgeOp op2 = new CannyEdgeOp(2.0f, 10, 30);
        // CannyEdgeOp op3 = new CannyEdgeOp(2.0f, 10, 50, CannyEdgeOp.ABS_MAGNITUDES);
        CannyEdgeOp op = new CannyEdgeOp(2.0f, 10, 30);
        BufferedImage edgeMap = op.filter(inputImage, null);
        BufferedImage magImage = op.getGradientMagnitudeImage();
        BufferedImage orientImage = op.getGradientOrientationImage();
        // output.encode(op.filter(edgeMap, null));
        // output.encode(op.filter(magImage, null));
        output.encode(op.filter(orientImage, null));
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java CannyEdgeDetector <infile> <outfile>");
      System.exit(1);
    }
  }
}