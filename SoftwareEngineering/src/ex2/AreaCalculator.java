package ex2;

import java.util.ArrayList;
import java.util.Iterator;

public class AreaCalculator {
    private ArrayList<Shape> mShapes = new ArrayList<>();
    private double mAreaSum = 0.0;

    public void calculate() {
        Iterator<Shape> iter = mShapes.iterator();
        while(iter.hasNext()) {
            Shape curShape = iter.next();
            mAreaSum += curShape.area();
        }
        System.out.println(mAreaSum);
    }

    public void addShape(final Shape _shape) {
        mShapes.add(_shape);
    }

    public double getmAreaSum() {
        return mAreaSum;
    }
}
