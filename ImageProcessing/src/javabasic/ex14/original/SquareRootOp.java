package javabasic.ex14.original; /***************************************************************************
  SquareRootOp.java
***************************************************************************/
/**
 * Performs logarithmic mapping of grey levels in a BufferedImage.
 */

public class SquareRootOp extends GreyMapOp {
  /**
   * Constructs a SquareRootOp that will map 0 to 0 and 255 to 255.
   */

  public SquareRootOp() {
    computeMapping();
  }

  /**
   * Constructs a SquareRootOp that will map the specified
   * limits onto 0 and 255.
   * @param low lower limit, mapping to 0
   * @param high upper limit, mapping to 255
   */

  public SquareRootOp(int low, int high) {
    computeMapping(low, high);
  }

  /**
   * Computes a mapping that maps the specified limits onto 0 and 255.
   * @param low lower limit, mapping onto 0
   * @param high upper limit, mapping onto 255
   */
  public void computeMapping(int low, int high) {
    if (low < 0 || high > 255 || low >= high)
      throw new java.awt.image.ImagingOpException("invalid mapping limits");
    double a = Math.sqrt(low);
    double b = Math.sqrt(high);
    double scaling = 255.0 / (b - a);
    for (int i = 0; i < 256; ++i) {
      int value = (int) Math.round(scaling*(Math.sqrt(i) - a));
      setTableEntry(i, value);
    }
  }
}
