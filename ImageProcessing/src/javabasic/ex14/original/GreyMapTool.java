package javabasic.ex14.original; /***************************************************************************
  GreyMapTool.java  : This Java program displays an image and applies various different
  grey level mapping functions to it.  The current mapping function is
  plotted beside the image.  Controls are provided to switch to a
  different mapping or modify the shape of the current mapping.
  See GreyMapPanel.java and GreyMapView.java for further details.
***************************************************************************/
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class GreyMapTool extends JFrame {
  public GreyMapTool(String filename) throws IOException, ImageDecoderException, HistogramException {

    // Load image from file and create display component

    super("GreyMapTool: " + filename);
    ImageDecoder input = ImageFile.createImageDecoder(filename);
    BufferedImage image = input.decodeAsBufferedImage();
    LinearOp op = new LinearOp();
    ImageView imageView = new ImageView(image, op);

    // Create and store a set of grey level mapping operations

    Hashtable ops = new Hashtable();
    ops.put("linear", op);
    ops.put("square-root", new SquareRootOp());
    ops.put("logarithmic", new LogOp());
    ops.put("exponential", new ExpOp());
    ops.put("inverted", new InvertOp());
    ops.put("thresholded", new ThresholdOp(128));
    ops.put("equalised", new EqualiseOp(new Histogram(image)));

    // Create labels for the operations

    Vector names = new Vector();
    names.addElement("linear");
    names.addElement("square-root");
    names.addElement("logarithmic");
    names.addElement("exponential");
    names.addElement("inverted");
    names.addElement("thresholded");
    names.addElement("equalised");

    // Add a control panel and a scrolling image display to the frame

    JPanel pane = new JPanel();
    pane.setLayout(new FlowLayout());
    pane.add(new GreyMapPanel(imageView, ops, names));
    pane.add(new JScrollPane(imageView));
    setContentPane(pane);
    addWindowListener(new WindowMonitor());

  }

  public static void main(String[] argv) {
    /*if (argv.length > 0) {
      try {
        JFrame frame = new GreyMapTool(argv[0]);
        frame.pack();
        frame.setVisible(true);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java GreyMapTool <imagefile>");
      System.exit(1);
    }*/
    try {
      JFrame frame = new GreyMapTool(GreyMapTool.class.getResource("1_1.jpg").getPath());
      frame.pack();
      frame.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
