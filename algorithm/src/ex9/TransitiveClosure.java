package ex9;

import ex10.StdOut;

/******************************************************************************
 *  Compilation:  javac TransitiveClosure.java
 *  Execution:    java TransitiveClosure filename.txt
 *  Dependencies: Digraph.java DepthFirstDirectedPaths.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *
 *  Compute transitive closure of a digraph and support
 *  reachability queries.
 *
 *  Preprocessing time: O(V(E + V)) time.
 *  Query time: O(1).
 *  Space: O(V^2).
 *
 *  % java TransitiveClosure tinyDG.txt
 *         0  1  2  3  4  5  6  7  8  9 10 11 12
 *  --------------------------------------------
 *    0:   T  T  T  T  T  T                     
 *    1:      T                                 
 *    2:   T  T  T  T  T  T                     
 *    3:   T  T  T  T  T  T                     
 *    4:   T  T  T  T  T  T                     
 *    5:   T  T  T  T  T  T                     
 *    6:   T  T  T  T  T  T  T        T  T  T  T
 *    7:   T  T  T  T  T  T  T  T  T  T  T  T  T
 *    8:   T  T  T  T  T  T  T  T  T  T  T  T  T
 *    9:   T  T  T  T  T  T           T  T  T  T
 *   10:   T  T  T  T  T  T           T  T  T  T
 *   11:   T  T  T  T  T  T           T  T  T  T
 *   12:   T  T  T  T  T  T           T  T  T  T
 *
 ******************************************************************************/
/*  The TransitiveClosure class represents a data type for computing the transitive closure of a digraph.
 *  This implementation runs depth-first search from each vertex. The constructor takes time 
 *  proportional to V(V + E)  (in the worst case) and uses space proportional to V2, where V is 
 *  the number of vertices and <em>E</em> is the number of edges. */
public class TransitiveClosure {
    private DirectedDFS[] tc;  // tc[v] = reachable from v

    /* Computes the transitive closure of the digraph G.
     * param G : the digraph  */
    public TransitiveClosure(Digraph G) {
        tc = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++)
            tc[v] = new DirectedDFS(G, v);
    }

    /**
     * Is there a directed path from vertex <tt>v</tt> to vertex <tt>w</tt> in the digraph?
     * @param v the source vertex
     * @param w the target vertex
     * @return <tt>true</tt> if there is a directed path from <tt>v</tt> to <tt>w</tt>,
     *    <tt>false</tt> otherwise
     */
    public boolean reachable(int v, int w) {
        return tc[v].marked(w);
    }

    /**
     * Unit tests the <tt>TransitiveClosure</tt> data type.
     */
    public static void main(String[] args) {
        String str = TransitiveClosure.class.getResource("reva.txt").getPath();
//        In in = new In(args[0]);
        In in = new In(str);
        Digraph G = new Digraph(in);

        TransitiveClosure tc = new TransitiveClosure(G);

        // print header
        ex10.StdOut.print("     ");
        for (int v = 0; v < G.V(); v++)
            ex10.StdOut.printf("%3d", v);
        ex10.StdOut.println();
        ex10.StdOut.println("--------------------------------------------");

        // print transitive closure
        for (int v = 0; v < G.V(); v++) {
            ex10.StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (tc.reachable(v, w)) ex10.StdOut.printf("  T");
                else                    ex10.StdOut.printf("   ");
            }
            StdOut.println();
        }

        TarjanSCC.start(G);
    }

}