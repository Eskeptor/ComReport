package ex8;

import javax.swing.*;

public class PebbleLabel extends JLabel {
    private int xpos, ypos;

    PebbleLabel(final String _txt, final int _x, final int _y) {
        super(_txt, SwingConstants.CENTER);
        xpos = _x;
        ypos = _y;
    }

    public int getXpos() { return xpos; }
    public int getYpos() { return ypos; }
}
