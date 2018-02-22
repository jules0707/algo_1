/** ====================================================================================
http://coursera.cs.princeton.edu/algs4/assignments/queues.html
Deques and Randomized Queues
100/100
Hide grader output
See the Assessment Guide for information on how to interpret this report.
 
ASSESSMENT SUMMARY
 
Compilation:  PASSED (0 errors, 3 warnings)
API:          PASSED
 
Findbugs:     PASSED
PMD:          PASSED
Checkstyle:   FAILED (0 errors, 2 warnings)
 
Correctness:  43/43 tests passed
Memory:       105/105 tests passed
Timing:       136/136 tests passed
 
Aggregate score: 100.00%
[Compilation: 5%, API: 5%, Findbugs: 0%, PMD: 0%, Checkstyle: 0%, Correctness: 60%, Memory: 10%, Timing: 20%]
========================================================================================
**/
 
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
 
/*
 * requirements are "constant amortized time " for non-iterator operations. And linear for iterator
 * (repeated doubling and 1/4 full shrinking provides constant amortized when adding / popping  items)
 */
 
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q; // a generic array of Items
    private int N; // number of items in the array
 
    // constructor of an empty randomized queue
    public RandomizedQueue() {
        // "necessary ugly cast" of java arrays that are not generic
        q = (Item[]) new Object[1]; // initial array capacity is set to 1
        N = 0; // initial number of items in the array is zero.
    }
 
    // is the array empty?
    public boolean isEmpty() {
        return N == 0;
    }
 
    // return the number of items
    public int size() {
        return N;
    }
 
    // add an item in amortized constant time
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("item is null");
        if (N == q.length) // the array has reached its maximum
                            // capacity
            resize(2 * q.length);
        q[N] = item;
        N++;
    }
 
    // remove and return a random item in constant time!
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("empty queue");
        int r = StdRandom.uniform(N);
        Item item = q[r];
        q[r] = q[N - 1]; // we fill up the deleted item with the last one of the
                            // queue thanks sparkoo Michal Vala for the idea!
        // and reduce the queue by one item
        q[--N] = null; // for loitering
        // resizing of array if necessary when 1/4 empty
        if (N > 0 && N == q.length / 4)
            resize(q.length / 2);
        return item;
    }
 
    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("empty queue");
        return q[StdRandom.uniform(N)];
    }
 
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }
 
    private class RQIterator implements Iterator<Item> {
        private int i = N;
        // a shuffled copy of q
        private final Item[] sq = shuffleCopy();
 
        // we make a ahuffled copy of q item by item
        private Item[] shuffleCopy() {
            Item[] shuffledCopy = (Item[]) new Object[N];
            int index = 0;
            if (q != null) {
                StdRandom.shuffle(q, 0, N);
                for (int j = 0; j < q.length; j++) {
                    if (q[j] != null) {
                        // and only copy when there is a non null value
                        shuffledCopy[index] = q[j];
                        index++;
                    }
                }
            }
            return shuffledCopy;
        }
 
        public boolean hasNext() {
            return i > 0;
        }
 
        public void remove() {
            throw new java.lang.UnsupportedOperationException("not supported");
        }
 
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No more items");
            return sq[--i];
        }
    }
 
    private void resize(int capacity) {
        // a new queue to store the resized queue.
        // necessary ugly cast java arrays not generic
        Item[] resizedQ = (Item[]) new Object[capacity];
        // we use two indices to remove the nulls during the copy process
        int j = 0;
        for (int index = 0; index < q.length; index++) {
            if (q[index] != null) {
                // and only copy when there is a value
                resizedQ[j] = q[index];
                j++;
            }
        }
        q = resizedQ;
    }
}