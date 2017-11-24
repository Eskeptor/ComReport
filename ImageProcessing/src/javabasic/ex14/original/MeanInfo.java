package javabasic.ex14.original;

import java.text.DecimalFormat;
import java.awt.*;
import javax.swing.*;

public class MeanInfo extends JPanel {

  private JLabel imageLabel = new JLabel();
  private JLabel regionLabel = new JLabel();
  private DecimalFormat realValue = new DecimalFormat("000.000");

  public MeanInfo(double imageMean) {

    JPanel imagePane = new JPanel();
    imagePane.add(new JLabel("image: "));
    Font fixedFont = new Font("Monospaced", Font.BOLD, 12);
    imageLabel.setFont(fixedFont);
    imageLabel.setForeground(Color.black);
    imageLabel.setText(realValue.format(imageMean));
    imagePane.add(imageLabel);
    imagePane.setBorder(BorderFactory.createEtchedBorder());
    add(imagePane);

    JPanel regionPane = new JPanel();
    regionPane.add(new JLabel("ROI: "));
    regionLabel.setFont(fixedFont);
    regionLabel.setForeground(Color.black);
    regionPane.add(regionLabel);
    regionPane.setBorder(BorderFactory.createEtchedBorder());
    add(regionPane);

    clearDisplay();
  }

  public void display(double mean) {
    regionLabel.setText(realValue.format(mean));
  }

  public void clearDisplay() {
    regionLabel.setText("---.---");
  }
}
