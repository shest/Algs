import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return size;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        itemValidate(item);
       	Node oldfirst = first;
        first = new Node();
        first.next = oldfirst;
        first.item = item;
        first.prev = null;
        if (isEmpty()) { last = first; }
        else { oldfirst.prev = first; }
        size++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        itemValidate(item);
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) { first = last; }
        else { oldlast.next = last;}
        size++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        emptyValidate();
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {last = null; }
        else    first.prev = null;
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        emptyValidate();
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) {first = null; }
        else    last.next = null;
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {return new ListIterator(); }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    private void itemValidate(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }
    
    private void emptyValidate() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }
    
    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(0);
        deque.isEmpty();
        deque.removeLast();//      ==> 0
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        StdOut.println(deque.removeLast());
    }
    
}
