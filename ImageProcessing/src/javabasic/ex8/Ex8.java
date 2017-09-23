package javabasic.ex8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Pictures(1.jpg, 2.jpg, 3.jpg, 4.jpg) Copyright by Reva(http://mister1315.tistory.com)

class CustomFrame extends JFrame {
    enum ButtonType {
        LeftUp, RightUp, LeftDown, RightDown
    }
    private static final String[] COMMENT = {"방끗 웃는 읭읭이", "부들부들 떠는 읭읭이", "운명이라고 믿는 읭읭이", "자다가 깬 읭읭이"};
    private static final String BUTTON_COMMENT_PRESS_BEFORE = "설명보기";
    private static final String BUTTON_COMMENT_PRESS_AFTER = "배경색 변경";
    private JLabel leftUpSpecLbl;
    private JLabel rightUpSpecLbl;
    private JLabel leftDownSpecLbl;
    private JLabel rightDownSpecLbl;
    private JButton leftUpSpecBtn;
    private JButton rightUpSpecBtn;
    private JButton leftDownSpecBtn;
    private JButton rightDownSpecBtn;

    CustomFrame(final String _title) {
        super(_title);
        setLayout(new GridLayout(2,2,5,5));

        JPanel leftUp = new JPanel();
        leftUp.setLayout(new GridLayout(1,2));
        JPanel leftUpSpec = new JPanel();
        leftUpSpec.setLayout(new BorderLayout());
        leftUpSpecBtn = new JButton(BUTTON_COMMENT_PRESS_BEFORE);
        leftUpSpecLbl = new JLabel();
        leftUpSpecLbl.setOpaque(true);
        leftUpSpec.add(leftUpSpecBtn, BorderLayout.NORTH);
        leftUpSpec.add(leftUpSpecLbl, BorderLayout.CENTER);
        JLabel leftUpImage = new JLabel(new ImageIcon(getClass().getResource("1.jpg")));
        leftUp.add(leftUpImage);
        leftUp.add(leftUpSpec);
        leftUpSpecBtn.addActionListener(new ButtonHandler(this, ButtonType.LeftUp));

        JPanel rightUp = new JPanel();
        rightUp.setLayout(new GridLayout(1, 2));
        JPanel rightUpSpec = new JPanel();
        rightUpSpec.setLayout(new BorderLayout());
        rightUpSpecBtn = new JButton(BUTTON_COMMENT_PRESS_BEFORE);
        rightUpSpecLbl = new JLabel();
        rightUpSpecLbl.setOpaque(true);
        rightUpSpec.add(rightUpSpecBtn, BorderLayout.NORTH);
        rightUpSpec.add(rightUpSpecLbl, BorderLayout.CENTER);
        JLabel rightUpImage = new JLabel(new ImageIcon(getClass().getResource("2.jpg")));
        rightUp.add(rightUpImage);
        rightUp.add(rightUpSpec);
        rightUpSpecBtn.addActionListener(new ButtonHandler(this, ButtonType.RightUp));

        JPanel leftDown = new JPanel();
        leftDown.setLayout(new GridLayout(1,2));
        JPanel leftDownSpec = new JPanel();
        leftDownSpec.setLayout(new BorderLayout());
        leftDownSpecBtn = new JButton(BUTTON_COMMENT_PRESS_BEFORE);
        leftDownSpecLbl = new JLabel();
        leftDownSpecLbl.setOpaque(true);
        leftDownSpec.add(leftDownSpecBtn, BorderLayout.NORTH);
        leftDownSpec.add(leftDownSpecLbl, BorderLayout.CENTER);
        JLabel leftDownImage = new JLabel(new ImageIcon(getClass().getResource("3.jpg")));
        leftDown.add(leftDownImage);
        leftDown.add(leftDownSpec);
        leftDownSpecBtn.addActionListener(new ButtonHandler(this, ButtonType.LeftDown));

        JPanel rightDown = new JPanel();
        rightDown.setLayout(new GridLayout(1,2));
        JPanel rightDownSpec = new JPanel();
        rightDownSpec.setLayout(new BorderLayout());
        rightDownSpecBtn = new JButton(BUTTON_COMMENT_PRESS_BEFORE);
        rightDownSpecLbl = new JLabel();
        rightDownSpecLbl.setOpaque(true);
        rightDownSpec.add(rightDownSpecBtn, BorderLayout.NORTH);
        rightDownSpec.add(rightDownSpecLbl, BorderLayout.CENTER);
        JLabel rightDownImage = new JLabel(new ImageIcon(getClass().getResource("4.jpg")));
        rightDown.add(rightDownImage);
        rightDown.add(rightDownSpec);
        rightDownSpecBtn.addActionListener(new ButtonHandler(this, ButtonType.RightDown));

        add(leftUp);
        add(rightUp);
        add(leftDown);
        add(rightDown);
    }

    class ButtonHandler implements ActionListener {
        private ButtonType buttonType;
        private Component parent;
        private boolean isPressed;
        ButtonHandler(final Component _parent, final ButtonType _type) {
            parent = _parent;
            buttonType = _type;
            isPressed = false;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isPressed) {
                switch (buttonType) {
                    case LeftUp:
                        leftUpSpecLbl.setText(COMMENT[0]);
                        leftUpSpecBtn.setText(BUTTON_COMMENT_PRESS_AFTER);
                        isPressed = true;
                        break;
                    case RightUp:
                        rightUpSpecLbl.setText(COMMENT[1]);
                        rightUpSpecBtn.setText(BUTTON_COMMENT_PRESS_AFTER);
                        isPressed = true;
                        break;
                    case LeftDown:
                        leftDownSpecLbl.setText(COMMENT[2]);
                        leftDownSpecBtn.setText(BUTTON_COMMENT_PRESS_AFTER);
                        isPressed = true;
                        break;
                    case RightDown:
                        rightDownSpecLbl.setText(COMMENT[3]);
                        rightDownSpecBtn.setText(BUTTON_COMMENT_PRESS_AFTER);
                        isPressed = true;
                        break;
                }
            } else {
                Color color = JColorChooser.showDialog(parent, BUTTON_COMMENT_PRESS_AFTER, Color.WHITE);
                switch (buttonType) {
                    case LeftUp:
                        leftUpSpecLbl.setBackground(color);
                        isPressed = true;
                        break;
                    case RightUp:
                        rightUpSpecLbl.setBackground(color);
                        isPressed = true;
                        break;
                    case LeftDown:
                        leftDownSpecLbl.setBackground(color);
                        isPressed = true;
                        break;
                    case RightDown:
                        rightDownSpecLbl.setBackground(color);
                        isPressed = true;
                        break;
                }
            }
        }
    }
}

public class Ex8 {
    public static void main(String[] args) {
        CustomFrame frame = new CustomFrame("과제8");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700, 415);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}