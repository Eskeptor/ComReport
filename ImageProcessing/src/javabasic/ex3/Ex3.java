package javabasic.ex3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonHandler implements ActionListener {
    private static Component parent;
    ButtonHandler(final Component _component) {
        parent = _component;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        JOptionPane.showMessageDialog(parent, String.format("%s 버튼을 클릭함", button.getName()));
    }
}

class ButtonFrame extends JFrame {
    ButtonFrame(final String _title) {
        super(_title);
        setLayout(new FlowLayout());
        ButtonHandler handler = new ButtonHandler(this);

        JButton button1 = new JButton("Edit");
        button1.setBackground(Color.YELLOW);
        button1.setForeground(Color.BLUE);
        button1.addActionListener(handler);
        button1.setName("Edit");
        add(button1);

        JButton button2 = new JButton("", new ImageIcon(getClass().getResource("start.gif")));
        button2.addActionListener(handler);
        button2.setName("start(아이콘)");
        add(button2);

        JButton button3 = new JButton("", new ImageIcon(getClass().getResource("stop.jpg")));
        button3.addActionListener(handler);
        button3.setName("stop(아이콘)");
        add(button3);

        JButton button4 = new JButton("start", new ImageIcon(getClass().getResource("start.gif")));
        button4.addActionListener(handler);
        button4.setName("start(아이콘+텍스트)");
        add(button4);
    }
}

public class Ex3 {
    public static void main(String[] args) {
        ButtonFrame frame = new ButtonFrame("과제3");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setVisible(true);
    }
}