package ex11;

/******************************************************************************
 *  Compilation:  javac LinearProbingHashST.java
 *  Execution:    java LinearProbingHashST
 *  Dependencies: StdIn.java StdOut.java
 *  
 *  Symbol table implementation with linear probing hash table.
 *
 *  % java LinearProbingHashST
 *  128.112.136.11
 *  208.216.181.15
 *  null
 ******************************************************************************/
/*
 *  The LinearProbingHashST class represents a symbol table of generic  key-value pairs.
 *  It supports the usual put, get, contains, delete, size, and is-empty methods.
 *  It also provides a keys method for iterating over all of the keys.
 *  A symbol table implements the associative array abstraction: when associating a value with a key 
 *  that is already in the symbol table, the convention is to replace the old value with the new value.
 *  Unlike java.util.Map, this class uses the convention that values cannot be null; setting the value 
 *  associated with a key to null is equivalent to deleting the key from the symbol table.
 *  
 *  This implementation uses a linear probing hash table. It requires that the key type overrides 
 *  the equals() and hashCode() methods. The expected time per put, contains, or remove operation 
 *  is constant, subject to the uniform hashing assumption. The size, and is-empty operations take 
 *  constant time. Construction takes constant time.
 *  For other implementations, ST, BinarySearchST, SequentialSearchST, BST, RedBlackBST, and
 *  SeparateChainingHashST.
 */
public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int N;           // number of key-value pairs in the symbol table
    private int M;           // size of linear probing table
    private Key[] keys;      // the keys
    public Value[] vals;    // the values

    /* Initializes an empty symbol table. */
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    /* Initializes an empty symbol table of given initial capacity.
     * param capacity : the initial capacity  */
    public LinearProbingHashST(int capacity) {
        M = capacity;
        keys = (Key[])   new Object[M];
        vals = (Value[]) new Object[M];
    }

    /* Returns the number of key-value pairs in this symbol table.
     * return the number of key-value pairs in this symbol table */
    public int size() {
        return N;
    }

    /* Is this symbol table empty?
     * return true if this symbol table is empty and false otherwise */
    public boolean isEmpty() {
        return size() == 0;
    }

    /* Does this symbol table contain the given key?
     * param key : the key
     * return true if this symbol table contains key and false otherwise
     * throws NullPointerException if key is null   */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    // resize the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        M    = temp.M;
    }

    /* Inserts the key-value pair into the symbol table, overwriting the old value with the new value 
     * if the key is already in the symbol table. If the value is null, this effectively deletes the key 
     * from the symbol table. Param key : the key; param val : the value
     * throws NullPointerException if key is null   */
    public void put(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (N >= M/2) resize(2*M);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    /* Returns the value associated with the given key.
     * param key : the key
     * return the value associated with the given key if the key is in the symbol table
     *     and null if the key is not in the symbol table
     * throws NullPointerException if key is null     */
    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) 
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    /* Removes the key and associated value from the symbol table
     * (if the key is in the symbol table).
     * param key : the key
     * throws NullPointerException if key is null  */
    public void delete(Key key) {
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % M;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;  
            put(keyToRehash, valToRehash);
            i = (i + 1) % M;
        }

        N--;        

        // halves size of array if it's 12.5% full or less
        if (N > 0 && N <= M/8) resize(M/2);

        assert check();
    }

    /* Returns all keys in the symbol table as an Iterable. To iterate over all of the keys 
     * in the symbol table named st, use the foreach notation: for (Key key : st.keys()).
     * return all keys in the sybol table as an Iterable  */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

    // integrity check - don't check after each put() because
    // integrity not maintained during a delete()
    private boolean check() {

        // check that hash table is at most 50% full
        if (M < 2*N) {
            System.err.println("Hash table size M = " + M + "; array size N = " + N);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < M; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }


    /* Unit tests the LinearProbingHashST data type. */
    public static void main(String[] args) {
        LinearProbingHashST<Integer, Integer> st = new LinearProbingHashST<>(12);
        st.put(0, 13);
        st.put(2, 15);
        st.put(3, 16);
        st.put(2, 28);
        st.put(7, 7);
        st.put(5, 31);
        st.put(7, 20);
        st.put(1, 1);
        st.put(12, 38);

        // print keys
        for (Integer s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}