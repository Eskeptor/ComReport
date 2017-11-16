package ex3;

import java.util.Iterator;

public class MultiCalculator extends AreaCalculator {
    @Override
    public void calculate() {
        Iterator<Shape> iter = mShapes.iterator();
        mAreaResult = 1.0;
        while(iter.hasNext()) {
            Shape curShape = iter.next();
            mAreaResult *= curShape.area();
        }
    }
}
