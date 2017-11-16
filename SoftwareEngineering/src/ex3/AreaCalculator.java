package ex3;

import java.util.ArrayList;

public abstract class AreaCalculator {
    protected ArrayList<Shape> mShapes = new ArrayList<>();
    protected double mAreaResult;
    public abstract void calculate();
    protected void addShape(final Shape _shape) {
        mShapes.add(_shape);
    }
    protected double getAreaResult() {
        return mAreaResult;
    }
}
