package ex7;

import java.io.File;

/******************************************************************************
 *  Compilation:  javac UF.java
 *  Execution:    java UF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:  tinyUF.txt, mediumUF.txt, largeUF.txt
 *
 *  Weighted quick-union by rank with path compression by halving.
 *
 *  % java UF < tinyUF.txt
 *  10
 *  4 3
 *  3 8
 *  6 5
 *  9 4
 *  2 1
 *  5 0
 *  7 2
 *  6 1
 *  2 components
 *
 ******************************************************************************/
public class UF {

    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;     // number of components

    /* Initializes an empty union-find data structure with N sites 0 through N-1. 
     * Each site is initially in its own component.
     * param  N : the number of sites
     * throws IllegalArgumentException if N < 0  */
    public UF(int N) {
        if (N < 0) throw new IllegalArgumentException();
        count = N;
        parent = new int[N];
        rank = new byte[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /* Returns the component identifier for the component containing site p.
     *
     * param  p : the integer representing one site
     * return the component identifier for the component containing site p
     * throws IndexOutOfBoundsException unless 0 <= p < N  */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    /* Returns the number of components.
     * return the number of components (between 1 and N)  */
    public int count() {
        return count;
    }
  
    /* Returns true if the the two sites are in the same component.
     * param  p : the integer representing one site
     * param  q : the integer representing the other site
     * return true if the two sites p and q are in the same component;
     *         false otherwise
     * throws IndexOutOfBoundsException unless
     *         both 0 <= p < N and 0 <= q < N     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
  
    /* Merges the component containing site p with the the component containing site q.
     * param  p : the integer representing one site
     * param  q : the integer representing the other site
     * throws IndexOutOfBoundsException unless
     *         both 0 <= p < N and 0 <= q < N   */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if      (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int N = parent.length;
        if (p < 0 || p >= N) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));  
        }
    }

    /* Reads in a an integer N and a sequence of pairs of integers (between 0 and N-1) 
     * from standard input, where each integer in the pair represents some site;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output. */
    public static void main(String[] args) {
        ReadData readData = new ReadData(UF.class.getResource(".").getPath() + File.separator + "mediumUF.txt");
        int N = readData.getmCount();
        UF uf = new UF(N);
        for(int i = 0; i < N; i++) {
            int p = readData.getmData(i, 0);
            int q = readData.getmData(i, 1);
            if (uf.connected(p, q))
                continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }

//        int N = StdIn.readInt();
//        UF uf = new UF(N);
//        while (!StdIn.isEmpty()) {
//            int p = StdIn.readInt();
//            int q = StdIn.readInt();
//            if (uf.connected(p, q)) continue;
//            uf.union(p, q);
//            StdOut.println(p + " " + q);
//        }
        StdOut.println(uf.count() + " components");
    }
}