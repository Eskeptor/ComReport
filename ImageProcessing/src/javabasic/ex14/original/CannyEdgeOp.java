package javabasic.ex14.original; /***************************************************************************
  CannyEdgeOp.java
***************************************************************************/
import java.awt.image.*;

/* Performs Canny edge detection on an image. */
public class CannyEdgeOp extends StandardGreyOp {
  ///////////////////////////// CLASS CONSTANTS ////////////////////////////
  public static final int SQRT_MAGNITUDES = 1;
  public static final int ABS_MAGNITUDES = 2;
  private static final double[] SECTOR_ANGLE =
   { 0.125*Math.PI, 0.375*Math.PI, 0.625*Math.PI, 0.875*Math.PI };
  /////////////////////////// INSTANCE VARIABLES ///////////////////////////
  private float filterSize;
  private int lowThreshold = -1;
  private int highThreshold = -1;
  private int magCalcMethod = SQRT_MAGNITUDES;
  private Kernel kernel;
  private int width;
  private int height;
  private WritableRaster magnitude;
  private WritableRaster orientation;
  ///////////////////////////// PUBLIC METHODS /////////////////////////////
  /* Creates a CannyEdgeOp with the specified Gaussian filter size.
   * Gradient data will not be thresholded.
   * param size : standard deviation of Gaussian */
  public CannyEdgeOp(float size) {
    setFilterSize(size);
  }

  /* Creates a CannyEdgeOp with the specified Gaussian filter size and a pair of thresholds.
   * If the thresholds are equal, ordinary thresholding of gradient data takes place; if they differ,
   * hysteresis thresholding is used.
   * param size : standard deviation of Gaussian
   * param low : lower threshold
   * param high : upper threshold   */
  public CannyEdgeOp(float size, int low, int high) {
    this(size); setThresholds(low, high);
  }

  /* Creates a CannyEdgeOp with the specified Gaussian filter size, thresholds and gradient 
   * magnitude calculation method.  If the thresholds are equal, ordinary thresholding of gradient 
   * magnitude takes place; if they differ, hysteresis thresholding is used.
   * param size : standard deviation of Gaussian
   * param low : lower threshold
   * param high : upper threshold
   * param calcMethod calculation method  */
  public CannyEdgeOp(float size, int low, int high, int calcMethod) {
    this(size, low, high);  magCalcMethod = calcMethod;
  }

  /* return size of Gaussian low pass filter used for smoothing.  */
  public float getFilterSize() {
    return filterSize;
  }

  /* return lower threshold applied to gradient magnitudes. */
  public int getLowThreshold() {
    return lowThreshold;
  }

  /* return upper threshold applied to gradient magnitudes. */
  public int getHighThreshold() {
    return highThreshold;
  }

  /* return gradient magnitude calculation method. */
  public int getMagnitudeCalculationMethod() {
    return magCalcMethod;
  }

  /* return gradient magnitude image generated by last call to the filter() method.*/
  public BufferedImage getGradientMagnitudeImage() {
    if (magnitude == null)
      throw new ImagingOpException("no gradient magnitude data available");
    BufferedImage magImage =
     new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    magImage.setData(magnitude);
    return magImage;
  }

  /* return gradient orientation image generated by last call to the filter() method. */
  public BufferedImage getGradientOrientationImage() {
    if (orientation == null)
      throw new ImagingOpException("no gradient orientation data available");
    BufferedImage orientImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    orientImage.setData(orientation);
    return orientImage;
  }

  /* Performs Canny edge detection on an image.
   * param src : source image
   * param dest : destination image, or null
   * return edge map or gradient magnitudes */
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    checkImage(src);
    if (dest == null) dest = createCompatibleDestImage(src, null);
    width = src.getWidth();  height = src.getHeight();
    Raster raster = src.getRaster();
    magnitude = raster.createCompatibleWritableRaster();
    orientation = raster.createCompatibleWritableRaster();

    computeGradient(src);
    performNonMaximalSuppression();
    if (lowThreshold > 0 && highThreshold > 0) {
      if (lowThreshold == highThreshold)  applyHighThreshold(dest);
      else
        performHysteresisThresholding(dest);
    }
    return dest;
  }

  /////////////////////// PRIVATE & PROTECTED METHODS //////////////////////
  /* Sets size of Gaussian low pass filter and creates a separable kernel of that size.
   * param size : standard deviation of Gaussian
   * exception ImagingOpException if size is not greater than zero. */
  private void setFilterSize(float size) {
    if (size <= 0.0f)
      throw new ImagingOpException("filter size must be greater than zero");
    filterSize = size;
    kernel = new SeparableGaussianKernel(size);
  }

  /* Sets thresholds applied to gradient magnitude data.
   * param low : lower threshold
   * param high : upper threshold
   * exception ImagingOpException if thresholds are invalid. */
  private void setThresholds(int low, int high) {
    if (low < 1 || high > 255 || low > high)
      throw new ImagingOpException("invalid thresholds");
    lowThreshold = low;  highThreshold = high;
  }

  /* Computes gradient magnitude and orientation in the specified image.
   * param image : source image   */
  protected void computeGradient(BufferedImage image) {
    ConvolutionOp op = new ConvolutionOp(kernel,
     NeighbourhoodOp.REFLECTED_INDEXING,
     ConvolutionOp.SEPARABLE,
     ConvolutionOp.NO_RESCALING);
    float[] data = op.separableConvolve(image);

    float[] mag = new float[width*height];
    float gx, gy, g, gmax = 0.0f;
    for (int y = 1; y < height-1; ++y)
      for (int x = 1; x < width-1; ++x) {
        gx = xGradient(data, x, y);
        gy = yGradient(data, x, y);
        if (magCalcMethod == ABS_MAGNITUDES)  g = Math.abs(gx) + Math.abs(gy);
        else  g = (float) Math.sqrt(gx*gx + gy*gy);
        if (g > gmax)  gmax = g;
        mag[y*width + x] = g;
        orientation.setSample(x, y, 0, sector(gx, gy));
      }

    float scale = 255.0f/gmax;
    int i = 0;
    for (int y = 0; y < height; ++y)
      for (int x = 0; x < width; ++x, i++)
        magnitude.setSample(x, y, 0, Math.round(scale*mag[i]));
  }

  /* Computes gradient in the x direction. */
  private final float xGradient(float[] data, int x, int y) {
    return data[y*width+x+1] - data[y*width+x-1];
  }

  /* Computes gradient in the y direction. */
  private final float yGradient(float[] data, int x, int y) {
    return data[(y+1)*width+x] - data[(y-1)*width+x];
  }

  /* Computes orientation of a vector given its x and y components.
   * return orientation, as a sector between 1 and 4.   */
  private int sector(float x, float y) {
    double theta = Math.abs(Math.atan2(y, x));
    for (int i = 0; i < 4; ++i)
      if (theta < SECTOR_ANGLE[i])
        return i+1;
    return 1;
  }

  /* Peforms non-maximal suppression of gradient magnitude data. */
  protected void performNonMaximalSuppression() {
    WritableRaster result = magnitude.createCompatibleWritableRaster();
    int g, sector;
    for (int y = 1; y < height-1; ++y)
      for (int x = 1; x < width-1; ++x) {
        g = magnitude.getSample(x, y, 0);
        sector = orientation.getSample(x, y, 0);
        if ((sector == 1 &&  (g < magnitude.getSample(x-1, y, 0) || g < magnitude.getSample(x+1, y, 0)))
         || (sector == 2 &&   (g < magnitude.getSample(x-1, y+1, 0)  || g < magnitude.getSample(x+1, y-1, 0)))
         || (sector == 3 &&  (g < magnitude.getSample(x, y-1, 0)  || g < magnitude.getSample(x, y+1, 0)))
         || (sector == 4 &&  (g < magnitude.getSample(x-1, y-1, 0) || g < magnitude.getSample(x+1, y+1, 0))))
          result.setSample(x, y, 0, 0);
        else
          result.setSample(x, y, 0, g);
      }
    magnitude = result;
  }

  /* Generates an edge map by applying a single threshold to gradient magnitude data.
   * param image : destination image  */
  protected void applyHighThreshold(BufferedImage image) {
    WritableRaster raster = image.getRaster();
    for (int y = 1; y < height-1; ++y)
      for (int x = 1; x < width-1; ++x)
        if (magnitude.getSample(x, y, 0) >= highThreshold)
          raster.setSample(x, y, 0, 255);
  }

  /* Generates an edge map by hysteresis thresholding.
   * param image : destination image  */
  protected void performHysteresisThresholding(BufferedImage image) {
    WritableRaster raster = image.getRaster();
    for (int y = 1; y < height-1; ++y)
      for (int x = 1; x < width-1; ++x)
        if (magnitude.getSample(x, y, 0) >= highThreshold)
          trace(x, y, raster);
  }

  /* Recursively tracks edges between strong edge pixels.
   * param x : x coordinate of starting point
   * param y : y coordinate of starting point
   * param raster : edge map  */
  protected boolean trace(int x, int y, WritableRaster raster) {
    if (raster.getSample(x, y, 0) == 0) {
      raster.setSample(x, y, 0, 255);
      for (int k = -1; k <= 1; ++k)
        for (int j = -1; j <= 1; ++j) {
          if (j == 0 && k == 0)  continue;
          if (magnitude.getSample(x+j, y+k, 0) >= lowThreshold)
            if (trace(x+j, y+k, raster))
              return true;
        }
    }
    return false;
  }
}
