public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private final int moves;
        private final Board board;
        private final int priority;
        private final SearchNode prev;

        public SearchNode(int moves, Board board, SearchNode prev) {
            this.moves = moves;
            this.board = board;
            this.prev = prev;
            this.priority = this.moves + this.board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }
    private boolean isSolvable;
    private MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    private SearchNode root;
    private SearchNode twin;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        root = new SearchNode(0, initial, null);
        twin = new SearchNode(0, initial.twin(), null);
        if (root.board.isGoal()) {
            isSolvable = true;
            return;
        }
        if (twin.board.isGoal()) {
            isSolvable = false;
            return;
        }
        pq.insert(root);
        pq.insert(twin);
        root = pq.delMin();
        while(!root.board.isGoal()) {
            for (Board b : root.board.neighbors()) {
                if (root.prev != null && b.equals(root.prev.board)) continue;
                SearchNode newSn = new SearchNode(root.moves + 1, b, root);
                pq.insert(newSn);
            }
            root = pq.delMin();
        }
        isSolvable = true;
        SearchNode last = root;
        while (last.prev != null) {
            last = last.prev;
        }
        if (last.board.equals(twin.board)) isSolvable = false;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable) return root.moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<Board>();
        SearchNode current = root;
        stack.push(current.board);
        while (current.prev != null) {
            current = current.prev;
            stack.push(current.board);
        }
        return stack;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Stopwatch s = new Stopwatch();

        Solver solver = new Solver(initial);
        StdOut.println(s.elapsedTime());

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

