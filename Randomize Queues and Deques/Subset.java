public class Subset {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		int n = 0;
		if (k > 0) {
			RandomizedQueue<String> rq = new RandomizedQueue<String>();
			while(!StdIn.isEmpty()) {
				String s = StdIn.readString();
				n++;
				if (n <= k) {
					rq.enqueue(s);
				} else {
					int rm = StdRandom.uniform(n);
					if (rm < k) {
						rq.dequeue();
						rq.enqueue(s);
					}
				}
			}
			for (int i = 0; i < k; i++) {
				StdOut.println(rq.dequeue());
			}
		}
	}

}
