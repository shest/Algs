import java.util.Arrays;

public class Fast {
	private class Line implements Comparable<Line> {
		private Point[] points;
		private int size;
		
		public Line(Point[] a) {
			this.points = a;
			size = a.length;
		}
		
		public String toString() {
			String s = points[0].toString();
			for (int i = 1; i < size; i++) {
				s += " -> " + points[i].toString();
			}
			return s;
		}
		
		public  int compareTo(Line that) {
			for (int i = 0; i < size; i++) {
				if (this.points[i].compareTo(that.points[i]) > 0) return +1;
		    	if (this.points[i].compareTo(that.points[i]) < 0) return -1;
			}
	    	return 0;
	    }
	}
	
	private static Line[] resize(Line[] stack, int max) {
		assert stack.length >= max;
		
		Line[] newStack = new Line[max];
		for (int i = 0; i < stack.length; i++) {
			newStack[i] = stack[i];
		}
		return newStack;
	}
	
	private static Line[] shrink(Line[] stack, int size) {
		Line[] newStack = new Line[size];
		for (int i = 0; i < size; i++) {
			newStack[i] = stack[i];
		}
		return newStack;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        //reads all points from file
        for (int i = 0; i < n; i++) {
        	int x = in.readInt();
        	int y = in.readInt();
        	points[i] = new Point(x, y);
        	points[i].draw();
        }
        
        Point[] original = new Point[n];
        for (int i = 0; i < n; i++) {
        	original[i] = points[i];
        }

        Fast main = new Fast();
        
        //create array of lines
        Line[] lines = new Line[2];
        int size = 0;
        for (int i = 0; i < n; i++) {
        	Point p = original[i];
        	Arrays.sort(points);
        	Arrays.sort(points, p.SLOPE_ORDER);
        	for (int j = 1; j < n - 1; j++) {
        		int cnt = 1;
        		int next = j;
        		if (p.slopeTo(points[j]) == p.slopeTo(points[j+1])) {
        			cnt++;
        			j++;
        			while (j < n - 1 && p.slopeTo(points[j]) == p.slopeTo(points[j+1])) {
        				cnt++;
        				j++;
        			}
        			//sort points and create line		
    				if (cnt >= 3 && p.compareTo(points[next]) < 0) {
    					Point[] arr = new Point[cnt+1];
    					arr[0] = p;
    					for (int k = 1; k <= cnt; k++) {
    						arr[k] = points[next + k - 1];
    					}
    					Line line = main.new Line(arr);
    					if (size == lines.length) {lines = resize(lines, 2*size); }
    					lines[size++] = line;
    					p.drawTo(arr[cnt]);
        			}
        		}
        	}
        }
        
        //sort array of lines
        if (size == 0) return;
        if (size == 1) {
        	StdOut.println(lines[0]);
        } else {
            lines = Fast.shrink(lines, size);
        	Arrays.sort(lines);
            //print out lines
            for (int i = 0; i < lines.length; i++) {
        	    StdOut.println(lines[i]);
            }
        }
	}
}
