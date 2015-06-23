package chord;


import java.util.SortedSet;
import java.util.TreeSet;

public final class ActiveNode {
    
	private SortedSet<Node> activenode = new TreeSet<Node>();  
    
	public void addActiveNode(Node node)
	{
		this.activenode.add(node);
	}
   
	public  static boolean isActiveNode(int index)
	{
		return this.activenode.contains(index);
	}
	
}
