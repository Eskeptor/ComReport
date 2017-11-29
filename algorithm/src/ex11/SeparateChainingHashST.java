package ex11;

/******************************************************************************
 *  Compilation:  javac SeparateChainingHashST.java
 *  Execution:    java SeparateChainingHashST
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A symbol table implemented with a separate-chaining hash table.
 * 
 ******************************************************************************/
/*  The SeparateChainingHashST class represents a symbol table of generic key-value pairs.
 *  It supports the usual put, get, contains, delete, size, and is-empty methods.
 *  It also provides a keys method for iterating over all of the keys.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike java.util.Map, this class uses the convention that values cannot be null;
 *  setting the value associated with a key to null is equivalent to deleting the key
 *  from the symbol table.
 *  
 *  This implementation uses a separate chaining hash table. It requires that the key type overrides 
 *  the equals() and hashCode() methods.
 *  The expected time per put, contains, or remove operation is constant, subject to the uniform 
 *  hashing assumption. The size, and is-empty operations take constant time.
 *  Construction takes constant time.
 *  For other implementations, see ST, BinarySearchST, SequentialSearchST, BST, RedBlackBST, 
 *  and LinearProbingHashST.
 */
public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    // largest prime <= 2^i for i = 3 to 31
    // not currently used for doubling and shrinking
    // private static final int[] PRIMES = {
    //    7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
    //    32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
    //    8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
    //    536870909, 1073741789, 2147483647
    // };

    private int N;                                // number of key-value pairs
    private int M;                                // hash table size
    private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables

    /* Initializes an empty symbol table.  */
    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    } 

    /* Initializes an empty symbol table with M chains.
     * param M : the initial number of chains */
    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST<Key, Value>();
    } 

    // resize the hash table to have the given number of chains b rehashing all of the keys
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.M  = temp.M;
        this.N  = temp.N;
        this.st = temp.st;
    }

    // hash value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    } 

    /* Returns the number of key-value pairs in this symbol table.
     * return the number of key-value pairs in this symbol table     */
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
     * throws NullPointerException if key is null     */
    public boolean contains(Key key) {
        return get(key) != null;
    } 

    /* Returns the value associated with the given key.
     * param key : the key
     * return the value associated with the given key if the key is in the symbol table
     * and null if the key is not in the symbol table
     * throws NullPointerException if key is null   */
    public Value get(Key key) {
        int i = hash(key);
        return st[i].get(key);
    } 

    /* Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is null, this effectively deletes the key from the symbol table.
     * param key : the key; param val : the value
     * throws NullPointerException if key is null   */
    public void put(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }

        // double table size if average length of list >= 10
        if (N >= 10*M) resize(2*M);

        int i = hash(key);
        if (!st[i].contains(key)) N++;
        st[i].put(key, val);
    } 

    /* Removes the key and associated value from the symbol table
     * (if the key is in the symbol table).
     * param key : the key
     * throws NullPointerException if key is null  */
    public void delete(Key key) {
        int i = hash(key);
        if (st[i].contains(key)) N--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (M > INIT_CAPACITY && N <= 2*M) resize(M/2);
    } 

    // return keys in symbol table as an Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    } 

    /* Unit tests the SeparateChainingHashST data type. */
    public static void main(String[] args) { 
        SeparateChainingHashST<Integer, Integer> st = new SeparateChainingHashST<>();

        st.put(0, 39);
        st.put(0, 31);
        st.put(1, 40);
        st.put(3, 94);
        st.put(3, 3);
        st.put(3, 42);
        st.put(3, 55);
        st.put(4, 43);
        st.put(5, 44);
        st.put(5, 70);
        st.put(8, 86);
        st.put(8, 47);
        st.put(9, 74);
        st.put(11, 76);

        // print keys
        for (Integer s : st.keys())
            StdOut.println(s + " " + st.get(s)); 

    }
}