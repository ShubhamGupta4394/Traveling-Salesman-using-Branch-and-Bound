import java.util.ArrayList;


public class TreeNode {
      int level;
      int id;
      ArrayList<Integer> path = new ArrayList<Integer>();
      Integer bound = Integer.MAX_VALUE;
      Integer costSoFar = 0;
      
	
	public String toString() {
		return "Node [id=" + id + ", level=" + level + ", path=" + path + ", bound=" + bound + ", costSoFar="
				+ costSoFar + "]";
	}

}
