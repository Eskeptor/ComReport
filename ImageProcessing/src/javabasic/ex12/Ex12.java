package javabasic.ex12;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

class ImageComponent extends Component {
    class ARGB {
        public int alpha;
        public int red ;
        public int green;
        public int blue;
        ARGB(int alpha, int red, int green, int blue) {
            this.alpha = alpha;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
    private BufferedImage mImage;
    private Color mColor;
    private boolean mFirstExecute;
    private ARGB[][] mOriginalColor;

    ImageComponent(final String _imageSrc, final Color _color) {
        mColor = _color;
        try {
            InputStream inputStream = getClass().getResourceAsStream(_imageSrc);
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(inputStream);
            mImage = decoder.decodeAsBufferedImage();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(mImage.getWidth(), mImage.getHeight());
        mFirstExecute = false;
    }

    public void setmColor(final Color _color) {
        mColor = _color;
        repaint();
    }


    @Override
    public void paint(Graphics _g) {
        if(mImage != null) {
            final int WIDTH = mImage.getWidth();
            final int HEIGHT = mImage.getHeight();

            if(!mFirstExecute) {
                mOriginalColor = new ARGB[HEIGHT][WIDTH];
                for(int y = 0; y < HEIGHT; y++) {
                    for(int x = 0; x < WIDTH; x++) {
                        int pixel = mImage.getRGB(x, y);
                        mOriginalColor[y][x] = new ARGB((pixel >> 24) & 0xff, (pixel >> 16) & 0xff, (pixel >> 8) & 0xff, pixel & 0xff);
                    }
                }
                mFirstExecute = true;
            }

            for(int y = 0; y < HEIGHT; y++) {
                for(int x = 0; x < WIDTH; x++) {
                    int pixel = 0;
                    int a =  mOriginalColor[y][x].alpha;
                    int r, g, b;
                    if(mColor == Color.red) {
                        r = mOriginalColor[y][x].red;
                        g = b = 0;
                    } else if(mColor == Color.green) {
                        g = mOriginalColor[y][x].green;
                        r = b = 0;
                    } else if(mColor == Color.blue){
                        b = mOriginalColor[y][x].blue;
                        r = g = 0;
                    } else {
                        r = mOriginalColor[y][x].red;
                        g = mOriginalColor[y][x].green;
                        b = mOriginalColor[y][x].blue;
                    }
                    pixel = (a << 24) | (r << 16) | (g << 8) | b;
                    mImage.setRGB(x, y, pixel);
                }
            }
            _g.drawImage(mImage, 0, 0, null);
        }
    }
}

class MainFrame extends JFrame {
    MainFrame(final String _title) {
        super(_title);
        setLayout(new BorderLayout());

        JButton redButton = new JButton("빨간색");
        JButton greenButton = new JButton("초록색");
        JButton blueButton = new JButton("파란색");
        JButton resetButton = new JButton("원본");
        ImageComponent imageComponent = new ImageComponent("1.jpg", Color.WHITE);

        redButton.addActionListener((e -> imageComponent.setmColor(Color.red)));
        greenButton.addActionListener((e -> imageComponent.setmColor(Color.green)));
        blueButton.addActionListener((e -> imageComponent.setmColor(Color.blue)));
        resetButton.addActionListener((e -> imageComponent.setmColor(Color.WHITE)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(resetButton);
        buttonPanel.add(redButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(blueButton);


        add(imageComponent, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(getPreferredSize());
        setVisible(true);
    }
}

public class Ex12 {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame("과제 12");
    }
}
