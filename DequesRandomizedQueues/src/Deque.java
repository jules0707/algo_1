
import java.util.Iterator;
 
/* http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * choice to use Linked list as requirement is "constant worst case time" for non iterator operations
 * and arrays are "linear at worst case" for push and pop (resizing arrays via doubling and one quarter full shrinking)
 */
 
public class Deque<Item> implements Iterable<Item> {
 
    private Node first, last;
    private int n;
 
    private class Node {
        private Item item;
        private Node next; // Useful to remove and add first
        private Node prev; // Useful to remove and add last
    }
 
    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }
 
    public boolean isEmpty() {
        return first == null;
    }
 
    public int size() {
        return n;
    }
 
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("item is null");
 
        Node oldFirst = first; // can be null!
        first = new Node(); // construct the new node that will be added
        first.item = item;
        first.prev = null; // always null as it is the first
 
        if (n == 0) {
            first.next = last; // last is null here
        } else if (n == 1) {
            last = oldFirst;
            first.next = last;
            last.prev = first;
        } else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        n++;
    }
 
    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("item is null");
        if (n == 0) {
            addFirst(item);
        } else {
            Node oldLast = last; // can be null!
            last = new Node();
            last.item = item;
            last.next = null; // by principle
 
            if (n == 1) {
                last.prev = first; // the only possibility
                first.next = last;
            } else {
                last.prev = oldLast;
                oldLast.next = last;
            }
            n++;
        }
    }
 
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("deque is empty");
        Node oldFirst = first;
        Item item = first.item;
 
        if (n == 1) {
            first = null; // then the deck gets emptied
            last = null;
        } else {
            first = oldFirst.next;
            if (n == 2) { // last becomes first
                last = null;
                first.next = null;
                first.prev = null;
            }
            first.prev = null;
            first.next = oldFirst.next.next;
        }
 
        n--;
        return item;
    }
 
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("deque is empty");
        Item item;
        Node oldLast = last; // can be null !
 
        if (n == 1) {
            item = first.item;
            first = null; // we empty the deck
            last = null; // to be sure
        } else {
            item = oldLast.item;
            if (n == 2) { // only two items in the deck last becomes null
                last = null;
                first.next = null;
            } else { // otherwise
                last = oldLast.prev;
                last.next = null;
            }
        }
        n--;
        return item;
    }
 
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
 
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
 
        public boolean hasNext() {
            return current != null;
        }
 
        public void remove() {
            throw new java.lang.UnsupportedOperationException("not supported");
        }
 
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No more items");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}