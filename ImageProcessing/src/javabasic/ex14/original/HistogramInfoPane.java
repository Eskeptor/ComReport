package javabasic.ex14.original; /***************************************************************************
  HistogramInfoPane.java   :  This class extends JPanel to provide specialised facilities for
  displaying value and frequency information from a histogram.
  An instance of this class is used in the HistogramTool application.
  This application tracks mouse movement when the cursor is over a
  histogram and invokes the HistogramInfoPane's updateInfo method to
  display the value at the point under the cursor, together with
  the corresponding frequency / cumulative frequency.

  See the HistogramTool class for further information.
***************************************************************************/
import java.awt.*;
import javax.swing.*;

public class HistogramInfoPane extends JPanel {
  /////////////////////////// INSTANCE VARIABLES ///////////////////////////
  private Histogram histogram;
  private JLabel greyLevel = new JLabel();
  private JLabel freq = new JLabel();
  private JLabel cumFreq = new JLabel();
  //////////////////////////////// METHODS /////////////////////////////////
  public HistogramInfoPane(Histogram theHistogram) {

    histogram = theHistogram;

    // Create a panel containing pixel value

    JPanel greyLevelPane = new JPanel();
    greyLevelPane.add(new JLabel("value"));
    Font fixedFont = new Font("Monospaced", Font.BOLD, 12);
    greyLevel.setFont(fixedFont);
    greyLevel.setForeground(Color.black);
    greyLevelPane.add(greyLevel);
    greyLevelPane.setBorder(BorderFactory.createEtchedBorder());
    add(greyLevelPane);

    // Create a panel containing frequency information

    JPanel frequencyPane = new JPanel();
    frequencyPane.add(new JLabel("freq"));
    freq.setFont(fixedFont);
    freq.setForeground(Color.black);
    frequencyPane.add(freq);
    frequencyPane.add(new JLabel("cum freq"));
    cumFreq.setFont(fixedFont);
    cumFreq.setForeground(Color.black);
    frequencyPane.add(cumFreq);
    frequencyPane.setBorder(BorderFactory.createEtchedBorder());
    add(frequencyPane);

    updateInfo(0, 0);

  }

  // Updates displayed information, given band and grey level value
  public void updateInfo(int band, int value) {
    greyLevel.setText(StringTools.rightJustify(value, 3));
    freq.setText(StringTools.rightJustify(
     histogram.getFrequency(band, value), 6));
    cumFreq.setText(StringTools.rightJustify(
     histogram.getCumulativeFrequency(band, value), 6));
  }
}
