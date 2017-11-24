package javabasic.ex14.original;

import java.awt.image.BufferedImage;
import java.io.IOException;

/* Interface implemented by all image format decoding classes.
 * see java.awt.image.BufferedImage  */

public interface ImageDecoder {
  BufferedImage decodeAsBufferedImage()
   throws IOException, ImageDecoderException;
}