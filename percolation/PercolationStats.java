import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

	private final Percolation[] test;
	private final int sz;

	public PercolationStats (int n, int trials) {

		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException("n and trials must be greater than 0");

		sz = n;
		test = new Percolation[trials];

		for (int i = 0; i < trials; i++) {

			test[i] = new Percolation(n);

			while (!test[i].percolates())
				test[i].open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
			
		}


	}

	public double mean() {

		double openSites = 0;

		for (int i = 0; i < test.length; i++) {

			openSites += (test[i].numberOfOpenSites() / (double) (sz * sz));

		}

		return openSites / (double) test.length;

	}

	public double stddev() {

		double m = mean();
		double v = 0;

		for (int i = 0; i < test.length; i++)
			v += Math.pow((test[i].numberOfOpenSites() / (double) (sz * sz)) - m, 2);

		return Math.sqrt(v / (double) (test.length - 1));

	}

	
	public double confidenceLo() {

		double m = mean();
		double s = stddev();

		return m - ((1.96 * s) / (double) test.length);

	}

	public double confidenceHi() {

		double m = mean();
		double s = stddev();

		return m + ((1.96 * s) / (double) test.length);

	}

	public static void main(String[] args) {

		if (args.length < 2)
			throw new IllegalArgumentException("Usage: java PercolationStats trials n-size");

		PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		StdOut.println("mean: " + test.mean());
		StdOut.println("std: " + test.stddev());
		StdOut.println("95% confidence interval: [" + test.confidenceLo() + " , " + test.confidenceHi() + "]");

	}

}
