package javabasic.ex2;

import javax.swing.*;
import java.awt.FlowLayout;

class CustomFrame extends JFrame {
    CustomFrame(final String _title) {
        super(_title);

        setLayout(new FlowLayout());

        JLabel label1 = new JLabel("작년에 찍었던 사진");
        label1.setIcon(new ImageIcon(getClass().getResource("1.jpg")));
        add(label1);

        JLabel label2 = new JLabel("이번 실습때 찍었던 사진1");
        label2.setIcon(new ImageIcon(getClass().getResource("2.jpg")));
        add(label2);

        JLabel label3 = new JLabel("이번 실습때 찍었던 사진2");
        label3.setIcon(new ImageIcon(getClass().getResource("3.jpg")));
        add(label3);
    }
}

public class Ex2 {
    public static void main(String[] args) {
        CustomFrame frame = new CustomFrame("과제2");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setVisible(true);
    }
}