package javabasic.ex14.original;

import java.awt.image.BufferedImage;
import java.io.IOException;

/* Interface implemented by all image format encoding classes.
 * see java.awt.image.BufferedImage */

public interface ImageEncoder {
  void encode(BufferedImage image)
   throws IOException, ImageEncoderException;
}