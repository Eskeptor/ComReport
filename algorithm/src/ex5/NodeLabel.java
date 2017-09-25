package ex5;

import java.awt.*;

public class NodeLabel  {
    private int       ParentKey;
    private int       Key;
    private boolean   WasModified;
    private String    Text;
    private boolean   IsVisible;
    private Color Foreground;
    private Color     Background;
    private Dimension Size;
    private Point     Location;
    private int       Row;
    private int       Col;
    private boolean   Visiting;

    public NodeLabel(String s) {
        Visiting    = false;
        Text        = s;
        ParentKey   = 0;
        Key         = 0;
        WasModified = true;
        Size        = new Dimension(10,10);
        Location    = null;
        Row         = 0;
        Col         = 0;
    }

    public Dimension getSize() {
        // return a copy
        return new Dimension(Size);
    }

    public void setSize(int width,int height) {
        Size = new Dimension(width,height);
    }

    public Point getLocation() {
        // return a copy
        return new Point(Location);
    }

    public void print(String s) {
        /*
        Dimension d = getSize();
        Point     p = Location;
        System.out.println(s + " " + getKey() + " X:"+Integer.toString(p.x)+" Y:"+Integer.toString(p.y) + " w:" + Integer.toString(d.width) + " h:" + Integer.toString(d.height) );
        */
    }

    public void setVisiting(boolean b) {
        Visiting = b;
    }

    public boolean getVisiting() {
        return Visiting;
    }

    public void setLocationAndSize(Dimension d,int rows,int cols) {
        int w;
        int h;

        w = (d.width  / cols);
        h = (d.height / rows);

        Size     = new Dimension((int)(w * .66),(int)(h * .66));
        Location = new Point(w * Col,h * Row);
    }

    public void setRowCol(int row,int col) {
        Row = row;
        Col = col;
    }

    public void setText(String s) {
        Text = s;
    }

    public String getText() {
        return Text;
    }

    public void setForeground(Color c) {
        Foreground = c;
    }

    public void setBackground(Color c) {
        Background = c;
    }

    public void setModified(boolean b) {
        WasModified = b;
    }

    public boolean wasModified() {
        boolean b = WasModified;
        WasModified = false;
        return b;
    }

    public void setParentKey(int key) {
        ParentKey = key;
    }

    public int getParentKey() {
        return ParentKey;
    }

    public int getKey() {
        return Key;
    }

    public void setKey(int k) {
        Key = k;
    }

    public void setVisible(boolean b) {
        IsVisible = b;
    }

    public boolean isVisible() {
        return IsVisible;
    }

    public Color getBackground() {
        return Background;
    }

    public Color getForeground() {
        return Foreground;
    }

    public void paint(Graphics g) {
        Point p = getLocation();
        if (IsVisible) {
            g.setColor(getBackground());
            g.fillRect(p.x,p.y,Size.width,Size.height);
            g.setColor(getForeground());
            int scenter = g.getFontMetrics().stringWidth(Text) / 2;
            int ncenter = Size.width / 2;
            int w       = ncenter - scenter;
            g.drawString(Text,p.x + w,p.y + Size.height - 2);
        }
    }
}
