package javabasic;

public class DiceGame {
    private Dice mDice1;
    private Dice mDice2;
    private int mFaceValue1;
    private int mFaceValue2;
    private int[] mCell;
    private int mCurCell1;
    private int mCurCell2;

    private final int GOAL = 29;

    public DiceGame() {
        mDice1 = new Dice();
        mDice2 = new Dice();
        mFaceValue1 = mFaceValue2 = mCurCell1 = mCurCell2 = 0;
        mCell = new int[30];

        for (int i = 0; i < 30; i++) {
            mCell[i] = i;
        }

        makeTrapCells();
        makeBonusCells();
    }

    public WinningStatus roll() {
        mFaceValue1 = mDice1.roll();
        mFaceValue2 = mDice2.roll();

        mCurCell1 += mFaceValue1;
        mCurCell2 += mFaceValue2;

        if (mCurCell1 >= GOAL && mCurCell2 >= GOAL)
            return WinningStatus.Draw;
        else if (mCurCell1 >= GOAL && mCurCell2 < GOAL)
            return WinningStatus.Player;
        else if (mCurCell1< GOAL && mCurCell2 >=GOAL)
            return WinningStatus.AlphaDice;
        else {
            if (mCurCell1 != mCell[mCurCell1])
                mCurCell1 = mCell[mCurCell1];
            if (mCurCell2 != mCell[mCurCell2])
                mCurCell2 = mCell[mCurCell2];
            return WinningStatus.NotYet;
        }
    }

    public int getFaceValue1() {
        return mFaceValue1;
    }

    public int getFaceValue2() {
        return mFaceValue2;
    }

    public int getCurCellPos1() {
        return mCurCell1;
    }

    public int getCurCellPos2() {
        return mCurCell2;
    }

    private void makeTrapCells() {
        mCell[10] = 0;
        mCell[28] = 0;
        mCell[8] = 3;
        mCell[15] = 5;
        mCell[21] = 12;
        mCell[25] = 17;
    }

    private void makeBonusCells() {
        mCell[11] = 20;
        mCell[26] = 27;
        mCell[9] = 14;
        mCell[16] = 22;
    }

    public Scores load() {
        try {
            return ScoreDAO.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Scores scores) {
        try {
            ScoreDAO.save(scores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}