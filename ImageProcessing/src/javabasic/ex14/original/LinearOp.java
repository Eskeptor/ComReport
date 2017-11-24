package javabasic.ex14.original; /***************************************************************************
  LinearOp.java
***************************************************************************/
/**
 * Performs linear mapping of grey levels in a BufferedImage.
 */

public class LinearOp extends GreyMapOp {
  /**
   * Constructs a LinearOp that will map 0 to 0 and 255 to 255.
   */

  public LinearOp() {
    computeMapping();
  }

  /**
   * Constructs a LinearOp that will map the specified limits onto 0 and 255.
   * @param low lower limit, mapping to 0
   * @param high upper limit, mapping to 255
   */

  public LinearOp(int low, int high) {
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
    float scaling = 255.0f / (high - low);
    for (int i = 0; i < 256; ++i)
      setTableEntry(i, Math.round(scaling*(i - low)));
  }
}
