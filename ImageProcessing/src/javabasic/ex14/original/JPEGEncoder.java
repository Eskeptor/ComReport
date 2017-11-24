package javabasic.ex14.original;

import java.io.*;
import java.awt.image.BufferedImage;
import com.sun.image.codec.jpeg.*;

/* Writes image data to a stream or file encoded in the JPEG format. This class is merely a wrapper 
 * for the JPEGImageEncoder class in the com.sun.image.codec.jpeg package.
 * see JPEGEncoderException, JPEGDecoder, java.awt.image.BufferedImage,
 * com.sun.image.codec.jpeg.JPEGImageEncoder */
public class JPEGEncoder implements ImageEncoder {

  /////////////////////////// INSTANCE VARIABLES ///////////////////////////
  private JPEGImageEncoder encoder;

  //////////////////////////////// METHODS /////////////////////////////////
  /* Constructs a JPEGEncoder that writes to standard output. */

  public JPEGEncoder() { this(System.out); }
  
  /* Constructors a JPEGEncoder that writes to the specified OutputStream.
   * param out stream to which JPEG-compressed data will be written  */
  public JPEGEncoder(OutputStream out) {
    encoder = JPEGCodec.createJPEGEncoder(out);
  }
  
  /* Constructs a JPEGEncoder that writes to a named file.
   * param imgfile name of the image file
   * exception FileNotFoundException if the file cannot be accessed. */
  public JPEGEncoder(String imgfile) throws FileNotFoundException {
    this(new FileOutputStream(imgfile));
  }

  /* Encodes the specified image in JPEG File Interchange Format.
   * param image the image to be encoded
   * exception IOException if data could not be written to the stream.
   * exception JPEGEncoderException if the image cannot be written in  this format. */
  public void encode(BufferedImage image)
   throws IOException, JPEGEncoderException {
    try {
      encoder.encode(image);
    }
    catch (ImageFormatException e) {
      throw new JPEGEncoderException(e.getMessage());
    }
  }
}