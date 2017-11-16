package ex8;

public class D6DiceGame extends DiceGame {
    @Override
    public Dice createDice() {
        return new D6();
    }

    @Override
    public Board createBoard(int _numOfCells, Dice _dice1, Dice _dice2) {
        return new D6Board(_numOfCells, _dice1, _dice2);
    }
}
