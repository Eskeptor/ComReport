package javabasic.ex14.original; /***************************************************************************

  MeanFilter.java

  This program blurs an image using a rectangular mean filter with
  dimensions specified by the user.

  Example of use:

    java MeanFilter sharp.pgm blurred.pgm 3 3


  Written by Nick Efford.

  Copyright (c) 2000, Pearson Education Ltd.  All rights reserved.

  THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
  ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE
  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
  BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

***************************************************************************/


import java.awt.image.*;
//import com.pearsoneduc.ip.io.*;
//import com.pearsoneduc.ip.op.MeanKernel;
//import com.pearsoneduc.ip.util.IntervalTimer;


public class MeanFilter {
  public static void main(String[] argv) {
    if (argv.length > 3) {
      try {
        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        int w = Integer.parseInt(argv[2]);
        int h = Integer.parseInt(argv[3]);
        BufferedImage inputImage = input.decodeAsBufferedImage();
        Kernel kernel = new MeanKernel(w, h);
        ConvolveOp blurOp = new ConvolveOp(kernel);
        IntervalTimer timer = new IntervalTimer();
        timer.start();
        BufferedImage outputImage = blurOp.filter(inputImage, null);
        System.out.println("Mean filtering finished [" +
         timer.stop() + " sec]");
        output.encode(outputImage);
        System.exit(0);
      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println("usage: java MeanFilter <infile> <outfile> <w> <h>");
      System.exit(1);
    }
  }
}
