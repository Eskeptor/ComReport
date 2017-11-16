package ex6;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/* Class BTreeTest
 * Description: This class contains all the test cases for BTree */
public class BTreeTest {
    private final BTree<Integer, String> mBTree;
    private final Map<Integer, String> mMap;
    private BTTestIteratorImpl<Integer, String> mIter;


    public BTreeTest() {
        mBTree = new BTree<Integer, String>();
        mMap = new TreeMap<Integer, String>();
        mIter = new BTTestIteratorImpl<Integer, String>();
    }

    public BTree<Integer, String> getBTree() {
        return mBTree;
    }

    public BTNode<Integer, String> getRootNode() {
        return mBTree.getRootNode();
    }

    protected void add(Integer key, String value) {
        mMap.put(key, value);
        mBTree.insert(key, value);
    }

    protected void delete(Integer key) throws BTException {
        System.out.println("Delete key = " + key);
        String strVal1 = mMap.remove(key);
        String strVal2 = mBTree.delete(key);
        if (!isEqual(strVal1, strVal2)) {
            throw new BTException("Deleted key = " + key + " has different values: " + strVal1 + " | " + strVal2);
        }
    }

    private void clearData() {
        mBTree.clear();
        mMap.clear();
    }

    public void listItems(BTIteratorIF<Integer, String> iterImpl) {
        mBTree.list(iterImpl);
    }

    private boolean isEqual(String strVal1, String strVal2) {
        if ((strVal1 == null) && (strVal2 == null)) {
            return true;
        }

        if ((strVal1 == null) && (strVal2 != null)) {
            return false;
        }

        if ((strVal1 != null) && (strVal2 == null)) {
            return false;
        }

        if (!strVal1.equals(strVal2)) {
            return false;
        }

        return true;
    }


    public void validateSize() throws BTException {
        System.out.println("Validate size ...");
        if (mMap.size() != mBTree.size()) {
            throw new BTException("Error in validateSize(): Failed to compare the size:  " + mMap.size() + " <> " + mBTree.size());
        }
    }


    public void validateSearch(Integer key) throws BTException {
        System.out.println("Validate search for key = " + key + " ...");
        String strVal1 = mMap.get(key);
        String strVal2 = mBTree.search(key);

        if (!isEqual(strVal1, strVal2)) {
            throw new BTException("Error in validateSearch(): Failed to compare value for key = " + key);
        }
    }


    public void validateData() throws BTException {
        System.out.println("Validate data ...");

        for (Map.Entry<Integer, String> entry : mMap.entrySet()) {
            try {
                // System.out.println("Search key = " + entry.getKey());
                String strVal = mBTree.search(entry.getKey());
                if (!isEqual(entry.getValue(), strVal)) {
                    throw new BTException("Error in validateData(): Failed to compare value for key = " + entry.getKey());
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new BTException("Runtime Error in validateData(): Failed to compare value for key = " + entry.getKey() + " msg = " + ex.getMessage());
            }
        }
    }


    public void validateOrder() throws BTException {
        System.out.println("Validate the order of the keys ...");
        mIter.reset();
        mBTree.list(mIter);
        if (!mIter.getStatus()) {
            throw new BTException("Error in validateData(): Failed to compare value for key = " + mIter.getCurrentKey());
        }
    }


    public void validateAll() throws BTException {
        validateData();
        validateSize();
        validateOrder();
    }


    public void addKey(int i) {
        add(i, "value-" + i);
    }


    public void validateTestCase0() throws BTException {
        System.out.println();
        System.out.println("+ Running TestCase0 validation");

        add(1, "key1");
        add(2, "key2");
        add(3, "key3");
        add(4, "key4");
        add(5, "key5");
        add(6, "key6");
        add(7, "key7");
        add(8, "key8");
        add(9, "key9");
        add(10, "key10");
        add(13, "key13");
        add(15, "key15");
        add(17, "key17");
        add(19, "key19");
        add(20, "key20");
        add(23, "key23");
        add(25, "key25");
        add(27, "key27");
        add(28, "key28");
        add(30, "key30");
        add(31, "key31");
        add(33, "key33");
        add(34, "key34");
        add(35, "key35");
        add(36, "key36");
        add(37, "key37");
        add(38, "key38");
        add(39, "key39");
        add(40, "key40");
        add(41, "key41");
        add(45, "key45");

        validateAll();
        System.out.println("Completed");
    }


    /* Main Entry for the test
     * param : args      */
    public static void main(String []args) {
        BTreeTest test = new BTreeTest();

        try {
            test.validateTestCase0();
            System.out.println("BTree validation is successfully complete.");
        }
        catch (BTException btex) {
            System.out.println("BTException msg = " + btex.getMessage());
            btex.printStackTrace();
        }
        catch (Exception ex) {
            System.out.println("BTException msg = " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    //
    // Randomly generate integer within the specified range
    //
    public static int randInt(int min, int max) {
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


    /* Inner class to implement BTree iterator  */
    class BTTestIteratorImpl<K extends Comparable, V> implements BTIteratorIF<K, V> {
        private K mCurrentKey;
        private K mPreviousKey;
        private boolean mStatus;


        public BTTestIteratorImpl() {
            reset();
        }

        @Override
        public boolean item(K key, V value) {
            mCurrentKey = key;
            if ((mPreviousKey != null) && (mPreviousKey.compareTo(key) > 0)) {
                mStatus = false;
                return false;
            }
            mPreviousKey = key;
            return true;
        }

        public boolean getStatus() {
            return mStatus;
        }

        public K getCurrentKey() {
            return mCurrentKey;
        }

        public final void reset() {
            mPreviousKey = null;
            mStatus = true;
        }
    }
}
