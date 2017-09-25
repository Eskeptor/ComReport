package ex5;

import java.awt.*;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class RedBlackTreeCanvas extends Canvas implements Observer {
    private int         MaxRows;
    private int         MaxCols;
    private NodeLabel   TreeGrid[][];
    private RedBlackTree TreeX;
    private Vector TreeData;
    private long        StepTimeout;
    private Stepper     Stpr;
    private Builder     BuildThread;
    private Vector      ActiveNodes;
    private FinishBuild Finished;
    private boolean     Busy;
    private boolean     LastUpdate;
    private String      Progress;

    public RedBlackTreeCanvas() {
        NodeLabel x;
        Dimension d;

        // set up
        Progress    = "";  Busy        = false;   TreeData    = new Vector(64);
        MaxRows    = 20;  MaxCols    = 20;  TreeGrid    = null;
        Stpr        = new Stepper();   BuildThread = null;

        // set the background color
        setBackground(Color.lightGray);
        // rows are indexed by level
        // columns are incremented for each visit
        TreeGrid = new NodeLabel[MaxRows][MaxCols];
        d = getSize();
        for(int row=0;row < MaxRows;row++) {
            for(int col=0;col < MaxCols;col++) {
                x = TreeGrid[row][col] = new NodeLabel("");
                x.setForeground(Color.white);  x.setBackground(Color.red);
                x.setVisible(false);  x.setRowCol(row,col);
            }
        }
        setData(null);
    }

    // connecting from node to parent
    public void connectLabels(Graphics g,NodeLabel n,NodeLabel p,Color c) {
        Point     pa = n.getLocation();
        Dimension da = n.getSize();
        Point     pb = p.getLocation();
        Dimension db = p.getSize();
        pa.x += da.width  / 2;  pa.y += da.height / 2;  pb.x += db.width  / 2;  pb.y += db.height / 2;
        g.setColor(c);  g.drawLine(pa.x,pa.y,pb.x,pb.y);
    }

    // get a copy of the current data set
    public synchronized Vector getData() {
        Vector v = null;
        if (TreeData != null) {
            v = (Vector)TreeData.clone();
        }
        return v;
    }

    // set the current data set to a new set of values
    public synchronized void setData(Vector v) {
        // parse integer elements from the string and insert in the vector
        if (v != null) {
            TreeData = (Vector)v.clone();
        }
        else {
            TreeData = null;
        }
    }

    public void buildTree() {
        Vector v = getData();

        // get data vector and quit if it is empty
        Enumeration e = v.elements();
        if (e.hasMoreElements() == false) {
            return;
        }

        // insert all elements in the data vector into the tree
        TreeX = new RedBlackTree(this,((Integer)e.nextElement()).intValue());

        while (e.hasMoreElements()) {
            TreeX.insert(((Integer)e.nextElement()).intValue(),TreeX);
        }

        // balance the tree
        TreeX.balance(TreeX);
    }

    // set the step timeout
    public void setTimeout(long t) {
        StepTimeout = t;
    }

    // get the step timeout
    public long getTimeout() {
        return StepTimeout;
    }

    // no thread
    public void clrThread() {
        BuildThread = null;
    }

    public void setLastUpdate(boolean b) {
        LastUpdate = b;
    }

    public boolean getLastUpdate() {
        return LastUpdate;
    }

    // builder thread builds the tree data structure in the background
    // this allows other user interface updates while it is being built
    // rather than having to wait.
    class Builder extends Thread {
        public void run() {
            // build the tree
            buildTree();

            // when done, mark thread not used
            clrThread();

            // signal finished
            Finished.signalDone();
        }
    }

    class FinishBuild extends Observable {
        public void signalDone() {
            setChanged();
            notifyObservers();
        }
    }

    public synchronized void redraw(Observer o) {
        // set up an observable to signal finished
        Finished = new FinishBuild();
        Finished.addObserver(o);

        // run the builder thread
        if (BuildThread == null) {
            setTimeout(500L);
            BuildThread = new Builder();
            BuildThread.start();
        }
    }

    public synchronized void reset() {
        // run the builder thread
        if (BuildThread == null) {
            setTimeout(0);
            BuildThread = new Builder();
            BuildThread.start();
        }
    }

    public boolean step() {
        // signal
        Stpr.signalStep();
        return getLastUpdate();
    }

    class Stepper {
        boolean s;

        public Stepper() {
            s = false;
        }

        public synchronized void waitStep() {
            try {
                if (getTimeout() == 0) {
                    // wait until signaled
                    while(s == false) {
                        wait();
                    }
                    s = false;
                }
                else {
                    java.lang.Thread.sleep(getTimeout());
                }
            } catch (InterruptedException e) {
            }
        }

        // signal one step to whoever is waiting
        public synchronized void signalStep() {
            s = true;
            notifyAll();
        }
    }

    public void clearPanel() {
    }

    private void connectNodes(Graphics g,Vector v) {
        NodeLabel lt;
        NodeLabel lp;
        int       ParentKey;

        // if no vector, do nothing
        if (v   == null) return;

        // outer loop checks every node
        Enumeration e = v.elements();
        while(e.hasMoreElements()) {
            lt        = (NodeLabel)e.nextElement();
            ParentKey = lt.getParentKey();
            // inner loop matches node and parent
            Enumeration p = v.elements();
            while(p.hasMoreElements()) {
                lp = (NodeLabel)p.nextElement();
                // node and parent match, draw a connection
                if (lp.getKey() == ParentKey) {
                    connectLabels(g,lt,lp,lt.getBackground());
                }
            }
        }
    }

    public void paintLabels(Graphics g,Vector t) {
        Enumeration e;
        NodeLabel   lt;

        if (t == null) {
            return;
        }

        e = t.elements();
        while(e.hasMoreElements()) {
            lt = (NodeLabel)e.nextElement();
            lt.setText(Integer.toString(lt.getKey()));
            /* use the following to display characters instead of ints
            char[] a = {(char)lt.getKey()};
            lt.setText(new String(a));
            */
            lt.setVisible(true);
            lt.paint(g);
        }
    }

    public void setPosition(Vector t,Dimension d,int rows,int cols) {
        Enumeration e;
        NodeLabel   lt;

        if (t == null) {
            return;
        }

        e = t.elements();
        while(e.hasMoreElements()) {
            lt = (NodeLabel)e.nextElement();
            lt.setLocationAndSize(d,rows,cols);
        }
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        if (!Busy) {
            // draw current operation
            g.drawString(Progress,0,getSize().height - 10);
            // draw input vector
            g.drawString("Input Data : " + getData(),0,getSize().height - 25);
            setPosition(ActiveNodes,getSize(),MaxRows,MaxCols);
            connectNodes(g,ActiveNodes);
            paintLabels(g,ActiveNodes);
        }
    }

    // when the tree changes
    public void update(Observable o,Object arg) {
        RedBlackTree t     = (RedBlackTree)o;
        Graphics g = getGraphics();

        if (arg != null) {
            setLastUpdate(false);
            Progress = (String)arg;
        }
        else {
            // done
            setLastUpdate(true);
            Progress = "Finished";
        }

        // mark Busy, no repaints while Busy
        Busy = true;

        // get a new vector to store active nodes in
        ActiveNodes = new Vector(MaxCols);

        // traverse inorder and redraw
        // after this procedure is done, the active nodes are ready to draw
        t.inorder(0,t,new TreeLayout());

        // not Busy anymore
        Busy    = false;

        // force a repaint
        repaint();

        // wait for user to observe results
        Stpr.waitStep();
    }

    class TreeLayout implements BinaryTreeVisitor {
        int column;

        public TreeLayout() {
            column = 0;
        }

        public void reset() {
            column = 0;
        }

        public void visit(BinaryTree t,BinaryTree parent,int level) {
            NodeLabel lt;
            RedBlackTree rbt;

            rbt = (RedBlackTree)t;

            // draw the item and a line back to its parent
            if ((level < MaxRows)&&(column < MaxCols)) {
                lt = TreeGrid[level][column];
                lt.setKey(rbt.getKey());
                lt.setParentKey(parent.getKey());
                lt.setModified(rbt.getModified());
                if (rbt.getColor() == RedBlackTree.red) {
                    lt.setBackground(Color.red);
                }
                else {
                    lt.setBackground(Color.black);
                }

                // add to list of nodes to connect
                if (ActiveNodes == null) {
                    ActiveNodes = new Vector(MaxCols);
                }

                ActiveNodes.addElement(lt);
            }
            column++;
        }
    }
}
