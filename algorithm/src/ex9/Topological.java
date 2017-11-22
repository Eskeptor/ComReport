package ex9;

/******************************************************************************
 *  Compilation:  javac Topoological.java
 *  Execution:    java  Topological filename.txt separator
 *  Dependencies: Digraph.java DepthFirstOrder.java DirectedCycle.java
 *                EdgeWeightedDigraph.java EdgeWeightedDirectedCycle.java
 *                SymbolDigraph.java
 *  Data files:   jobs.txt
 *
 *  Compute topological ordering of a DAG or edge-weighted DAG.
 *  Runs in O(E + V) time.
 *
 *  % java Topological jobs.txt "/"
 *  Calculus
 *  Linear Algebra
 *  Introduction to CS
 *  Programming Systems
 *  Algorithms
 *  Theoretical CS
 *  Artificial Intelligence
 *  Machine Learning
 *  Neural Networks
 *  Robotics
 *  Scientific Computing
 *  Computational Biology
 *  Databases
 *
 *
 ******************************************************************************/
/* The Topological class represents a data type for determining a topological order of a directed 
 * acyclic graph (DAG). Recall, a digraph has a topological order if and only if it is a DAG.
 * The hasOrder operation determines whether the digraph has a topological order, and if so, 
 * the order operation returns one. This implementation uses depth-first search.
 * The constructor takes time proportional to V + E  (in the worst case),  where V is the number 
 * of vertices and E is the number of edges. Afterwards, the hasOrder and rank operations takes 
 * constant time;  the order operation takes time proportional to V.
 * See DirectedCycle, DirectedCycleX, and EdgeWeightedDirectedCycle to compute a directed 
 * cycle if the digraph is not a DAG.
 * See TopologicalX for a nonrecursive queue-based algorithm to compute a topological order of a DAG. */
public class Topological {
    private Iterable<Integer> order;  // topological order
    private int[] rank;               // rank[v] = position of vertex v in topological order

    /* Determines whether the digraph <tt>G</tt> has a topological order and, if so, finds such a 
     * topological order. Param G the digraph  */
    public Topological(Digraph G) {
        DirectedCycle finder = new DirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
            rank = new int[G.V()];
            int i = 0;
            for (int v : order)
                rank[v] = i++;
        }
    }

    /* Determines whether the edge-weighted digraph G has a topological order and, if so, finds 
     * such an order. Pparam G the edge-weighted digraph  */
    public Topological(EdgeWeightedDigraph G) {
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    /* Returns a topological order if the digraph has a topologial order, and null otherwise.
     * return a topological order of the vertices (as an interable) if the digraph has a topological order 
     * (or equivalently, if the digraph is a DAG), and null otherwise  */
    public Iterable<Integer> order() {
        return order;
    }

    /* Does the digraph have a topological order?
     * return true if the digraph has a topological order (or equivalently, if the digraph is a DAG), 
     * and false otherwise  */
    public boolean hasOrder() {
        return order != null;
    }

    /* The rank of vertex v in the topological order; -1 if the digraph is not a DAG
     * return the position of vertex v in a topological order of the digraph; 
     * -1 if the digraph is not a DAG
     * throws IndexOutOfBoundsException unless v is between 0 and V-1 */
    public int rank(int v) {
        validateVertex(v);
        if (hasOrder()) return rank[v];
        else            return -1;
    }

    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = rank.length;
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /* Unit tests the Topological data type.  */
    public static void main(String[] args) {
        String filename  = args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Topological topological = new Topological(sg.G());
        for (int v : topological.order()) {
            StdOut.println(sg.name(v));
        }
    }

    public static void start(final Digraph _digraph) {
        Topological topological = new Topological(_digraph);
        for (int v : topological.order()) {
            StdOut.print(v + " ");
        }
    }
}