package ex5;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.lang.Thread;

public class RedBlackTreeApplet extends Applet implements Observer {
    RedBlackTreeCanvas  tp;
    int       step_state;
    Button    startButton;
    Button    stepButton;
    Button    resetButton;

    public RedBlackTreeApplet() {
        tp = null; step_state = 0;
    }

    public void init() {
        Label x;
        // applet is border layout
        setLayout(new BorderLayout());
        // build the panel that the graph is drawn on
        tp     = new RedBlackTreeCanvas();
        // add the panel to the applet
        add(tp,"Center");
        startButton = new Button("Start"); startButton.addActionListener(new StartButtonHandler());
        stepButton = new Button("Step");  stepButton.addActionListener(new StepButtonHandler());

        resetButton = new Button("Reset");
        resetButton.addActionListener(new ResetButtonHandler());
        resetButton.setEnabled(false);

        Panel p = new Panel();
        p.setLayout(new GridLayout(1,5));
        p.add(startButton);    p.add(new Label(""));   p.add(stepButton);
        p.add(new Label(""));  p.add(resetButton);

        add(p,"South");  add(new Label(" "),"North");  add(new Label(" "),"East");  add(new Label(" "),"West");
    }

    public Vector getDataVector() {
        DataBuilder b = new RandomDataBuilder();
        int[]       a = b.getDataVector(20);
        Vector      v = new Vector(a.length);
        for(int i=0;i<a.length;i++) {
            v.addElement(new Integer(a[i]+1));
        }
        return v;
    }

    public void update(Observable o,Object arg) {
        resetButton.setEnabled(true);
    }

    class StartButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(false); stepButton.setEnabled(false);
            Vector v = getDataVector();
            tp.setData(v);  tp.redraw(RedBlackTreeApplet.this);
        }
    }

    class ResetButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(true);  stepButton.setEnabled(true);  resetButton.setEnabled(false);
            step_state = 0;
        }
    }

    class StepButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch(step_state) {
                case 0:
                    startButton.setEnabled(false);
                    Vector v = getDataVector();
                    tp.setData(v);  tp.reset(); step_state = 1;  break;
                case 1:
                    if (tp.step() == true) {
                        stepButton.setEnabled(false);  resetButton.setEnabled(true); step_state = 0;
                    }
                    break;
                default:
                    step_state = 0;   break;
            }
        }
    }

    public void start() {}
}