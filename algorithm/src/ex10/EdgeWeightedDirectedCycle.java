package ex10;

/******************************************************************************
 *  Compilation:  javac EdgeWeightedDirectedCycle.java
 *  Execution:    java EdgeWeightedDirectedCycle V E F
 *  Dependencies: EdgeWeightedDigraph.java DirectedEdge.java Stack.java
 *
 *  Finds a directed cycle in an edge-weighted digraph.
 *  Runs in O(E + V) time.
 *
 *
 ******************************************************************************/
/*  The EdgeWeightedDirectedCycle class represents a data type for determining whether 
 *  an edge-weighted digraph has a directed cycle. The hasCycle operation determines whether 
 *  the edge-weighted digraph has a directed cycle and, if so, the cycle operation returns one.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the hasCycle operation takes constant time;
 *  the cycle operation takes time proportional to the length of the cycle.
 *  See Topological to compute a topological order if the edge-weighted digraph is acyclic. */
public class EdgeWeightedDirectedCycle {
    private boolean[] marked;             // marked[v] = has vertex v been marked?
    private DirectedEdge[] edgeTo;        // edgeTo[v] = previous edge on path to v
    private boolean[] onStack;            // onStack[v] = is vertex on the stack?
    private Stack<DirectedEdge> cycle;    // directed cycle (or null if no such cycle)

    /* Determines whether the edge-weighted digraph <tt>G</tt> has a directed cycle and,
     * if so, finds such a cycle. Param G the edge-weighted digraph  */
    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph G) {
        marked  = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo  = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);

        // check that digraph has a cycle
        assert check(G);
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();

            // short circuit if directed cycle found
            if (cycle != null) return;

            //found new vertex, so recur
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<DirectedEdge>();
                while (e.from() != w) {
                    cycle.push(e);
                    e = edgeTo[e.from()];
                }
                cycle.push(e);
                return;
            }
        }

        onStack[v] = false;
    }

    /**
     * Does the edge-weighted digraph have a directed cycle?
     * @return <tt>true</tt> if the edge-weighted digraph has a directed cycle,
     * <tt>false</tt> otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the edge-weighted digraph has a directed cycle,
     * and <tt>null</tt> otherwise.
     * @return a directed cycle (as an iterable) if the edge-weighted digraph
     *    has a directed cycle, and <tt>null</tt> otherwise
     */
    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }


    // certify that digraph is either acyclic or has a directed cycle
    private boolean check(EdgeWeightedDigraph G) {

        // edge-weighted digraph is cyclic
        if (hasCycle()) {
            // verify cycle
            DirectedEdge first = null, last = null;
            for (DirectedEdge e : cycle()) {
                if (first == null) first = e;
                if (last != null) {
                    if (last.to() != e.from()) {
                        System.err.printf("cycle edges %s and %s not incident\n", last, e);
                        return false;
                    }
                }
                last = e;
            }

            if (last.to() != first.from()) {
                System.err.printf("cycle edges %s and %s not incident\n", last, first);
                return false;
            }
        }


        return true;
    }

    /**
     * Unit tests the <tt>EdgeWeightedDirectedCycle</tt> data type.
     */
    public static void main(String[] args) {

        // create random DAG with V vertices and E edges; then add F random edges
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        int F = Integer.parseInt(args[2]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < E; i++) {
            int v, w;
            do {
                v = StdRandom.uniform(V);
                w = StdRandom.uniform(V);
            } while (v >= w);
            double weight = StdRandom.uniform();
            G.addEdge(new DirectedEdge(v, w, weight));
        }

        // add F extra edges
        for (int i = 0; i < F; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = StdRandom.uniform(0.0, 1.0);
            G.addEdge(new DirectedEdge(v, w, weight));
        }

        StdOut.println(G);

        // find a directed cycle
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Cycle: ");
            for (DirectedEdge e : finder.cycle()) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }

        // or give topologial sort
        else {
            StdOut.println("No directed cycle");
        }
    }

}