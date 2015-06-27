package chord;


import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public final class ActiveNode {
    
	public static List<Node> activenode = new ArrayList<Node>();  
    
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
