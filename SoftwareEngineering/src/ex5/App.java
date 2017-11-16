package ex5;

import java.util.ArrayList;

public class App {
    public static void testLSP(MinMax minMax) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(100);
        arrayList.add(500);
        arrayList.add(50);
        arrayList.add(505);
        arrayList.add(30);

        ArrayList<Integer> result = minMax.minMax(arrayList);
        System.out.println("smallest Value: " + result.get(0));
        System.out.println("largest Value: " + result.get(result.size() - 1));
    }

    public static void main(String[] args) {
//        testLSP(new MinMax());
//        testLSP(new MinMax1());
        testLSP(new MinMax2());
    }
}