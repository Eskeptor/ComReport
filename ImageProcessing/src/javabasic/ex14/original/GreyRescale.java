package javabasic.ex14.original; /***************************************************************************
 GreyRescale.java
 This program remaps grey levels linearly using the supplied scale factor and offset.
 Example of use:
 java GreyRescale dark.png bright.png 1.5 20
 ***************************************************************************/
import java.awt.image.*;
import java.io.File;

public class GreyRescale {
    public static void main(String[] argv) {
        String inputPath = GreyRescale.class.getResource("1.jpg").getPath();
        String outputPath = GreyRescale.class.getResource("").getPath() + File.separator + "1_1.jpg";
        float scale = 1.0f;
        float offset = 0.0f;
        try {
            ImageDecoder input = ImageFile.createImageDecoder(inputPath);
            ImageEncoder output = ImageFile.createImageEncoder(outputPath);
            BufferedImage image = input.decodeAsBufferedImage();
            RescaleOp rescale = new RescaleOp(scale, offset, null);
            output.encode(rescale.filter(image, null));
            System.out.println("변환 완료");
            System.exit(0);
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        /*if (argv.length > 3) {
            try {
                ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
                ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
                float scale = Float.valueOf(argv[2]).floatValue();
                float offset = Float.valueOf(argv[3]).floatValue();
                BufferedImage image = input.decodeAsBufferedImage();
                RescaleOp rescale = new RescaleOp(scale, offset, null);
                output.encode(rescale.filter(image, null));
                System.exit(0);
            }
            catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }
        }
        else {
            System.err.println(
                    "usage: java GreyRescale <infile> <outfile> <scale> <offset>");
            System.exit(1);
        }*/
    }
}
