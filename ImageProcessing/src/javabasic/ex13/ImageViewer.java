package javabasic.ex13;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import javabasic.ex13.original.PPMEncoder;
import javabasic.ex13.original.PixelInfoPane;
import javabasic.ex13.original.SIFEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ImageViewer extends JFrame {
    private static final String MAGNIFIER_ICON = "magnify.gif";
    private final int ADD_FRAME_SIZE = 56;
    private final int INFO_FRAME_WIDTH_SIZE = 460;

    private BufferedImage mImage;
    private PixelInfoPane mInfoPane;
    private CustomMagnifier mMagnifier;
    private JToggleButton mMagBtn;
    private JButton convertPPMBtn;
    private JButton convertSIFBtn;
    private boolean isConvertPPM;
    private boolean isConvertSIF;
    class ImagePane extends JPanel {
        public ImagePane() {
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mInfoPane.updateInfo(getPixelCoordinates(e.getPoint()));
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    mMagnifier.updateView(getPixelCoordinates(event.getPoint()));
                }
            });
        }

        public Point getPixelCoordinates(Point cursorPosition) {
            return new Point(cursorPosition.x, cursorPosition.y);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(mImage, 0, 0, null);
        }
    }

    ImageViewer(final String _imageSrc) {
        super(_imageSrc);
        try {
            InputStream inputStream = getClass().getResourceAsStream(_imageSrc);
            getClass().getResource("").getPath();
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(inputStream);
            mImage = decoder.decodeAsBufferedImage();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        mInfoPane = new PixelInfoPane(mImage);
        mMagnifier = new CustomMagnifier(this, mImage);
        mMagBtn = new JToggleButton(new ImageIcon(getClass().getResource(MAGNIFIER_ICON)));
        convertPPMBtn = new JButton("PPM");
        convertSIFBtn = new JButton("SIF");

        mMagBtn.addActionListener((e) -> {
            if (mMagnifier.isVisible())
                mMagnifier.setVisible(false);
            else
                mMagnifier.setVisible(true);
        });

        convertPPMBtn.addActionListener((e) -> {
            if (!isConvertPPM) {
                try {
                    PPMEncoder encoder = new PPMEncoder(getClass().getResource(_imageSrc).getPath() + ".ppm");
                    encoder.encode(mImage);
                    JOptionPane.showConfirmDialog(null, "PPM 변환 완료", "성공", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    isConvertPPM = true;
                    convertPPMBtn.setText("PPM View");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showConfirmDialog(null, "PPM 변환 실패", "경고", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                try {
                    new CustomDialog(getClass().getResource(_imageSrc).getPath() + ".ppm", ImageType.PPM);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        convertSIFBtn.addActionListener((e) -> {
            if (!isConvertSIF) {
                try {
                    SIFEncoder encoder = new SIFEncoder(getClass().getResource(_imageSrc).getPath() + ".sif");
                    encoder.encode(mImage);
                    JOptionPane.showConfirmDialog(null, "SIF 변환 완료", "성공", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    isConvertSIF = true;
                    convertSIFBtn.setText("SIF View");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showConfirmDialog(null, "SIF 변환 실패", "경고", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                try {
                    new CustomDialog(getClass().getResource(_imageSrc).getPath() + ".sif", ImageType.SIF);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        ImagePane imagePane = new ImagePane();

        JPanel controlPane = new JPanel();
        controlPane.add(mInfoPane);
        controlPane.add(mMagBtn);
        controlPane.add(convertPPMBtn);
        controlPane.add(convertSIFBtn);

        add(imagePane, BorderLayout.CENTER);
        add(controlPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if (mImage.getWidth() > INFO_FRAME_WIDTH_SIZE)
            setSize(mImage.getWidth(), mImage.getHeight() + ADD_FRAME_SIZE);
        else
            setSize(INFO_FRAME_WIDTH_SIZE, mImage.getHeight() + ADD_FRAME_SIZE);
        setVisible(true);
    }

    public void resetMagnifyButton() {
        mMagBtn.getModel().setSelected(false);
    }

    public static void main(String[] args) {
        final String str = "1.jpg";
        ImageViewer viewer = new ImageViewer(str);
    }
}


