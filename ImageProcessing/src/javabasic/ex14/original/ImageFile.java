package javabasic.ex14.original;

import java.io.IOException;

/* Defines static factory methods that create image encoders and decoders for files.
 * Each encoder implements the ImageEncoder interface and therefore provides a method named
 * ImageEncoder(BufferedImage) which can be used to write the image to a file.  
 * Similarly, each decoder implements the ImageDecoder interface and therefore provides a method
 * ImageDecoder decodeAsBufferedImage() decodeAsBufferedImage
 * which can be used to read data from a file.
 *
 * Appropriate encoders / decoders are created by examining the suffix of the filename supplied to 
 * the factory methods.  For example, if the filename ends with .jpg, a JPEGEncoder will be
 * created to write the file, or a JPEGDecoder to read it.
 * see ImageEncoder, ImageDecoder, ImageEncoderException, ImageDecoderException  */

public class ImageFile {
  /* Creates an ImageEncoder suitable for use with the specified filename.
   * param filename : name of the image file
   * return new instance of a suitable ImageEncoder.
   * exception IOException if there was some problem with output
   * exception ImageEncoderException if the image type is unsupported by
   * the format or could not be encoded in that format.  */

  public static ImageEncoder createImageEncoder(String filename) throws IOException, ImageEncoderException {
    if (filename.endsWith(".pbm") || filename.endsWith(".pgm") || filename.endsWith(".ppm"))
      return new PPMEncoder(filename);
    else if (filename.endsWith(".sif"))
      return new SIFEncoder(filename);
    else if (filename.endsWith(".png"))
      return new PNGEncoder(filename);
    else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
      return new JPEGEncoder(filename);
    else
      throw new ImageEncoderException("cannot determine file format");
  }

  /* Creates an ImageDecoder suitable for use with the specified filename.
   * param filename : name of the image file
   * return new instance of a suitable ImageDecoder.
   * exception IOException if there was some problem with reading data
   * exception ImageDecoderException if the file does not appear to follow the expected format. */

  public static ImageDecoder createImageDecoder(String filename) throws IOException, ImageDecoderException {
    if (filename.endsWith(".pbm") || filename.endsWith(".pgm") || filename.endsWith(".ppm"))
      return new PPMDecoder(filename);
    else if (filename.endsWith(".sif"))
      return new SIFDecoder(filename);
    else if (filename.endsWith(".png"))
      return new PNGDecoder(filename);
    else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
      return new JPEGDecoder(filename);
    else
      throw new ImageDecoderException("cannot determine file format");
  }
}