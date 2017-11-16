package ex5;

import java.util.ArrayList;
import java.util.Collections;

public class MinMax1 extends MinMax {
    @Override
    public ArrayList<Integer> minMax(ArrayList<Integer> _array) {
        ArrayList<Integer> arrayList = _array;
        int minValue = Collections.min(_array);
        int maxValue = Collections.min(_array);
        arrayList.set(0, minValue);
        arrayList.set(_array.size() - 1, maxValue);
        return arrayList;
    }
}
