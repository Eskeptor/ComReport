package ex8;

public abstract class Board {
    private Dice mDice1;
    private Dice mDice2;
    private int mFaceValue1;
    private int mFaceValue2;
    private int mCurCell1;
    private int mCurCell2;
    private int mGoal;
    protected int[] mCell;

    public Board(final int _numOfCells, final Dice _dice1, final Dice _dice2) {
        mGoal = _numOfCells;
        mCell = new int[_numOfCells];
        for (int i = 0; i < mGoal; i++)
            mCell[i] = i;
        mDice1 = _dice1;
        mDice2 = _dice2;
    }

    public abstract void makeTrapCells();

    public abstract void makeBonusCells();

    public WinningStatus roll() {
        mFaceValue1 = mDice1.roll();
        mFaceValue2 = mDice2.roll();

        mCurCell1 += mFaceValue1;
        mCurCell2 += mFaceValue2;

        if (mCurCell1 >= mGoal && mCurCell2 >= mGoal)
            return WinningStatus.Draw;
        else if (mCurCell1 >= mGoal && mCurCell2 < mGoal)
            return WinningStatus.Player;
        else if (mCurCell1 < mGoal && mCurCell2 >= mGoal)
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

}
