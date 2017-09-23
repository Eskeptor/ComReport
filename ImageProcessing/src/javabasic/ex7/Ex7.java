package javabasic.ex7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class MouseFrame extends JFrame {
    private Icon imageSource1;
    private Icon imageSource2;
    private JLabel image;
    class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {
            image.setIcon(imageSource2);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            image.setIcon(imageSource1);
        }
    }
    public MouseFrame(final String _title) {
        super(_title);
        JPanel panel = new JPanel();
        imageSource1 = new ImageIcon(getClass().getResource("1.jpg"));
        imageSource2 = new ImageIcon(getClass().getResource("2.jpg"));
        image = new JLabel();
        image.setIcon(imageSource1);
        panel.add(image);
        add(panel, BorderLayout.CENTER);
        MouseHandler handler = new MouseHandler();
        panel.addMouseListener(handler);
    }
}

public class Ex7 {
    public static void main(String[] args) {
        MouseFrame frame = new MouseFrame("과제7");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }
}