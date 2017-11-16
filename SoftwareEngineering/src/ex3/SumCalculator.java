package ex3;

import java.util.Iterator;

public class SumCalculator extends AreaCalculator {
    @Override
    public void calculate() {
        Iterator<Shape> iter = mShapes.iterator();
        mAreaResult = 0.0;
        while(iter.hasNext()) {
            Shape curShape = iter.next();
            mAreaResult += curShape.area();
        }
    }
}
