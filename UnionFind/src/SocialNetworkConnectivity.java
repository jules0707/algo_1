import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionPathCompressionUF;

/*---------------- TASK -----------------
 Social network connectivity. Given a social network containing n members 
 and a log file containing m timestamps at which times pairs of members formed friendships,
 design an algorithm to determine the earliest time at which all members are connected 
 (i.e., every member is a friend of a friend of a friend ... of a friend). 
 Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
 The running time of your algorithm should be mlogn or better and use extra space proportional 
 to n.
 */

/* SAMPLE DATA
 * 10 components
 * 
 100000176 4 3
 100000177 3 8
 100000178 6 5
 100000179 9 4
 100000186 2 1
 100000196 8 9
 100000276 5 0
 100000286 7 2
 100000287 3 6
 100000296 6 1
 100000376 1 0
 100000476 6 7
 */

// simple straight forward implementation of the WeightedQuickUnionPathCompressionUF class

public class SocialNetworkConnectivity {
	private static int[] ts;

	public SocialNetworkConnectivity() {
		super();
	}

	public static void main(String[] args) {
		int n = StdIn.readInt();
		WeightedQuickUnionPathCompressionUF wqupc = new WeightedQuickUnionPathCompressionUF(
				n);
		ts = new int[n]; // we store the timestamps in a seperate array
		int t = 0;
		int p;
		int q;
		int index = -1;

		// as long as every one is not connected we connect more members
		while (wqupc.count() > 1) {
			t = StdIn.readInt();
			Arrays.fill(ts, t);
			p = StdIn.readInt();
			q = StdIn.readInt();
			wqupc.union(p, q);
			index++;
		}
		StdOut.println(" network connected at time: "
				+ Integer.toString(ts[index]));

	}

}
