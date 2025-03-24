import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import student.TestableRandom;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author Harleen Kaur
 * 
 * @version 2024-01-22
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element (Sentinel Node)
    private int size; // number of entries in the Skip List
    private Random rng;

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
        this.rng = new TestableRandom();
    }


    /**
     * returns a random level (using geometric distribution), minimum of 1
     * 
     * @return int the randomly generated level
     */
    public int randomLevel() {
        int level = 1;
        while (rng.nextBoolean())
            level++;
        return level;
    }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * @param key
     *            key to be searched for
     * @return ArrayList of KVPairs that match the search key
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        ArrayList<KVPair<K, V>> output = new ArrayList<>();
        SkipNode curr = head;

        // Level traversal to find first matching element
        for (int level = curr.level; level >= 0; level--) {
            while (curr.forward[level] != null && curr.forward[level].element()
                .getKey().compareTo(key) < 0) {
                curr = curr.forward[level];
            }
        }

        // Move to first potential matching node
        curr = curr.forward[0];

        // Collect only exactly matching elements
        while (curr != null && curr.element().getKey().compareTo(key) == 0) {
            output.add(curr.element());
            curr = curr.forward[0];
        }

        return output;
    }


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     * 
     * @param it
     *            the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        K key = it.getKey();
        int randLevel = randomLevel();
        if (head.level < randLevel) {
            adjustHead(randLevel);
        }
        SkipNode node = head;
        SkipNode[] recordNode = (SkipNode[])Array.newInstance(SkipNode.class,
            head.level + 1);
        for (int i = head.level; i >= 0; i--) {
            while (node.forward[i] != null && node.forward[i].element().getKey()
                .compareTo(key) < 0) {
                node = node.forward[i];
            }
            recordNode[i] = node;

        }
        SkipNode newNode = new SkipNode(it, randLevel);
// System.out.println("Random Level: "+randLevel);
        for (int j = 0; j <= randLevel; j++) {
            newNode.forward[j] = recordNode[j].forward[j];
            recordNode[j].forward[j] = newNode;
// System.out.println("Random Level: "+j+"New node:
// "+newNode.element().getKey());
// System.out.println(newNode.toString());
        }
        size++;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    @SuppressWarnings("unchecked")
    public void adjustHead(int newLevel) {
        SkipNode oldHead = head;
        head = new SkipNode(null, newLevel);
        for (int i = 0; i <= oldHead.level; i++) {
            head.forward[i] = oldHead.forward[i];
        }

    }


    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     * 
     * @param key
     *            of the the KVPair to be removed
     * @return remove the removed pair if the pair was valid and null if not
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        if (key == null)
            return null;

        SkipNode[] updateNodes = (SkipNode[])Array.newInstance(SkipNode.class,
            head.level + 1);
        SkipNode curr = head;

        for (int level = head.level; level >= 0; level--) {
            while (curr.forward[level] != null && curr.forward[level].element()
                .getKey().compareTo(key) < 0) {
                curr = curr.forward[level];
            }
            updateNodes[level] = curr;
        }

        SkipNode removeNode = curr.forward[0];
        if (size == 0 || removeNode == null || !removeNode.element().getKey()
            .equals(key)) {
            return null;
        }
        for (int level = 0; level <= head.level; level++) {
            if (updateNodes[level].forward[level] != removeNode)
                break;
            updateNodes[level].forward[level] = removeNode.forward[level];

        }
        while (head.level > 0 && head.forward[head.level] == null) {
            head.level--;
        }

        size--;
        return removeNode.element();
    }


    /**
     * Removes a KVPair with the specified value.
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns true if the removal was successful
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, V> removeByValue(V val) {
        if (val == null)
            return null;

        SkipNode[] updateNodes = (SkipNode[])Array.newInstance(SkipNode.class,
            head.level + 1);

        // Initialize update array with head
        for (int i = 0; i <= head.level; i++) {
            updateNodes[i] = head;
        }

        // Search from the top level down
        SkipNode curr = head;
        SkipNode removeNode = null;

        // First find the node to remove at each level
        for (int level = head.level; level >= 0; level--) {
            while (curr.forward[level] != null) {
                V nodeValue = curr.forward[level].element().getValue();
                if (nodeValue != null && nodeValue.equals(val)) {
                    removeNode = curr.forward[level];
                    break;
                }
                if (curr.forward[level].forward[level] != null
                    && curr.forward[level].forward[level].element().getValue()
                        .equals(val)) {
                    curr = curr.forward[level];
                    removeNode = curr.forward[level];
                    break;
                }
                curr = curr.forward[level];
            }
            updateNodes[level] = curr;
            curr = updateNodes[0]; // Reset to leftmost position for next level
        }

        if (removeNode == null) {
            return null;
        }

        // Remove the node by updating all forward pointers
        for (int level = 0; level <= head.level; level++) {
            curr = updateNodes[level];
            if (curr.forward[level] != null && curr.forward[level].element()
                .getValue().equals(val)) {
                curr.forward[level] = curr.forward[level].forward[level];
            }
        }

        // Update head level if necessary
        while (head.level > 0 && head.forward[head.level] == null) {
            head.level--;
        }

        size--;
        return removeNode.element();

    }


    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
        SkipNode left = head;
        int headDepth = (left.forward.length == 1)
            ? 1
            : (left.forward.length - 1);
        System.out.println("SkipList dump:");
        System.out.println("Node with depth " + headDepth + ", value null");

        left = left.forward[0];
        while (left != null) {
            System.out.println("node with depth " + (left.forward.length - 1)
                + " value " + left.element().getKey() + " " + left.element()
                    .getValue());
            left = left.forward[0];
        }
        System.out.println("SkipList size is: " + size);
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // An array of pointers to subsequent nodes
        private SkipNode[] forward;
        // the level of the node
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }


    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;

        public SkipListIterator() {
            current = head;
        }


        @Override
        public boolean hasNext() {
            return current.forward[0] != null;
        }


        @Override
        public KVPair<K, V> next() {
            KVPair<K, V> elem = current.forward[0].element();
            current = current.forward[0];
            return elem;
        }

    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new SkipListIterator();
    }

}
