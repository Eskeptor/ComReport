package javabasic.ex14;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class PGMConverterP2 {
    public static BufferedImage convert(final File _file) {
        BufferedImage originalImage = null, convertedImage = null;
        try {
            InputStream inputStream = new FileInputStream(_file);
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(inputStream);
            originalImage = decoder.decodeAsBufferedImage();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (originalImage != null) {
            final int IMAGE_WIDTH = originalImage.getWidth();
            final int IMAGE_HEIGHT = originalImage.getHeight();

            convertedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_BYTE_GRAY);

            for (int y = 0; y < IMAGE_HEIGHT; y++) {
                for (int x = 0; x < IMAGE_WIDTH; x++) {
                    int pixel = originalImage.getRGB(x, y);
                    convertedImage.setRGB(x, y, pixel);
                }
            }
        }
        return convertedImage;
    }
}
