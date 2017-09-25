package ex5;

import java.util.*;


// default implementation of binary tree with integer key
// and observable implementation
public class BinaryTree  extends Observable {
    protected int          key;
    protected BinaryTree   left;
    protected BinaryTree   right;
    protected BinaryTree   parent;
    private   boolean      modified;

    public BinaryTree() {
        key      = 0;
        left     = null;
        right    = null;
        parent   = null;
        modified = true;
    }

    public BinaryTree(int x) {
        key      = x;
        left     = null;
        right    = null;
        modified = true;
    }

    public BinaryTree(int x,BinaryTree p) {
        key      = x;
        left     = null;
        right    = null;
        parent   = p;
        modified = true;
    }

    public BinaryTree newTree(int x,BinaryTree p) {
        BinaryTree t;

        t = new BinaryTree(x,p);
        return t;
    }

    public BinaryTree newTree(BinaryTree t) {
        return  new BinaryTree(t);
    }

    public BinaryTree(Observer a,int x) {
        key     = x;
        left    = null;
        right   = null;
        addObserver(a);
        setChanged();
        notifyObservers("new BinaryTree");
    }

    public BinaryTree(BinaryTree x) {
        key      = x.key;
        left     = x.left;
        right    = x.right;
        parent   = x.parent;
        modified = true;
    }

    public BinaryTree(BinaryTree x,BinaryTree p) {
        key      = x.key;
        left     = x.left;
        right    = x.right;
        parent   = p;
        modified = true;
    }

    protected void copy(BinaryTree x) {
        key      = x.key;
        left     = x.left;
        right    = x.right;
        parent   = x.parent;
        modified = x.modified;
    }

    public BinaryTree getParent() {
        return parent;
    }

    public void setParent(BinaryTree p) {
        parent = p;
    }

    public int getKey() {
        return key;
    }

    public void setModified() {
        setModifiedR();
    }

    public boolean getModified() {
        boolean b = modified;
        modified = false;
        return b;
    }

    public BinaryTree insert(int x,BinaryTree root) {
        root.setChanged();
        root.notifyObservers("inserting:" + Integer.toString(x));
        return insertR(x,root);
    }

    public void inorder(int level,BinaryTree parent,BinaryTreeVisitor v)
    {
        inorderR(level,parent,v);
    }

    public void balance(BinaryTree root) {
        balanceR(root);

        // last update
        root.setChanged();
        root.notifyObservers(null);
    }

    public BinaryTree rotLeft() {
        BinaryTree x;  BinaryTree me;
        if (right == null) return null;
        me        = newTree(this);
        x         = me.right;    me.right  = x.left;   x.left    = me;
        x.parent  = me.parent;    me.parent = x;
        return   x;
    }

    public BinaryTree rotRight() {
        BinaryTree x;   BinaryTree me;

        if (left == null) return null;
        me        = newTree(this);
        x         = me.left;  me.left   = x.right;  x.right   = me;
        x.parent  = me.parent;   me.parent = x;
        return x;
    }

    protected boolean isModifiedR() {
        boolean b = false;
        // preorder traversal
        if (left != null) {
            b |= left.isModifiedR();
        }
        if (right != null) {
            b |= right.isModifiedR();
        }
        return modified;
    }

    // ==================================================================
    // ==================================================================


    private void inorderR(int level,BinaryTree parent,BinaryTreeVisitor v) {
        if (left != null) {
            left.inorderR(level+1,this,v);
        }

        if (v != null) {
            v.visit(this,parent,level);
        }

        if (right != null) {
            right.inorderR(level+1,this,v);
        }
    }

    // partition around a depth of K
    private void partR(int k,BinaryTree root) {
        int  t;
        BinaryTree x;
        String s = "Partitioned Subtree";

        // if the left tree is null
        if (left == null) {
            t = 0;
        }
        else {
            // get number of elements under the left tree
            // need to recompute count each time as tree changes
            t = left.countR();
        }

        // if most nodes are on the left side
        if (t > k ) {
            // if there is a left subtree
            if (left != null) {
                // partition it
                left.partR(k,root);
                // single rotation
                x = rotRight();
                if (x != null) {
                    // copy returned node to 'this'
                    key   = x.key;
                    left  = x.left;
                    right = x.right;
                    setModifiedR();
                    s += " : Rotated Right at : " + Integer.toString(key);
                }
            }
        }

        // if depth of left tree is less than partition center then most nodes are on the right side
        if (t < k ) {
            // if there is a right subtree
            if (right != null) {
                // partition it
                right.partR(k-t-1,root);
                // single rotation
                x = rotLeft();
                if (x != null) {
                    // copy returned node to 'this'
                    key   = x.key;
                    left  = x.left;
                    right = x.right;
                    setModifiedR();
                    s += " : Rotated Left at : " + Integer.toString(key);
                }
            }
        }

        // allow observers to update
        if (isModifiedR() == true) {
            root.setChanged();
            root.notifyObservers(s);
        }
    }

    private void balanceR(BinaryTree root) {
        int count;
        count = countR();
        if (count <= 1) return;
        // partition self
        partR(count/2,root);
        // balance left subtree
        if (left != null) {
            left.balanceR(root);
        }
        // balance right subtree
        if (right != null) {
            right.balanceR(root);
        }
        // allow observers to update
        if (isModifiedR() == true) {
            root.setChanged();
            root.notifyObservers("Rebalancing");
        }
    }

    private void update(BinaryTree root) {
        root.setChanged();
        root.notifyObservers("Update");
    }

    private int countR()
    {
        int count;
        // preorder traversal
        count = 1;
        if (left != null) {
            count += left.countR();
        }
        if (right != null) {
            count += right.countR();
        }

        return count;
    }

    private void setModifiedR() {
        // preorder traversal
        modified = true;

        if (left != null) {
            left.setModifiedR();
        }
        if (right != null) {
            right.setModifiedR();
        }
    }

    private BinaryTree insertR(int x,BinaryTree root) {
        BinaryTree b = null;

        // notify of traversal
        root.setChanged();
        root.notifyObservers(new Integer(getKey()));

        // less than
        if (x < key) {
            if (left == null) {
                b = left = newTree(x,this);
                left.setModified();
                root.setChanged();
                root.notifyObservers("inserted left:" + Integer.toString(x));
            }
            else {
                b = left.insertR(x,root);
            }
        }
        // greater than
        else if (x > key) {
            if (right == null) {
                b = right = newTree(x,this);
                right.setModified();
                root.setChanged();
                root.notifyObservers("inserted right:" + Integer.toString(x));
            }
            else {
                b = right.insertR(x,root);
            }
        }
        // equal, do nothing, no duplicates allowed
        else {
        }

        return b;
    }

}


