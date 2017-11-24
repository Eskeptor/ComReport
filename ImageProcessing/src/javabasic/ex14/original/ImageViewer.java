package javabasic.ex14.original;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class ImageViewer extends JFrame {
  /////////////////////////////// CONSTANTS ////////////////////////////////
  private static final String MAGNIFIER_ICON = "magnify.gif";

  /////////////////////////// INSTANCE VARIABLES ///////////////////////////
  private BufferedImage image;          // image to be displayed
  private ImageView view;               // component that displays image
  private Scroller scrollingView;       // scrolls a partial view of image
  private PixelInfoPane infoPane;       // displays pixel coords and value
  private JToggleButton magnifyButton;  // (de)activates magnifying glass
  private Magnifier magnifier;          // magnifies part of the image

  ///////////////////////////// INNER CLASSES //////////////////////////////

  class Scroller extends JScrollPane {
    // Creates a scroll pane wrapped around the displayed image

    public Scroller(ImageView theView) {
      super(theView);
      addMouseMotionListener(  // for tracking of pixel coords/value
       new MouseMotionAdapter() {
         public void mouseMoved(MouseEvent event) {
           infoPane.updateInfo(getPixelCoordinates(event.getPoint()));
         }
       });
      addMouseListener(        // for image magnification
       new MouseAdapter() {
         public void mouseClicked(MouseEvent event) {
           magnifier.updateView(getPixelCoordinates(event.getPoint()));
         }
       });
    }

    // Maps cursor position into image coordinate system

    public Point getPixelCoordinates(Point cursorPosition) {
      Point viewOrigin = getViewport().getViewPosition();
      Point pixelPosition = new Point(viewOrigin.x + cursorPosition.x,
       viewOrigin.y + cursorPosition.y);
      return pixelPosition;
    }
  }

  //////////////////////////////// METHODS /////////////////////////////////
  public ImageViewer(String filename)
   throws IOException, ImageDecoderException {

    super(filename);

    // Load image from a file

    ImageDecoder input = ImageFile.createImageDecoder(filename);
    image = input.decodeAsBufferedImage();

    // Create components to display image and pixel information

    view = new ImageView(image);
    scrollingView = new Scroller(view);
    infoPane = new PixelInfoPane(image);

    // Create magnifying glass dialog and a button to activate it

    magnifier = new Magnifier(this, image);
    magnifyButton = new JToggleButton(
     new ImageIcon(getClass().getResource(MAGNIFIER_ICON)));
    magnifyButton.addActionListener(
     new ActionListener() {
       public void actionPerformed(ActionEvent event) {
         if (magnifier.isVisible())
           magnifier.setVisible(false);
         else
           magnifier.setVisible(true);
       }
     });

    // Group pixel information and magnify button

    JPanel controlPane = new JPanel();
    controlPane.add(infoPane);
    controlPane.add(magnifyButton);

    // Add view and controls to the GUI

    Container pane = getContentPane();
    pane.add(scrollingView, BorderLayout.CENTER);
    pane.add(controlPane, BorderLayout.SOUTH);

    addWindowListener(new WindowMonitor());

  }

  // Unselects magnify button when magnifier is closed

  public void resetMagnifyButton() {
    magnifyButton.getModel().setSelected(false);
  }

  public static void main(String[] argv) {
    if (argv.length > 0) {
      try {
        JFrame frame = new ImageViewer(argv[0]);
        frame.pack();
        frame.setVisible(true);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java ImageViewer <imagefile>");
      System.exit(1);
    }
  }

}