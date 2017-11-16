package javabasic.ex13;

import javabasic.ex13.original.PPMDecoder;
import javabasic.ex13.original.SIFDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomDialog extends JDialog {

    class ImagePanel extends JPanel {
        private BufferedImage mImage;
        ImagePanel(final String _imageSrc, final ImageType _type) throws Exception {
            switch (_type) {
                case PPM: {
                    mImage = new PPMDecoder(_imageSrc).decodeAsBufferedImage();
                    break;
                }
                case SIF: {
                    mImage = new SIFDecoder(_imageSrc).decodeAsBufferedImage();
                    break;
                }
            }
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(mImage, 0, 0, null);
        }

        public int getImageWidth() {
            return mImage.getWidth();
        }

        public int getImageHeight() {
            return mImage.getHeight();
        }
    }


    CustomDialog(final String _imageSrc, final ImageType _type) throws Exception {
        ImagePanel imagePanel = new ImagePanel(_imageSrc, _type);

        add(imagePanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(imagePanel.getImageWidth(), imagePanel.getImageHeight());
        setVisible(true);
    }

}
