package javabasic.ex14.original; /***************************************************************************
  GreyMapPanel.java  :  GreyMapPanel provides a control panel that can be used to select
  and edit various different grey level mapping functions.  An
  instance of GreyMapPanel is used in the GreyMapTool application.
***************************************************************************/
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class GreyMapPanel extends JPanel implements ActionListener, ChangeListener {
  /////////////////////////// INSTANCE VARIABLES ///////////////////////////
  private ImageView imageView;    // component displaying an image
  private Hashtable opSet;        // collection of remapping operations
  private GreyMapOp currentOp;    // current remapping operation
  private GreyMapView opView;     // component displaying mapping function
  private JComboBox opSelector;   // component to select an operation
  private JSlider lowSlider;      // component to adjust mapping function
  private JSlider highSlider;     // ditto
  //////////////////////////////// METHODS /////////////////////////////////
  public GreyMapPanel(ImageView view, Hashtable ops, Vector names) {
    // Store references to an image (can be null)
    // and a set of mapping operations

    imageView = view;
    opSet = ops;

    // Create components to view mapping function and switch
    // between different mapping operators

    currentOp = (GreyMapOp) opSet.get(names.firstElement());
    opView = new GreyMapView(currentOp);
    opSelector = new JComboBox(names);
    opSelector.addActionListener(this);

    // Create sliders to control shape of mapping function

    lowSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    lowSlider.setMinorTickSpacing(10);
    lowSlider.setMajorTickSpacing(50);
    lowSlider.setPaintTicks(true);
    lowSlider.setPaintLabels(true);
    lowSlider.addChangeListener(this);
    highSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
    highSlider.setMinorTickSpacing(10);
    highSlider.setMajorTickSpacing(50);
    highSlider.setPaintTicks(true);
    highSlider.setPaintLabels(true);
    highSlider.addChangeListener(this);

    // Add components to panel

    setLayout(new BorderLayout());
    add(wrapper(opView), BorderLayout.CENTER);
    JPanel controlPane = new JPanel(new GridLayout(3, 1));
    controlPane.add(wrapper(opSelector));
    controlPane.add(lowSlider);
    controlPane.add(highSlider);
    add(controlPane, BorderLayout.SOUTH);

  }

  // Wraps a component in a JPanel and returns a reference to that JPanel
  public JPanel wrapper(JComponent component) {
    JPanel pane = new JPanel();
    pane.add(component);
    return pane;
  }

  // Event handler for selection of a new mapping operation
  public void actionPerformed(ActionEvent event) {
    Object key = ((JComboBox) event.getSource()).getSelectedItem();
    currentOp = (GreyMapOp) opSet.get(key);
    currentOp.computeMapping(0, 255);      // reset mapping parameters
    lowSlider.setValue(0);
    highSlider.setValue(255);
    opView.updateView(currentOp);          // display new operation...
    if (imageView != null) {               // ...and its effect on the
      imageView.setOperation(currentOp);   // current image (if it exists)
      imageView.repaint();
    }
  }

  // Event handler for adjustment of sliders
  public void stateChanged(ChangeEvent event) {
    JSlider slider = (JSlider) event.getSource();    // which slider?
    int low = lowSlider.getValue();
    int high = highSlider.getValue();
    if (slider == lowSlider && low >= high) {        // invalid lower limit
      low = high-1;
    }
    else if (slider == highSlider && high <= low) {  // invalid upper limit
      high = low+1;
    }
    currentOp.computeMapping(low, high);
    opView.updateView();
    if (!slider.getValueIsAdjusting() && imageView != null)
      imageView.repaint();
  }
}
