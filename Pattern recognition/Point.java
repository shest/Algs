import java.util.Arrays;
import java.util.Comparator;


public class Point implements Comparable<Point> {
   
	// compare points by slope to this point
	public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
	
	private final int x;
	private final int y;
	
	// construct the point (x, y)
    public Point(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    // draw this point
    public   void draw() {
    	StdDraw.point(x, y);
    }
    
    // draw the line segment from this point to that point
    public   void drawTo(Point that) {
    	StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    // string representation
    public String toString() {
    	return "(" + x + ", " + y + ")";
    }

    // is this point lexicographically smaller than that point?
    public    int compareTo(Point that) {
    	if (this.y > that.y) return +1;
    	if (this.y < that.y) return -1;
    	if (this.x > that.x) return +1;
    	if (this.x < that.x) return -1;
    	return 0;
    }
    
    // the slope between this point and that point
    public double slopeTo(Point that) {
    	if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
    	if (this.x == that.x) return Double.POSITIVE_INFINITY;
    	if (this.y == that.y)return +0.0;
    	double res = (that.y - this.y) / (double) (that.x - this.x);
    	return res;
    }
    
    private class SlopeOrder implements Comparator<Point> {
    	public int compare(Point q, Point p) {
    		double slope1 = slopeTo(q);
    		double slope2 = slopeTo(p);
    		if (slope1 < slope2) return -1;
    		if (slope1 > slope2) return +1;
    		return 0;
    	}
    }
    
    public static void main(String[] args) {
        int x0 = Integer.parseInt(args[0]);
        int y0 = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[2]);

        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(.005);
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point(x, y);
            points[i].draw();
        }

        // draw p = (x0, x1) in red
        Point p = new Point(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.02);
        p.draw();


        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.SLOPE_ORDER);
        for (int i = 0; i < N; i++) {
            p.drawTo(points[i]);
            StdOut.println();
            StdDraw.show(100);
        }
    }
}
