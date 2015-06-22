package chord;
import java.util.List;
import java.util.ArrayList;


public class Node implements Runnable{
    private int id ;
    private String ip;	
	private String port;
	private List<Node> successors = new ArrayList<Node>();
	
	int sourcekey[]= new int[4];
	
	private List<Integer> source = new ArrayList<Integer>();
	Fingerline[] fingerline = new Fingerline[Fingerline.m];
	
	public void node(int id)
	{
		this.setId(id);
	}
	
	
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return this.id;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public String getIp()
	{
		return this.ip;
	}
	public void setPort(String port)
	{
		this.port = port;
	}
	public String getPort()
	{
		return this.port;
	}
	public void setSuccessorsList(Node node)
	{
		this.successors.add(node);
	}
	public Node getSuccessorsList(int index)
	{
		return this.successors.get(index);
	}
    public int getSizeSuccessorsList()
    {
	    return this.successors.size(); 
    }
	public void upload(int source)
	{
		this.source.add(source);
	}
	public int getSource(int index)
	{
		return this.source.get(index);
	}
	public int findSuccessor(int id)
    {
      int n1 = findPredecessor(id);
      return (int)Server.node[n1].fingerline[0].getSuccessor();
    }
    public int findPredecessor(int id)
    {
    	int n1= this.getId();
        //System.out.println("the successor of n1 :"+Server.node[n1].fingerline[0].getSuccessor()+n1+id);
    	//if(Server.node[n1].fingerline[0].getSuccessor()<Server.node[n1].getId())
    	double compare = 0;
    	compare = Server.node[n1].getId() - Server.node[n1].fingerline[0].getSuccessor();
    	while((compare < 0 && (id>Server.node[n1].fingerline[0].getSuccessor()|| id<=Server.node[n1].getId()))|| (compare>0 && id>Server.node[n1].fingerline[0].getSuccessor()&& id<=Server.node[n1].getId()))
    	{
    	    n1 = closestPrecedingFinger(Server.node[n1],id);
    	    compare = Server.node[n1].getId() - Server.node[n1].fingerline[0].getSuccessor();
        	}   
    	return n1;
    }
    public int closestPrecedingFinger(Node node,int id)
    {
    	   int i=0;
    	   int n=0;
    	   System.out.println("id of the node:"+node.getId()+"\n"+"id look for:"+id);
    	   if(node.getId()==id)
    	   {
    		   n=node.getId();
    	   }
    	   else{
    		   if(node.getId()<id)
    			{
    			   i = Fingerline.m-1;
    			   
    			   for(;i>=0;i--)
    	    	    {
  
    	    	     if(node.getId()< node.fingerline[i].getSuccessor() && id> node.fingerline[i].getSuccessor())
    	    	    	 {
    	    	    	 n = (int)node.fingerline[i].getSuccessor();
    	    	    	 System.out.println(n);
    	    	         break;
    	    	    	 }
    	    	     else n= node.getId();
    	    	      }
    			  }
    		   else if(node.getId()>id){
    			   i=Fingerline.m-1;
    			   System.out.println(node.fingerline[i].getSuccessor()+"\n"+node.getId());
    			   for(;i>=0;i--)
    			   {
    				   
    				   if(node.fingerline[i].getSuccessor()>node.getId() || node.fingerline[i].getSuccessor()<id)
    				   {
    					   n = (int)node.fingerline[i].getSuccessor();
    				       break;
    				   }
    				   else n=node.getId();
    			   }
    		   }
    			}
    	   System.out.println(n);
           return n;
    }
    
    public void run()
    {
    	this.upload(id);
    	
    }
}
