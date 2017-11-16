package ex8;

public class D6Board extends Board {
    public D6Board(final int _numOfCells, final Dice _dice1, final Dice _dice2) {
        super(_numOfCells, _dice1, _dice2);
    }

    @Override
    public void makeTrapCells() {
        mCell[10] = 0;
        mCell[28] = 0;
        mCell[8] = 3;
        mCell[15] = 5;
        mCell[21] = 12;
        mCell[25] = 17;
    }

    @Override
    public void makeBonusCells() {
        mCell[11] = 20;
        mCell[26] = 27;
        mCell[9] = 14;
        mCell[16] = 22;
    }
}
