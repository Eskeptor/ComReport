package javabasic.ex10;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

class CustomFrame extends JFrame {
    private Shape mClippingShape;
    private BufferedImage mImage;

    CustomFrame(final String _title) throws IOException{
        super(_title);
        InputStream in = Ex10.class.getResourceAsStream("1.jpg");
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
        mImage = decoder.decodeAsBufferedImage();
        in.close();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.clip(getClippingShape(g2));
        g2.drawImage(mImage, 0, 0, null);
    }

    private Shape getClippingShape(Graphics2D g2) {
        if (mClippingShape != null)
            return mClippingShape;
        Font font = new Font("Monospaced", Font.BOLD, 120);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, "전예찬");
        mClippingShape = gv.getOutline(20, 250);
        return mClippingShape;
    }
}

public class Ex10 {
    public static void main(String[] args) throws IOException{
        CustomFrame frame = new CustomFrame("과제");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
