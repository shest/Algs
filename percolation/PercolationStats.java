
/**
 *  The <tt>PercolationStats</tt> class perform a series of
 *  computational experiments.
 *  This class based on Percolate class.
 */
public class PercolationStats {
    /**
     * sample mean of percolation threshold.
     */
    private double mean;
    /**
     * sample standard deviation of percolation threshold.
     */
    private double stddev;
    /**
     * low  endpoint of 95% confidence interval.
     */
    private double confidenceLo;
    /**
     * high endpoint of 95% confidence interval.
     */
    private double confidenceHi;
    /**
     * Perform T independent experiments on an N-by-N grid.
     * @throws java.lang.IllegalArgumentException if N < 1 or T < 1
     * @param N the size of grid side
     * @param T the number of experiments
     */
    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        double[] siteVacancyProbability = new double[T];
        for (int k = 0; k < T; k++) {
            Percolation p = new Percolation(N);
            int cnt = 0;
            while (!p.percolates()) {
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    cnt++;
                }
            }
            siteVacancyProbability[k] = (double) cnt / (N * N);
        }
        mean = StdStats.mean(siteVacancyProbability);
        stddev = StdStats.stddev(siteVacancyProbability);
        final double lim = 1.96;
        confidenceLo = mean - lim * stddev / Math.sqrt(T);
        confidenceHi = mean + lim * stddev / Math.sqrt(T);
    }
    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return mean;
    }
    /**
     * @return sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return stddev;
    }

    /**
     * @return low endpoint of 95% confidence interval.
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /**
     * @return high endpoint of 95% confidence interval.
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    /**
     * performs T independent computational experiments
     *  on an N-by-N grid, and prints out the mean, standard deviation,
     *  and the 95% confidence interval for the percolation threshold.
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats pS = new PercolationStats(n, t);
        StdOut.println("mean = " + pS.mean());
        StdOut.println("stddev = " + pS.stddev());
        StdOut.println("95% confidence interval = " + pS.confidenceLo()
            + ", " + pS.confidenceHi());
    }
}
