import java.awt.List;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import javax.sound.sampled.Line;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;
import javax.xml.transform.Templates;

public class TestSolver {
	int problemID;
	int[][] problem;
	int size;
	long starttime, endtime, timetaken,minlength;
	int count = 0;
	int[] arr;
	int[] tourorder;
	ArrayList<Integer> tour = new ArrayList<Integer>();
	ArrayList<Integer> to = new ArrayList<Integer>();

	TestSolver(int[][] problem, int problemID) {
		this.problemID = problemID;
		this.problem = problem;
		this.size = problem.length;
		tourorder = new int[size];
		for (int i = 0; i < size; i++) {
			tourorder[i] = i;        //Inserting values into array
			this.problem[i][i] = Integer.MAX_VALUE;
			to.add(tourorder[i]);   //Inserting values into list array
		}
	}

	void calculatetime() {
		starttime = System.currentTimeMillis();
		solve();
		endtime = System.currentTimeMillis();
		timetaken = endtime - starttime; // calculating time
	}

	void solve() {
		PriorityQueue<TreeNode> PQ = new PriorityQueue<TreeNode>(new Comparator<TreeNode>() {
			public int compare(TreeNode n0, TreeNode n1) {
				return n0.bound.compareTo(n1.bound);
			}
		});    //Initialization of Priority Queue with Comparator
		int minTourCost = Integer.MAX_VALUE;
		TreeNode v = new TreeNode();
		v.level = 0;
		v.id = tourorder[0];	
		v.bound = bound();
		v.path.add(v.id);
		PQ.add(v);
		count++;
		
		
		while (!PQ.isEmpty()) {
			TreeNode temp = PQ.poll();
			if (temp.level == size - 2) {
				for (int k = 1; k < size; k++) {
					int id = tourorder[k];
					if (!temp.path.contains(id)) {
						temp.path.add(id);
						temp.path.add(tourorder[0]);
					}
				}
				if (temp.bound < minTourCost) {
					minTourCost = temp.bound;
					tour = temp.path;
				}
			
			}
			
			for (int i = 1; i < size; i++) {
				int id = tourorder[i];
				if (!temp.path.contains(id)) {
					int x = temp.id;
					int y = id;
					int lowerbound = problem[x][y] + temp.costSoFar;
					int bound = bound(temp.path, x, y, lowerbound);
					if (bound < minTourCost) {
						TreeNode u = new TreeNode();
						u.level = temp.level + 1;
						u.id = id;
						u.path.addAll(temp.path);
						u.path.add(id);
						u.bound = bound;
						u.costSoFar = lowerbound;
						PQ.add(u);
						count++;
					}
				}
			}
			minlength = minTourCost;
		}
	}

	private Integer bound() {   					 //Caluate Initial Bound
		int lowerbound = 0;
		for (int j = 0; j < size; j++) {
			int min = Integer.MAX_VALUE;
			for (int k = 0; k < size; k++) {
				min = Math.min(min, problem[j][k]);
			}
			lowerbound = lowerbound + min;
		}
		return lowerbound;
	}

	private int bound(ArrayList<Integer> path, int x, int y, int lowerbound) {       //Calculate Bound for each element to be inserted in priority queue
		for (int i = 0; i < size; i++) {
			int s = tourorder[i];
			if (path.contains(s)) {
				continue;
			}
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < size; j++) {
				if (i == j) {
					continue;
				} else if (y == s && path.contains(j)) {
					continue;
				} else {
					if (j == 0 || (!path.contains(j) && (y != j))) {
						min = Math.min(min, problem[i][j]);
					}
				}
			}
			lowerbound = lowerbound + min;
		}
		return lowerbound;
	}

	public String toString() {
		String stats = problemID + " " + size + " " + minlength + " " + count
				+ " " + timetaken + System.getProperty("line.separator");
		for (Integer i : tour) {
			stats = stats + i + System.getProperty("line.separator");
		}
		return stats;
	}

}