package ex8;

public abstract class DiceGame {
    private Board mBoard;

    public abstract Dice createDice();
    public abstract Board createBoard(final int _numOfCells, final Dice _dice1, final Dice _dice2);

    public Board createDiceGame(final int _numOfCells) {
        Dice dice1 = createDice();
        Dice dice2 = createDice();

        mBoard = new D6Board(_numOfCells, dice1, dice2);
        mBoard.makeTrapCells();
        mBoard.makeBonusCells();

        return mBoard;
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