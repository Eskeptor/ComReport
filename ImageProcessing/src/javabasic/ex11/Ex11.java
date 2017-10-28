package javabasic.ex11;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;


class CustomShow extends Component {
    private String mMessage;
    private Font mFont;
    private int mSplit;
    private BufferedImage mImage;
    private TextLayout mLayout;

    public CustomShow(final String _fileName, final String _message, final int _split) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(_fileName);
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(inputStream);
            mImage = decoder.decodeAsBufferedImage();
            inputStream.close();

            mFont = new Font("Serif", Font.PLAIN, 58);
            mMessage = _message;
            mSplit = _split;

            setSize(mImage.getWidth(), mImage.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(graphics2D);
        drawImageMosaic(graphics2D);
        drawText(graphics2D);
    }

    private void drawBackground(Graphics2D _graphics2D) {
        final int SIDE = 45;
        final int WIDTH = getSize().width;
        final int HEIGHT = getSize().height;
        final Color[] COLORS = {Color.yellow, Color.cyan, Color.orange, Color.pink, Color.magenta, Color.lightGray};

        for(int y = 0; y < HEIGHT; y += SIDE) {
            for (int x = 0; x < WIDTH; x += SIDE) {
                int polyX[] = {(SIDE)/2 + x, x, SIDE + x};
                int polyY[] = {y, SIDE + y, SIDE + y};
                int INDEX = (x + y) / SIDE % COLORS.length;
                _graphics2D.drawPolygon(polyX, polyY, 3);
                _graphics2D.setPaint(COLORS[INDEX]);
                _graphics2D.fillPolygon(polyX, polyY, 3);
            }
        }
    }

    private void drawImageMosaic(Graphics2D _graphics2D) {
        final int SIDE = 36;
        final int WIDTH = mImage.getWidth();
        final int HEIGHT = mImage.getHeight();
        for(int y = 0; y < HEIGHT; y += SIDE) {
            for(int x = 0; x < WIDTH; x += SIDE) {
                final float XBIAS = (float)x / (float)WIDTH;
                final float YBIAS = (float)y / (float)HEIGHT;
                final float ALPHA = 1.0f - Math.abs(XBIAS - YBIAS);
                _graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
                final int W = Math.min(SIDE, WIDTH - x);
                final int H = Math.min(SIDE, HEIGHT - y);
                BufferedImage tile = mImage.getSubimage(x, y, W, H);
                _graphics2D.drawImage(tile, x, y, null);
            }
        }
        _graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

    private void drawText(Graphics2D _graphics2D) {
        FontRenderContext fontRenderContext = _graphics2D.getFontRenderContext();
        mLayout = new TextLayout(mMessage, mFont, fontRenderContext);

        final int WIDTH = getSize().width;
        final int HEIGHT = getSize().height;
        Rectangle2D bounds = mLayout.getBounds();
        final double X = (WIDTH - bounds.getWidth()) / 2;
        final double Y = HEIGHT - bounds.getHeight();
        drawString(_graphics2D, X, Y, 0);
        drawString(_graphics2D, WIDTH - bounds.getHeight(), Y, -Math.PI / 2);
    }

    private void drawString(Graphics2D _graphics2D, final double _x, final double _y, final double _theta) {
        _graphics2D.translate(_x, _y);
        _graphics2D.rotate(_theta);
        final String FIRST = mMessage.substring(0, mSplit);
        final float WIDTH = drawBoxedString(_graphics2D, FIRST, Color.white, Color.red, 0);
        final String SECOND = mMessage.substring(mSplit);
        drawBoxedString(_graphics2D, SECOND, Color.blue, Color.white, WIDTH);
        _graphics2D.rotate(-_theta);
        _graphics2D.translate(-_x, -_y);
    }

    private float drawBoxedString(Graphics2D _graphics2D, final String _s, final Color _c1, final Color _c2, final double _x) {
        FontRenderContext fontRenderContext = _graphics2D.getFontRenderContext();
        TextLayout subLayout = new TextLayout(_s, mFont, fontRenderContext);
        final float ADVANCE = subLayout.getAdvance();
        GradientPaint gradientPaint = new GradientPaint((float)_x, 0, _c1, (float)(_x + ADVANCE), 0, _c2);
        _graphics2D.setPaint(gradientPaint);
        Rectangle2D bounds = mLayout.getBounds();
        Rectangle2D back = new Rectangle2D.Double(_x, 0, ADVANCE, bounds.getHeight());
        _graphics2D.fill(back);
        _graphics2D.setPaint(Color.white);
        _graphics2D.setFont(mFont);
        _graphics2D.drawString(_s, (float)_x, (float)-bounds.getY());

        return ADVANCE;
    }
}

public class Ex11 {
    public static void main(String[] args) {
        String fileName = "1.jpg";
        String message = "미스에이 수지";
        int split = 4;
        if(args.length > 0)
            fileName = args[0];
        if(args.length > 1)
            message = args[1];
        if(args.length > 2)
            split = Integer.parseInt(args[2]);

        JFrame frame = new JFrame("과제 11");
        frame.setLayout(new BorderLayout());
        CustomShow show = new CustomShow(fileName, message, split);
        frame.add(show, BorderLayout.CENTER);
        frame.setSize(frame.getPreferredSize());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
