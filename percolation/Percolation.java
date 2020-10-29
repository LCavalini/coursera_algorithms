import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private boolean[] grid;
	private final int sz;
	private final WeightedQuickUnionUF unionFind;
	private int openSites;

	public Percolation(int n) {

		if (n <= 0) throw new IllegalArgumentException("n must be greater than 0");

		openSites = 0;
		sz = n;
		grid = new boolean[sz*sz+1];
		
		// id[0] := virtual top, id[sz*sz+2] := virtual bottom
		unionFind = new WeightedQuickUnionUF(sz*sz+2);

		for (int i = 1; i <= sz*sz; i++)
			grid[i] = false;
		

	}

	private int xyTo1D(int row, int col) {

		return (row - 1) * sz + col;

	}

	private boolean isValidIndex(int row, int col) {

		return (row >= 1 && row <= sz && col >= 1 && col <= sz);
  
	}

	public void open(int row, int col) {

		if (!isValidIndex(row, col))
			throw new IllegalArgumentException("row and col must be equal or greater than 1 and lesser than n");

		if (!isOpen(row, col)) {

			grid[xyTo1D(row, col)] = true;
			openSites++;

			// virtual top
			if (row == 1) unionFind.union(xyTo1D(row, col), 0);
			// virtual bottom
			else if (row == sz) unionFind.union(xyTo1D(row, col), sz*sz+1);

			// left
			if (isValidIndex(row, col - 1) && isOpen(row, col - 1))
				unionFind.union(xyTo1D(row, col), xyTo1D(row, col - 1));
			
			// right
			if (isValidIndex(row, col + 1) && isOpen(row, col + 1))
				unionFind.union(xyTo1D(row, col), xyTo1D(row, col + 1));
			
			// top
			if (isValidIndex(row - 1, col) && isOpen(row - 1, col))
				unionFind.union(xyTo1D(row, col), xyTo1D(row - 1, col));
			
			// bottom
			if (isValidIndex(row + 1, col) && isOpen(row + 1, col))
				unionFind.union(xyTo1D(row, col), xyTo1D(row + 1, col));

		}
		
	}

	public boolean isOpen(int row, int col) {

		if (!isValidIndex(row, col))
			throw new IllegalArgumentException("row and col must be equal or greater than 1 and lesser than n");

		return grid[xyTo1D(row, col)];

	}

	public boolean isFull(int row, int col) {

		if (!isValidIndex(row, col))
			throw new IllegalArgumentException("row and col must be equal or greater than 1 and lesser than n");

		if (unionFind.find(xyTo1D(row, col)) == unionFind.find(0)) return true;

		return false; 
	
	}

	public int numberOfOpenSites() {

		return openSites;

	}

	public boolean percolates() {

		if (unionFind.find(0) == unionFind.find(sz*sz+1)) return true;

		return false;

	}

	public static void main(String[] args) {

		Percolation test = new Percolation(3);

		test.open(1, 1);
		test.open(2, 2);

		StdOut.println("(1, 1) is Full? " + test.isFull(1, 1));
		StdOut.println("(2, 2) is Full? " + test.isFull(2, 2));
		StdOut.println("Percolates? " + test.percolates());

		test.open(3, 2);
		StdOut.println("(3, 2) is Full? " + test.isFull(3, 2));
		StdOut.println("(2, 2) is Full? " + test.isFull(2, 2));
		StdOut.println("Percolates? " + test.percolates());
		
		test.open(2, 1);
		StdOut.println("(3, 2) is Full? " + test.isFull(3, 2));
		StdOut.println("Percolates? " + test.percolates());
	
	}

}
