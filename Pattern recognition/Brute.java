import java.util.Arrays;

public class Brute {
	private static void printLine(Point[] p) {
		StdOut.print(p[0]);
		for (int i = 1; i < p.length; i++) {
			StdOut.print(" -> " + p[i]);
		}
		StdOut.println();
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
                
        //brute algorithm
        for (int i = 0; i < n - 3; i++) {
        	for (int j = i + 1; j < n - 2; j++) {
        		for (int k = j + 1; k < n - 1; k++) {
        			for (int m = k + 1; m < n; m++) {
        				if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
        				if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[m])) continue;
        				Point[] arr = {points[i], points[j], points[k], points[m]};
        				Arrays.sort(arr);
        				printLine(arr);
        				arr[0].drawTo(arr[3]);
        			}
        		}
        	}
        }
	}
}
