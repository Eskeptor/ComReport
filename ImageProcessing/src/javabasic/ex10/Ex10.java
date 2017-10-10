package javabasic.ex10;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        String filename = "1.jpg";
        InputStream in = Ex10.class.getResourceAsStream(filename);
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
        mImage = decoder.decodeAsBufferedImage();
        in.close();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.clip(getClippingShape(g2));
        g2.drawImage(mImage, 0, 0, null);
//        gTemp.clip(getClippingShape(g2, "정면을 응시하는 사진", 25, 20, 400));
//        g2.drawImage(mImage, 0, 0, null);
    }

    private Shape getClippingShape(Graphics2D g2) {
        if (mClippingShape != null)
            return mClippingShape;
        Font font = new Font("Monospaced", Font.PLAIN, 120);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, "전예찬");
        mClippingShape = gv.getOutline(20, 200);
        return mClippingShape;
    }

    public void center() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
    }

    class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showConfirmDialog(null, "x:" + e.getX() + ", y:" + e.getY(), "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
}

public class Ex10 {
    public static void main(String[] args) throws IOException{
        CustomFrame frame = new CustomFrame("과제");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.center();
        frame.setVisible(true);
    }
}
