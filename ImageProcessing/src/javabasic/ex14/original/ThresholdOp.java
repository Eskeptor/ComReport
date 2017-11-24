package javabasic.ex14.original; /***************************************************************************
  ThresholdOp.java
***************************************************************************/
/**
 * Performs thresholding of grey levels in a BufferedImage.
 */

public class ThresholdOp extends GreyMapOp {
  /**
   * Constructs a ThresholdOp with a single threshold.
   */

  public ThresholdOp(int threshold) {
    computeMapping(threshold, 255);
  }

  /**
   * Constructs a ThresholdOp from a pair of thresholds.
   */

  public ThresholdOp(int low, int high) {
    computeMapping(low, high);
  }

  /**
   * Computes a mapping from a single threshold.
   */

  public void setThreshold(int threshold) {
    computeMapping(threshold, 255);
  }

  /**
   * Computes a mapping from a pair of thresholds.
   */

  public void setThresholds(int low, int high) {
    computeMapping(low, high);
  }

  /**
   * Computes a mapping from a pair of thresholds.
   */

  public void computeMapping(int low, int high) {
    if (low < 0 || high > 255 || low >= high)
      throw new java.awt.image.ImagingOpException("invalid thresholds");
    int i;
    for (i = 0; i < low; ++i)
      table[i] = (byte) 0;
    for (; i <= high; ++i)
      table[i] = (byte) 255;
    for (; i < 256; ++i)
      table[i] = (byte) 0;
  }
}
