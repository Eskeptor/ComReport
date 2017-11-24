package javabasic.ex14.original;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class MeanROI extends JFrame  implements MouseListener, MouseMotionListener {
  private BufferedImage image;   // input image
  private ViewWithROI view;      // image/ROI display component
  private MeanInfo info;         // panel to show mean grey level

  public MeanROI(String imageFile) throws IOException, ImageDecoderException {
    super("MeanROI: " + imageFile);
    readImage(imageFile);

    // Create component to view image with ROI
    view = new ViewWithROI(image);
    view.addMouseListener(this);
    view.addMouseMotionListener(this);

    // Compute mean grey level and create an information panel
    double mean = meanValue(image);
    info = new MeanInfo(mean);

    // Add components to frame
    Container pane = getContentPane();
    pane.add(view, BorderLayout.CENTER);
    pane.add(info, BorderLayout.SOUTH);
    addWindowListener(new WindowMonitor());
  }

  // Reads a greyscale image from a named file
  public void readImage(String filename)
   throws IOException, ImageDecoderException {
    ImageDecoder input = ImageFile.createImageDecoder(filename);
    image = input.decodeAsBufferedImage();
    if (image.getType() != BufferedImage.TYPE_BYTE_GRAY)
      throw new ImageDecoderException("invalid image type - must be grey");
  }

  // Computes mean grey level for an image or a region of that image
  public double meanValue(BufferedImage img) {
    Raster raster = img.getRaster();
    double sum = 0.0;
    for (int y = 0; y < img.getHeight(); ++y)
      for (int x = 0; x < img.getWidth(); ++x)
        sum += raster.getSample(x, y, 0);
    return sum / (img.getWidth()*img.getHeight());
  }

  public double meanValue(BufferedImage img, Rectangle region) {
    return meanValue(img.getSubimage(region.x, region.y,  region.width, region.height));
  }

  // Event handling
  public void mousePressed(MouseEvent event) {
    Point pos = event.getPoint();
    view.setROI(pos.x, pos.y, 0, 0);
  }

  public void mouseDragged(MouseEvent event) {
    Point pos = event.getPoint();
    Rectangle region = view.getROI();
    if (pos.x > region.x && pos.y > region.y) {
      region.width = pos.x - region.x + 1;
      region.height = pos.y - region.y + 1;
      view.setROI(region);
    }
  }

  public void mouseReleased(MouseEvent event) {
    Rectangle region = view.getROI();
    if (region.width > 0 && region.height > 0) {
      double mean = meanValue(image, region);
      info.display(mean);
    }
  }

  // unused, but required by MouseListener
  public void mouseMoved(MouseEvent event) {}

  // unused, but required by MouseMotionListener
  public void mouseClicked(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}

  public static void main(String[] argv) {
    if (argv.length > 0) {
      try {
        JFrame frame = new MeanROI(argv[0]);
        frame.pack();
        frame.setVisible(true);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java MeanROI <imagefile>");
      System.exit(1);
    }
  }
}