package ex5;

import java.util.ArrayList;
import java.util.Collections;

public class MinMax2 extends MinMax {
    @Override
    public ArrayList<Integer> minMax(ArrayList<Integer> _array) {
        ArrayList<Integer> arrayList = _array;
        Collections.sort(arrayList);
        return arrayList;
    }
}
