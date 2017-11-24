package javabasic.ex14.original;

import java.io.*;
import java.awt.image.BufferedImage;
import com.sun.image.codec.jpeg.*;

/* Reads image data in JPEG format from a stream or a file. This class is simply a wrapper 
 * for the decoder provided in the com.sun.image.codec.jpeg package.
 * see JPEGDecoderException, JPEGEncoder, java.awt.image.BufferedImage,
 * com.sun.image.codec.jpeg.JPEGImageDecoder   */

public class JPEGDecoder implements ImageDecoder {
  /////////////////////////// INSTANCE VARIABLES ///////////////////////////
  /* Decodes JPEG-compressed image data. */
  private JPEGImageDecoder decoder;

  //////////////////////////////// METHODS /////////////////////////////////
  /* Constructs a JPEGDecoder that reads from standard input. */
  public JPEGDecoder() { this(System.in); }
  /* Constructs a JPEGDecoder that reads from the specified InputStream.
   * param in : the InputStream from which data will be read   */
  public JPEGDecoder(InputStream in) {
    decoder = JPEGCodec.createJPEGDecoder(in);
  }

  /* Constructs a JPEGDecoder that reads from a named file.
   * param imgfile : name of the file containing the image data
   * exception FileNotFoundException if the file cannot be accessed. */
  public JPEGDecoder(String imgfile) throws FileNotFoundException {
    this(new FileInputStream(imgfile));
  }

  /* Decodes the input data and creates an image.
   * return a BufferedImage containing the data
   * exception IOException if there was a problem reading the datastream.
   * exception JPEGDecoderException if there were irregularities in  the datastream. */
  public BufferedImage decodeAsBufferedImage() throws IOException, JPEGDecoderException {
    try {
      return decoder.decodeAsBufferedImage();
    }
    catch (ImageFormatException e) {   // catch and convert exception
      throw new JPEGDecoderException(e.getMessage());
    }
  }
}