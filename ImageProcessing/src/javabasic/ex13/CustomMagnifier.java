package javabasic.ex13;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class CustomMagnifier extends JDialog implements ItemListener {
    private static final int VIEW_WIDTH = 128;
    private static final int VIEW_HEIGHT = 128;
    private static final int MAG_FACTOR = 2;
    private static final String[] MAG_OPTIONS = {
            " x2", " x3", " x4", " x5", " x6", " x7", " x8"
    };

    private ImageViewer mViewer;          // magnifier's owner
    private BufferedImage mSourceImage;   // image being magnified
    private ImageIcon mViewedImage;       // magnified portion of that image
    private JLabel mView;                 // component used for image display
    private Point mCentrePixel;           // position of magnified view
    private int mMagFactor = MAG_FACTOR;  // magnification factor

    public CustomMagnifier(final ImageViewer _theViewer, final BufferedImage _image) {

        super(_theViewer, "Magnifier", false);   // this is a non-modal dialog
        mViewer = _theViewer;

        // Trap case where user dismisses the dialog - so that
        // we can unselect the JToggleButton that made the dialog
        // visible in the first place...

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent event) {
                        mViewer.resetMagnifyButton();
                    }
                }
        );

        // Create default magnified view of a region surrounding
        // the source image's central pixel...

        mSourceImage = _image;
        mViewedImage = new ImageIcon();
        mCentrePixel = new Point(_image.getWidth()/2, _image.getHeight()/2);
        updateView();

        // Create a component to hold the magnified view

        JLabel view = new JLabel(mViewedImage);

        // Create:
        //   A control to vary magnification
        //   A label for the control
        //   A container for the control and its label

        JComboBox magSelector = new JComboBox(MAG_OPTIONS);
        magSelector.addItemListener(this);
        JPanel controlPane = new JPanel();
        controlPane.add(new JLabel("Magnification"));
        controlPane.add(magSelector);

        // Add magnified view and control to the dialog

        JPanel magnifyPane = new JPanel(new BorderLayout());
        magnifyPane.add(view, BorderLayout.CENTER);
        magnifyPane.add(controlPane, BorderLayout.SOUTH);

        setContentPane(magnifyPane);
        pack();
    }

    public void updateView() {
        try {
            int width = VIEW_WIDTH / mMagFactor;
            int height = VIEW_HEIGHT / mMagFactor;
            int x = mCentrePixel.x - width / 2;
            int y = mCentrePixel.y - height / 2;
            mViewedImage.setImage(
                    mSourceImage.getSubimage(x, y, width, height).getScaledInstance(
                            VIEW_WIDTH, VIEW_HEIGHT, Image.SCALE_FAST));
        }
        catch (RasterFormatException e) {
            Toolkit.getDefaultToolkit().beep();
        }
        repaint();
    }

    public void updateView(Point _pixel) {
        mCentrePixel.setLocation(_pixel);
        updateView();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String choice = (String) e.getItem();
        mMagFactor = Integer.parseInt(choice.substring(2));  // skip the " x"
        updateView();
    }
}
