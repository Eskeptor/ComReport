package ex5;

import java.util.*;

// random equally distributed, no duplicates
public class RandomDataBuilder extends DataBuilder {

    public int[] getDataVector(int size) {
        int[]     v    = new int[size];
        boolean[] used = new boolean[size];
        Random    rand = new Random();
        int       i;
        int       j;

        for(j=0;j<used.length;j++) {
            used[j] = false;
        }
        i = 0;
        while(i<v.length) {
            int x = Math.abs(rand.nextInt()) % size;
            if (!used[x]) {
                used[x] = true;
                v[i] = x;
                i++;
            }
        }

        return v;
    }
}
