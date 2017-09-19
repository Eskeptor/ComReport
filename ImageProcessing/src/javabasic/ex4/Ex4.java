package javabasic.ex4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

enum COLOR {
    RED, BLUE, GREEN, BLACK
}

class RadioButtonHandler implements ItemListener {
    private JLabel lblText;
    private COLOR color;
    RadioButtonHandler(JLabel _lblText, final COLOR _color) {
        lblText = _lblText;
        color = _color;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        switch (color) {
            case RED:
                lblText.setText("\"Red\"를 선택했습니다.");
                lblText.setBackground(Color.RED);
                lblText.setForeground(Color.WHITE);
                break;
            case BLUE:
                lblText.setText("\"Blue\"를 선택했습니다.");
                lblText.setBackground(Color.BLUE);
                lblText.setForeground(Color.WHITE);
                break;
            case GREEN:
                lblText.setText("\"Green\"을 선택했습니다.");
                lblText.setBackground(Color.GREEN);
                lblText.setForeground(Color.BLACK);
                break;
            case BLACK:
                lblText.setText("\"Black\"을 선택했습니다.");
                lblText.setBackground(Color.BLACK);
                lblText.setForeground(Color.WHITE);
                break;
        }
    }
}

class RadioFrame extends JFrame {

    public RadioFrame(final String _title) {
        super(_title);
        setLayout(new GridLayout(5,1));

        JRadioButton buttonRed = new JRadioButton("Red", false);
        JRadioButton buttonBlue = new JRadioButton("Blue", false);
        JRadioButton buttonGreen = new JRadioButton("Green", false);
        JRadioButton buttonBlack = new JRadioButton("Black", false);
        JLabel lblText = new JLabel("색을 선택하세요");
        lblText.setOpaque(true);
        lblText.setHorizontalAlignment(JLabel.CENTER);

        buttonRed.addItemListener(new RadioButtonHandler(lblText, COLOR.RED));
        buttonBlue.addItemListener(new RadioButtonHandler(lblText, COLOR.BLUE));
        buttonGreen.addItemListener(new RadioButtonHandler(lblText, COLOR.GREEN));
        buttonBlack.addItemListener(new RadioButtonHandler(lblText, COLOR.BLACK));

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(buttonRed);
        radioGroup.add(buttonBlue);
        radioGroup.add(buttonGreen);
        radioGroup.add(buttonBlack);

        add(buttonRed);
        add(buttonBlue);
        add(buttonGreen);
        add(buttonBlack);
        add(lblText);
    }
}

public class Ex4 {
    public static void main(String[] args) {
        RadioFrame frame = new RadioFrame("과제4");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setVisible(true);
    }
}