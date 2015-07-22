
/**
 *  The <tt>Percolation</tt> class models a percolation system.
 *  It supports the <em>open</em> operation for opening site
 *  of percolation system.
 *  This class based on weighted quick union by size (without path compression).
 *  Initializing a data structure with <em>N</em> objects takes linear time.
 */
public class Percolation {
    private static final byte BLOCKED = 0;
    private static final byte OPENED = 1;
    private static final byte FULL = 2;
    private static final byte CONNECT_TO_BOTTOM = 3;
    private static final byte PERCULATES = 4;
    
    private WeightedQuickUnionUF grid;
    private int n;
    private byte[] siteState; 
    private boolean percolates;
    
    /**
     * Create N-by-N grid, with all sites blocked.
     * @throws java.lang.IllegalArgumentException if N < 1
     * @param N the size of grid side
     */
    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException();
        }
        n = N;
        grid = new WeightedQuickUnionUF(N * N + 1);
        siteState = new byte[N * N + 1];
        siteState[0] = FULL;
    }
    
    /**
     * Open site if it is not open already.
     * @param i is row of a grid
     * @param j is column of a grid
     */
    public void open(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        
        int id = mapIndeces(i, j);
        if (siteState[id] > 0) return;
        siteState[id] = OPENED;

        if (n == 1){
        	grid.union(id, 0);
        	percolates = true;
        	return;
        }
        
        if (i == n) {
        	siteState[id] = CONNECT_TO_BOTTOM;
        }
        if (i == 1) {
        	union(id, 0);
        } else if (isOpen(i - 1, j)) {
        	union(id, id - n);
        }
        
       
        if (i < n && isOpen(i + 1, j)) {
        	union(id, id + n);
        }
        
        if (j > 1 && isOpen(i, j - 1)) {
        	union(id, id - 1);
        }
        
        if (j < n && isOpen(i, j + 1)) {
        	union(id, id + 1);
        }
    }
    
    /**
     * Is site (row i, column j) open?
     * @param i is row of a grid
     * @param j is column of a grid
     * @return <tt>true</tt> if the site is open
     * and <tt>false</tt> otherwise
     * @throws java.lang.IndexOutOfBoundsException unless both
     *  0 <= i < N and 0 <= j < N
     */
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        return siteState[mapIndeces(i, j)] != BLOCKED;
    }
    /**
     * Is site (row i, column j) full?
     * @param i is row of a grid
     * @param j is column of a grid
     * @return <tt>true</tt> if the site is full
     * and <tt>false</tt> otherwise
     * @throws java.lang.IndexOutOfBoundsException unless both
     * 0 <= i < N and 0 <= j < N
     */
    public boolean isFull(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        int id = mapIndeces(i, j);
        return grid.connected(id, 0);
    }
    /**
     * Does the system percolate?
     * @return <tt>true</tt> if the system percolates
     * and <tt>false</tt> otherwise
     */
    public boolean percolates() {
        return percolates;
    }
    
    private int mapIndeces(int i, int j) {
    	return (i - 1) * n + j;
    }
    
    private void union(int p, int q) {
    	byte pState = siteState[grid.find(p)];
    	byte qState = siteState[grid.find(q)];
    	byte mergeState = merge(pState, qState);
    	grid.union(p, q);
    	int root = grid.find(p);
    	siteState[root] = mergeState;
    	if (!percolates) {
    		percolates = mergeState == PERCULATES;
    	}
    }
    
    private byte merge(byte pS, byte qS) {
    	if (pS + qS == 5) {
    		return PERCULATES;
    	}
    	byte max = pS;
    	if (qS > max) {
    		max = qS;
    	}
    	return max;
    }
    
    /**
     * Performs T independent computational experiments (discussed above)
     * on an N-by-N grid, and prints out the mean, standard deviation,
     * and the 95% confidence interval for the percolation threshold.
     */
    public static void main(String[] args) {
      /*Percolation p = new Percolation(5);
        p.open(1, 2);
        p.open(4, 5);
        p.open(5, 2);
        p.open(4, 4);
        p.open(3, 4);
        p.open(2, 4);
        p.open(2, 2);
        p.open(2, 3);
        p.open(5, 5);
        if (p.percolates()) StdOut.println("yes");
        else StdOut.println("no");*/
    }
    
   
}
