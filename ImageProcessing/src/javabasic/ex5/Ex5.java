package javabasic.ex5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class ComboBoxFrame extends JFrame {
    ComboBoxFrame(final String _title) {
        super(_title);
        setLayout(new FlowLayout());

        final String[] list = {"사진1", "사진2", "사진3"};
        final Icon[] icons = {new ImageIcon(getClass().getResource("1.jpg")),
                new ImageIcon(getClass().getResource("2.jpg")),
                new ImageIcon(getClass().getResource("3.jpg"))};
        JLabel lblIcon = new JLabel(icons[0]);
        JComboBox<String> jComboBox = new JComboBox<>(list);
        jComboBox.setMaximumRowCount(3);
        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                    lblIcon.setIcon(icons[jComboBox.getSelectedIndex()]);
            }
        });

        add(jComboBox);
        add(lblIcon);
    }
}

public class Ex5 {
    public static void main(String[] args) {
        ComboBoxFrame frame = new ComboBoxFrame("과제5");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,200);
        frame.setVisible(true);
    }
}