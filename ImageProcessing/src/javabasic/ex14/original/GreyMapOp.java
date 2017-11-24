package javabasic.ex14.original; /***************************************************************************
  GreyMapOp.java
***************************************************************************/
import java.awt.image.*;
/**
 * Performs arbitrary mapping of grey levels in a BufferedImage,
 * using a look-up table.  The image must be an 8-bit greyscale image.
 *
 * <p>This is an abstract class; concrete subclasses must implement
 * the method {@link #computeMapping(int, int) computeMapping()},
 * which generates data for the lookup table.</p>
 */

public abstract class GreyMapOp extends StandardGreyOp {
  /////////////////////////// INSTANCE VARIABLES ///////////////////////////
  /** Lookup table data. */
  protected byte[] table = new byte[256];
  //////////////////////////////// METHODS /////////////////////////////////
  /**
   * Retrieves a lookup table entry.
   * @param i index into the lookup table
   * @return value stored at the specified index.
   */
  public int getTableEntry(int i) {
    if (table[i] < 0)
      return 256 + (int) table[i];
    else
      return (int) table[i];
  }

  /**
   * Modifies a lookup table entry.
   * @param i index into the lookup table
   * @param value value to be stored at the specified index
   *  (forced to be in the range 0-255 if necessary)
   */
  protected void setTableEntry(int i, int value) {
    if (value < 0)
      table[i] = (byte) 0;
    else if (value > 255)
      table[i] = (byte) 255;
    else
      table[i] = (byte) value;
  }

  /**
   * Computes a mapping of grey level with upper and lower limits.
   * @param low lower limit, mapping onto 0
   * @param high upper limit, mapping onto 255
   */
  public abstract void computeMapping(int low, int high);
  /**
   * Computes a mapping of grey level that maps 0 onto 0 and 255 onto 255.
   */

  public void computeMapping() {
    computeMapping(0, 255);
  }

  /**
   * Performs mapping of grey levels in an image.
   * @param src source image
   * @param dest destination image, or null
   * @return the mapped image.
   */
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
//    checkImage(src);
    if (dest == null)
      dest = createCompatibleDestImage(src, null);
    LookupOp operation = new LookupOp(new ByteLookupTable(0, table), null);
    operation.filter(src, dest);
    return dest;
  }
}
