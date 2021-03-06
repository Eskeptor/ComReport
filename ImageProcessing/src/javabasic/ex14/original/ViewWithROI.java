package javabasic.ex14.original; /***************************************************************************
  ViewWithROI.java
***************************************************************************/
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A simple image display component that overlays a region of interest,
 * or ROI.  The ROI is represented as a coloured rectangular outline.
 * The colour can be modified if desired.  The component is repainted
 * whenever the ROI is changed.
 * @see BufferedImage
 */

public class ViewWithROI extends ImageView {
  /////////////////////////// INSTANCE VARIABLES ///////////////////////////

  /** Region of interest (ROI). */
  private Rectangle region = new Rectangle();

  /** Colour of ROI outline. */
  private Color colour = Color.red;

  //////////////////////////////// METHODS /////////////////////////////////
  /**
   * Constructs a component to display the specified image.
   * @param image the BufferedImage to be displayed
   */

  public ViewWithROI(BufferedImage image) {
    super(image);
  }

  /**
   * Changes the region of interest that is drawn over the image.
   * @param x the x coordinate of the new region
   * @param y the y coordinate of the new region
   * @param w the width of the new region
   * @param h the height of the new region
   */

  public void setROI(int x, int y, int w, int h) {
    region.x = x;
    region.y = y;
    region.width = w;
    region.height = h;
    repaint();
  }

  /**
   * Changes the region of interest that is drawn over the image.
   * @param region ROI parameters, packed into a Rectangle
   */

  public void setROI(Rectangle region) {
    setROI(region.x, region.y, region.width, region.height);
  }


  /**
   * @return current region of interest for the displayed image.
   */

  public Rectangle getROI() {
    return new Rectangle(region.x, region.y, region.width, region.height);
  }

  /**
   * Changes the colour of the ROI outline drawn on the image.
   * @param c Color object defining the new colour of the outline
   */

  public void setROIColour(Color c) {
    colour = c;
  }

  /**
   * @return colour being used for ROI outline.
   */

  public Color getROIColour() {
    return colour;
  }

  /**
   * Paints component.
   * @param g graphics context of the component
   */

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    if (region.width > 0 && region.height > 0) {
      g2.setPaint(colour);
      g2.draw(region);
    }
  }

}