package ex8;

public class D6 extends Dice {
    @Override
    public int roll() {
        return (int)(Math.random() * 6 + 1);
    }
}
