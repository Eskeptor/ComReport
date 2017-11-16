package ex8;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Scores implements Serializable {
    private ArrayList<Entry> mScores;

    public Scores() {
        mScores = new ArrayList<>();
    }

    public ArrayList<Entry> getScores() {
        return mScores;
    }

    public void addScore(final Entry _score) {
        mScores.add(_score);
    }

    public Entry getEntry(final String _playerName) {
        Iterator<Entry> iter = mScores.iterator();
        Entry entry;
        while (iter.hasNext()) {
            entry = iter.next();
            if (entry.getName().equals(_playerName)) {
                return entry;
            }
        }
        entry = new Entry(_playerName, 0, 0, 0);
        addScore(entry);
        return entry;
    }

    public java.awt.List getList() {
        final java.awt.List list = new java.awt.List();
        Iterator<Entry> iter = mScores.iterator();
        Entry entry;
        while (iter.hasNext()) {
            entry = iter.next();
            list.add(entry.getName() + ":" + entry.getWin() + "wins " + entry.getLose() + "loses " + entry.getDraw() + "draws");
        }
        return list;
    }
}
