package javabasic.ex6;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

class ListFrame extends JFrame {
    ListFrame(final String _title) {
        super(_title);
        final String[] imageNames = {"보글젤", "피콜로", "팬텀", "곰돌이", "드래곤", "스티치", "라이언"};
        final Icon[] images = {new ImageIcon(getClass().getResource("1.jpg")),
                new ImageIcon(getClass().getResource("2.jpg")),
                new ImageIcon(getClass().getResource("3.jpg")),
                new ImageIcon(getClass().getResource("4.jpg")),
                new ImageIcon(getClass().getResource("5.jpg")),
                new ImageIcon(getClass().getResource("6.jpg")),
                new ImageIcon(getClass().getResource("7.jpg"))};
        setLayout(new FlowLayout());

        JList<String> list = new JList<>(imageNames);
        list.setVisibleRowCount(5);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(list));

        JLabel imageLabel = new JLabel();
        add(imageLabel);

        list.addListSelectionListener((event)-> imageLabel.setIcon(images[list.getSelectedIndex()]));

    }
}

public class Ex6 {
    public static void main(String[] args) {
        ListFrame frame = new ListFrame("과제6");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(450, 700);
        frame.setVisible(true);
    }
}
