package javabasic.ex14.original;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class RGBHistogram extends JFrame {
    enum BAND {
        GRAY, RGB
    }
    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 500;
    private final int HISTO_WIDTH = 296;
    private final int HISTO_HEIGHT = 296;
    private final int MAX = 256;

    private int mYSize = HISTO_HEIGHT - 40;
    private BAND mBand;
    private Histogram mHistogram;
    private int mSumR = 0, mSumB = 0, mSumG = 0;
    private int mRCount = 0, mBCount = 0, mGCount = 0;

    public RGBHistogram(final Histogram _histogram, final BAND _band) {
        super("RGB Histogram");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mHistogram = _histogram;
        mBand = _band;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D)g;

        if (mBand == BAND.RGB) {
            for (int i = 0; i < MAX; i++) {
                graphics2D.setColor(Color.RED);
                double scale = ((double)mYSize) / mHistogram.getMaxFrequency(0);
                int y = (int)Math.round(scale * mHistogram.getFrequency(0, i));
                if (y > 0) {
                    graphics2D.drawLine(20 + i, WINDOW_HEIGHT - 20, 20 + i, WINDOW_HEIGHT - y - 20);
                    mSumR += y;
                    mRCount++;
                }

                graphics2D.setColor(Color.GREEN);
                scale = ((double)mYSize) / mHistogram.getMaxFrequency(1);
                y = (int)Math.round(scale * mHistogram.getFrequency(1, i));
                if (y > 0) {
                    graphics2D.drawLine(20 + i, WINDOW_HEIGHT - 20, 20 + i, WINDOW_HEIGHT - y - 20);
                    mSumG += y;
                    mBCount++;
                }

                graphics2D.setColor(Color.BLUE);
                scale = ((double)mYSize) / mHistogram.getMaxFrequency(2);
                y = (int)Math.round(scale * mHistogram.getFrequency(2, i));
                if (y > 0) {
                    graphics2D.drawLine(20 + i, WINDOW_HEIGHT - 20, 20 + i, WINDOW_HEIGHT - y - 20);
                    mSumB += y;
                    mGCount++;
                }
            }

            graphics2D.setColor(Color.BLACK);
            graphics2D.drawLine(10, WINDOW_HEIGHT - 10, 10, WINDOW_HEIGHT - MAX);
            graphics2D.drawLine(10, WINDOW_HEIGHT - 10, MAX, WINDOW_HEIGHT - 10);
            graphics2D.drawString("0-->255", 260, WINDOW_HEIGHT - 10);
            graphics2D.setFont(new Font("Arial", Font.BOLD, 12));
            graphics2D.drawString("RGB Histogram", 300, 100);
            graphics2D.drawString("Red Mean: ", 300, 120);
            graphics2D.drawString(Integer.toString(mSumR / mRCount), 400, 120);
            graphics2D.drawString("Green Mean: ", 300, 140);
            graphics2D.drawString(Integer.toString(mSumG / mGCount), 400, 140);
            graphics2D.drawString("Blue Mean: ", 300, 160);
            graphics2D.drawString(Integer.toString(mSumB / mBCount), 400, 160);
        } else {
            for (int i = 0; i < MAX; i++) {
                graphics2D.setColor(Color.BLACK);
                double scale = ((double)mYSize) / mHistogram.getMaxFrequency(0);
                int y = (int)Math.round(scale * mHistogram.getFrequency(0, i));
                if (y > 0) {
                    graphics2D.drawLine(20 + i, WINDOW_HEIGHT - 20, 20 + i, WINDOW_HEIGHT - y - 20);
                    mSumR += y;
                    mRCount++;
                }
            }

            graphics2D.drawLine(10, WINDOW_HEIGHT - 10, 10, WINDOW_HEIGHT - MAX);
            graphics2D.drawLine(10, WINDOW_HEIGHT - 10, MAX, WINDOW_HEIGHT - 10);
            graphics2D.drawString("0-->255", 260, WINDOW_HEIGHT - 10);
            graphics2D.setFont(new Font("Arial", Font.BOLD, 12));
            graphics2D.drawString("Gray Histogram", 300, 100);
            graphics2D.drawString("Black Mean: ", 300, 120);
            graphics2D.drawString(Integer.toString(mSumR / mRCount), 400, 120);
        }


    }

    public static void main(String[] args) throws Exception {
//        InputStream inputStream = RGBHistogram.class.getResourceAsStream("2.jpg");
        InputStream inputStream = RGBHistogram.class.getResourceAsStream("1_1.jpg");
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(inputStream);
        final BufferedImage image = decoder.decodeAsBufferedImage();
        inputStream.close();

        Histogram histogram = new Histogram(image);
        RGBHistogram rgbHistogram = new RGBHistogram(histogram, BAND.GRAY);
//        RGBHistogram rgbHistogram = new RGBHistogram(histogram, BAND.RGB);
    }
}
