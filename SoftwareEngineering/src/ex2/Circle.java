package ex2;

public class Circle extends Shape {
    private double mRound;

    public Circle(final double _r) {
        mRound = _r;
    }

    @Override
    public double area() {
        return mRound * mRound * 3.14;
    }
}
