package ex8;

import java.io.Serializable;

public class Entry implements Serializable {
    private String mName;
    private int mWin;
    private int mLose;
    private int mDraw;

    public Entry(final String _playerName, final int _win, final int _lose, final int _draw) {
        mName = _playerName;
        mWin = _win;
        mDraw = _draw;
        mLose = _lose;
    }
    public Entry() {}

    public String getName() {
        return mName;
    }

    public void setName(final String _name) {
        mName = _name;
    }

    public int getWin() {
        return mWin;
    }

    public void setWin(final int _win) {
        mWin = _win;
    }

    public int getLose() {
        return mLose;
    }

    public void setLose(final int _lose) {
        mLose = _lose;
    }

    public int getDraw() {
        return mDraw;
    }

    public void setDraw(final int _draw) {
        mDraw = _draw;
    }
}
