package javabasic.ex14.original; /***************************************************************************

  IdealHighPass.java

  This program performs ideal high pass filtering of an image in the
  frequency domain.  A filter radius between 0.05 and 0.95 must be
  specified, along with a DC bias for the filter output.

  Note: this program is provided largely for illustrative purposes,
  since it creates unsightly ringing artifacts in the filtered image.

  Example of use:

    java IdealHighPass in.png out.png 0.3 127


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
//import com.pearsoneduc.ip.op.*;
//import com.pearsoneduc.ip.util.IntervalTimer;


public class IdealHighPass {
  public static void main(String[] argv) {
    if (argv.length > 3) {
      try {

        ImageDecoder input = ImageFile.createImageDecoder(argv[0]);
        ImageEncoder output = ImageFile.createImageEncoder(argv[1]);
        float radius =
         Math.max(0.05f, Math.min(0.95f,
          Float.valueOf(argv[2]).floatValue()));
        int bias = Integer.parseInt(argv[3]);

        BufferedImage inputImage = input.decodeAsBufferedImage();
        ImageFFT fft = new ImageFFT(inputImage);
        System.out.println("Filter radius = " + radius + "...");
        IntervalTimer timer = new IntervalTimer();
        timer.start();
        fft.transform();
        fft.idealHighPassFilter(radius);
        fft.transform();
        System.out.println("Filtering finished [" + timer.stop() + " sec]");

        BufferedImage outputImage = fft.toImage(null, bias);
        output.encode(outputImage);
        System.exit(0);

      }
      catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
    else {
      System.err.println(
       "usage: java IdealHighPass <infile> <outfile> <radius> <bias>");
      System.exit(1);
    }
  }

}
