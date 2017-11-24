package javabasic.ex14.original; /***************************************************************************
  ExpOp.java
***************************************************************************/
/**
 * Performs exponential remapping of grey levels in a BufferedImage.
 */

public class ExpOp extends GreyMapOp {
  private static final double SCALE = Math.log(256.0)/255.0;

  /**
   * Constructs an ExpOp that will map 0 to 0 and 255 to 255.
   */
  public ExpOp() {
    computeMapping();
  }

  /**
   * Constructs an ExpOp that will map the specified limits onto 0 and 255.
   * @param low lower limit, mapping to 0
   * @param high upper limit, mapping to 255
   */
  public ExpOp(int low, int high) {
    computeMapping(low, high);
  }

  /**
   * Computes a mapping such that the specified limits map onto 0 and 255.
   * @param low lower limit, mapping onto 0
   * @param high upper limit, mapping onto 255
   */
  public void computeMapping(int low, int high) {
    if (low < 0 || high > 255 || low >= high)
      throw new java.awt.image.ImagingOpException("invalid mapping limits");
    double a = Math.exp(SCALE*low) - 1.0;
    double b = Math.exp(SCALE*high) - 1.0;
    double scaling = 255.0 / (b - a);
    for (int i = 0; i < 256; ++i) {
      int value = (int) Math.round(scaling*(Math.exp(SCALE*i) - 1.0 - a));
      setTableEntry(i, value);
    }
  }
}
