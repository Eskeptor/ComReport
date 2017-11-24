package javabasic.ex14.original;

/***************************************************************************
  EqualiseOp.java
***************************************************************************/
/* Performs histogram equalisation on a BufferedImage. */

public class EqualiseOp extends GreyMapOp {
  /* Constructs an EqualiseOp object using histogram data.
   * param hist : Histogram of the image to be equalised
   * exception HistogramException if the histogram is from a colour image. */

  public EqualiseOp(Histogram hist) throws HistogramException {
    float scale = 255.0f / hist.getNumSamples();
    for (int i = 0; i < 256; ++i)
      table[i] = (byte) Math.round(scale*hist.getCumulativeFrequency(i));
  }

  public void computeMapping(int low, int high) {
    // Does nothing - limits are meaningless in histogram equalisation
  }
}
