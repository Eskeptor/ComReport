package ex8;

public class PebbleNode {
    private int value;
    private boolean isCheck;
    private int x, y;
    PebbleNode(final int _value, final int _x, final int _y) {
        value = _value;
        isCheck = false;
        x = _x;
        y = _y;
    }

    public void check() {
        isCheck = true;
    }

    public int getValue() {
        return value;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public int getX() { return x; }

    public int getY() { return y; }
}
