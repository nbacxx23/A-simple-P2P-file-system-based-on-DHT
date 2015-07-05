package chord;

public final class Server {
	
	final static Node[] node = new Node[8];
	
	public static void server()
	{
		int i;	   
		   for(i=0;i<8;i++)  //initial id of each node--not utill use hushfunction
		   {
			  node[i] = new Node();
			  node[i].setId(i);
		  }
	}
}
