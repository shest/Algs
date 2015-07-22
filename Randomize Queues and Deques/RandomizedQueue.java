import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int size;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2]; 
        size = 0;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return size;
    }
    
    private void resize(int max) {
    	Item[] newQ = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
        	newQ[i] = q[i];
        }
        q = newQ;
    }
    
    // add the item
    public void enqueue(Item item) {
    	validateItem(item);
        if (size == q.length) { resize(2*q.length); }
        q[size] = item;
        size++;
    }
    
    private void validateItem(Item item) {
    	if (item == null) throw new NullPointerException();
    }
    
    // remove and return a random item
    public Item dequeue() {
    	validateEmptyness();
        int rid = StdRandom.uniform(size);
        Item item = q[rid];
        size--;
        if (!isEmpty()) {
        	q[rid] = q[size];
            q[size] = null;
        } else q[rid] = null;
        if (size > 0 && size == q.length/4) {resize(q.length/2); }
        return item;
    }
    
    private void validateEmptyness() {
    	if (isEmpty()) throw new NoSuchElementException();
    }
    
    // return (but do not remove) a random item
    public Item sample() {
    	validateEmptyness();
    	int rid = StdRandom.uniform(size);
        Item item = q[rid];
        return item;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        int[] id = new int[size];
    	createRandomId(id);
    	return new RandomListIterator(id);
    }
    
    private void createRandomId(int[] id) {
		for (int i = 0; i < size; i++) {
    		id[i] = i;
    	}
		StdRandom.shuffle(id);
	}
    
    private class RandomListIterator implements Iterator<Item> {
    	private int i = 0;
    	private int[] id;
    	public boolean hasNext() {return i < size; }
    	public void remove()      { throw new UnsupportedOperationException();  }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[id[i]];
            i++;
            return item;
        }
        
        private RandomListIterator(int[] ind) {
    		id = ind;
    	}
    }
    
    // unit testing
    public static void main(String[] args) {
    	RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
    	 rq.enqueue(2);
         StdOut.println(rq.dequeue());
    	 rq.enqueue(1);
    	 rq.enqueue(2);
    	 rq.enqueue(3);
    	 rq.enqueue(4);
    	 for (int i : rq) {
    		 StdOut.print(i + ": ");
    		 for (int j : rq) {
    			 StdOut.print(j + " ");
    		 }
    		 StdOut.println();
    	 }
    }
}