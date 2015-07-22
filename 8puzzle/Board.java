import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private char[] blocks;
    private int N;
    private char emptyI;
    private char emptyJ;

    // construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();
        N = blocks.length;
        this.blocks = new char[N*N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] < 0 || blocks[i][j] >= N*N) throw new NoSuchElementException();
                this.blocks[i*N + j] = (char) blocks[i][j];
                if (blocks[i][j] == 0) {
                    emptyI = (char) i;
                    emptyJ = (char) j;
                }
            }
        }
    }

    private Board(char[] blocks, int emptyI, int emptyJ) {
        N = (int) Math.sqrt(blocks.length);
        this.blocks = new char[N*N];
        for (int i = 0; i < N*N; i++) {
            this.blocks[i] = blocks[i];
        }
        this.emptyI = (char) emptyI;
        this.emptyJ = (char) emptyJ;
    }

    // board dimension N
    public int dimension() { return N; }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j< N; j++) {
                if (blocks[i*N + j] == 0 || isRightBlock(i, j)) continue;
                hamming++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i*N + j] != 0) {
                    manhattan += manhattanForBlock(i, j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!isRightBlock(i, j)) return false;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board twin = new Board(blocks, emptyI, emptyJ);
        if (blocks[0] != 0 && blocks[1] != 0) {
            twin.exchBlocks(0,0,0,1);
        } else twin.exchBlocks(1,0,1,1);
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.N != that.N) return false;
        if (!Arrays.equals(this.blocks, that.blocks)) return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new BoardIterable();
    }

    private class BoardIterable implements Iterable<Board> {
       public Iterator<Board> iterator() {
            return new BoardIterator();
        }
    }

    private class BoardIterator implements Iterator<Board> {
        Queue<Board> q;

        public BoardIterator() {
            q = new Queue<Board>();
            if (emptyI != 0) {
                Board tmp = new Board(blocks, emptyI - 1, emptyJ);
                tmp.exchBlocks(emptyI, emptyJ, emptyI - 1, emptyJ);
                q.enqueue(tmp);
            }
            if (emptyI != N - 1) {
                Board tmp = new Board(blocks, emptyI + 1, emptyJ);
                tmp.exchBlocks(emptyI, emptyJ, emptyI + 1, emptyJ);
                q.enqueue(tmp);
            }
            if (emptyJ != 0) {
                Board tmp = new Board(blocks, emptyI, emptyJ - 1);
                tmp.exchBlocks(emptyI, emptyJ, emptyI, emptyJ - 1);
                q.enqueue(tmp);
            }
            if (emptyJ != N - 1) {
                Board tmp = new Board(blocks, emptyI, emptyJ + 1);
                tmp.exchBlocks(emptyI, emptyJ, emptyI, emptyJ + 1);
                q.enqueue(tmp);
            }
        }

        public boolean hasNext() {
            return !q.isEmpty();
        }

        public Board next() {
            if(this.hasNext()) {
                return q.dequeue();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", (int) blocks[i*N + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int manhattanForBlock(int i, int j) {
        return Math.abs(getGoalI(blocks[i*N + j]) - i) + Math.abs(getGoalJ(blocks[i*N + j]) - j);
    }

    private boolean isRightBlock(int i, int j) {
        if (i != getGoalI(blocks[i*N + j])) return false;
        if (j != getGoalJ(blocks[i*N + j])) return false;
        return true;
    }

    private void exchBlocks(int i1, int j1, int i2, int j2) {
        char tmp = blocks[i1*N + j1];
        blocks[i1*N + j1] = blocks[i2*N + j2];
        blocks[i2*N + j2] = tmp;
    }

    private int getGoalI(int value) {
        if (value == 0) {
            return N - 1;
        }
        return (value - 1) / N;
    }

    private int getGoalJ(int value) {
        if (value == 0) {
            return N - 1;
        }
        return (value - 1) % N;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        StdOut.println(board.toString());
        StdOut.println("N:" + board.dimension() + " hamming: " + board.hamming() + " manhattan: " + board.manhattan());
        for (Board b : board.neighbors()) {
            StdOut.println(b.toString());
        }
    }

}