package chord;


import java.util.SortedSet;
import java.util.TreeSet;

public final class ActiveNode {
    
	public static SortedSet<Node> activenode = new TreeSet<Node>();  
    
	public static void addActiveNode(Node node)
	{
		try{
			activenode.add(node);
		}catch(Exception e)
		{
		System.out.println(e.toString());
		}
	}
   
	public static boolean isActiveNode(int index)
	{
		return activenode.contains(index);
	}
	
}
