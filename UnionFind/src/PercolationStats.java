
/******************************************************************************

 *  Author:       Jules Martel (julesmartel@gmail.com)
 *  Date:         Tuesday 9th Feb. 2016
 *  
 *  This program estimates the means Standard deviation and percolation threshold
 *  value of a size N grid.
 *  It reads 2 parameters from command line T is the number of experiment
 *  and N the grid Size
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n; // n x n grid
    private int T; // Times to repeat
    private double[] thresholds;
    private double mu;
    private double power2ofSigma;

    public PercolationStats(int n, int T) {
        this.n = n;
        if (n <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException("not valid indices");

        this.T = T;
        this.thresholds = new double[T];

        // runs T times the monteCarlo simulation
        for (int i = 0; i < T; i++) {
            thresholds[i] = monteCarlo(n);
        }

        for (double threshold : thresholds) {
            mu += threshold;
        }
        // the avg threshold
        mu = mu / T;

        for (int i = 1; i <= T; i++) {
            power2ofSigma += Math.pow((thresholds[i - 1] - mu), 2);
        }
        power2ofSigma += power2ofSigma / (T - 1);
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return (mu - (1.96 * this.stddev() / Math.sqrt(T)));
    }

    public double confidenceHi() {
        return (mu + (1.96 * this.stddev() / Math.sqrt(T)));
    }

    private double monteCarlo(int n) {
        Percolation percolation = new Percolation(n);
        int p;
        int q;
        int opened = 0;
        while (!percolation.percolates()) {
            p = StdRandom.uniform(1, n+1 );
            q = StdRandom.uniform(1, n+1 );
            if (!percolation.isOpen(p, q)) {
                percolation.open(p, q);
                opened++;
            }
        }
        return (opened / (n * n));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n, T);
        System.out.println("mean:                   = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo()
                + ", " + stats.confidenceHi() + " ]");
    }
}
