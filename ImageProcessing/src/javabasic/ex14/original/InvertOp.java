package javabasic.ex14.original; /***************************************************************************
  InvertOp.java
***************************************************************************/
/**
 * Inverts grey levels in a BufferedImage.
 */

public class InvertOp extends GreyMapOp {

  public InvertOp() {
    computeMapping();
  }

  public InvertOp(int low, int high) {
    computeMapping(low, high);
  }

  public void computeMapping(int low, int high) {
    if (low < 0 || high > 255 || low >= high)
      throw new java.awt.image.ImagingOpException("invalid mapping limits");
    float scaling = -255.0f / (high - low);
    for (int i = 0; i < 256; ++i)
      setTableEntry(i, Math.round(scaling*(i - high)));
  }

}
