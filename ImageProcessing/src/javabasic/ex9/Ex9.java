package javabasic.ex9;

import javax.swing.*;

class PictureView extends JFrame {
    PictureView(final String _title) {
        super(_title);
        Icon image = new ImageIcon(getClass().getResource("1.jgp"));
    }
}

public class Ex9 {
}
