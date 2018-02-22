
/******************************************************************************
 *  Author:       Jules Martel (julesmartel@gmail.com)
 *  Date:         Nov. 2017
 *
 *  This program estimates the percolation threshold of a size N grid.
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int gridSize;
    private boolean[] isOpen;
    private boolean[] isFull;
    private WeightedQuickUnionUF wquf;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be > 0");
        }
        this.N = N;
        gridSize = N * N;
        isOpen = new boolean[gridSize + 2];
        isFull = new boolean[gridSize + 2];
        wquf = new WeightedQuickUnionUF(gridSize + 1);
        isOpen[0] = true;
        isFull[0] = true;
    }

    private int xyTo1D(int i, int j) {
        if (i == 0) {
            return 0;
        } else {
            validate(i, j);
            return (j + N * (i - 1));
        }
    }

    private void validate(int i, int j) {
        if (i <= 0 || i > N || j <= 0 || j > N) {
            throw new java.lang.IndexOutOfBoundsException("indices (" + i + ","
                    + j + ") are not between 1 and " + (N));
        }
    }

    public void open(int i, int j) {
        validate(i, j);
        // if site not already open
        if (!isOpen(i, j)) {
            int site = xyTo1D(i, j);
            isOpen[site] = true;

            // we fill in the site that are on the first line
            if (i == 1) {
                isFull[site] = true;
            }
            // and we connect it with its neighbors
            connectAdjacentSites(i, j);
        }
    }

    private void connectAdjacentSites(int i, int j) {
        int site = xyTo1D(i, j);
        int top = xyTo1D(i - 1, j);
        if (isOpen[top]) {
            wquf.union(top, site);
        }
        if (j > 1 && j <= N) {
            int left = xyTo1D(i, j - 1);
            if (isOpen[left]) {
                wquf.union(left, site);
            }
        }
        if (j >= 1 && j < N) {
            int right = xyTo1D(i, j + 1);
            if (isOpen[right]) {
                wquf.union(right, site);
            }
        }
        if (i != N) {
            int bottom = xyTo1D(i + 1, j);
            if (isOpen[bottom]) {
                wquf.union(bottom, site);
            }
        }
    }


    public boolean isOpen(int i, int j) {
        validate(i, j);
        int site = xyTo1D(i, j);
        return isOpen[site];
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        int site = xyTo1D(i, j);
        return wquf.connected(0, site);
    }

    public boolean percolates() {
        return wquf.connected(0, N + 2);
    }
}
