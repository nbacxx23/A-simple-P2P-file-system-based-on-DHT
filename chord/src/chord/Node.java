package chord;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;


public class Node implements Runnable{
    private Integer id ;
    private String ip;	
	private String port;
	public boolean active;
	private Node predecessor;
	private List<Node> successors = new ArrayList<Node>();
	private List<String> commands = new ArrayList<String>(); 
	private SortedSet<Double> sourcekey = new TreeSet<Double>();
    private HashMap<String,String> hashpredecessor = new HashMap<String,String>();

    
	private List<Double> source = new ArrayList<Double>();
	Fingerline[] fingerline = new Fingerline[Fingerline.m];
	
	public void node(int id)
	{
		this.setId(id);
	}
	
	public void setPredecessor(Node node)
	{
		this.predecessor = node;
	}
	
	public Node getPredecessor()
	{
		return this.predecessor;
	}
	
	public void setSourcekey(double source) throws Exception
	{
		this.sourcekey.add(source);
	}
	
	public void setCommand(String command)
	{
		this.commands.add(command);
	}
	
	public String getCommand(int index)
	{
		return this.commands.get(index);
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	public Integer getId()
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
	public void upload(double source)
	{
		this.source.add(source);
	}
	public double getSource(int index)
	{
		return this.source.get(index);
	}
	public Node findSuccessor(int id)
    {
      int n1 = findPredecessor(id);
      return Server.node[(int)Server.node[n1].fingerline[0].getSuccessor().getId()];
    }
    public int findPredecessor(int id)
    {
    	int n1= this.getId();
        //System.out.println("the successor of n1 :"+Server.node[n1].fingerline[0].getSuccessor()+n1+id);
    	//if(Server.node[n1].fingerline[0].getSuccessor()<Server.node[n1].getId())
    	double compare = 0;
    	compare = Server.node[n1].getId() - Server.node[n1].fingerline[0].getSuccessor().getId();
    	while((compare < 0 && (id>Server.node[n1].fingerline[0].getSuccessor().getId()|| id<=Server.node[n1].getId()))|| (compare>0 && id>Server.node[n1].fingerline[0].getSuccessor().getId()&& id<=Server.node[n1].getId()))
    	{
    	    n1 = closestPrecedingFinger(Server.node[n1],id);
    	    compare = Server.node[n1].getId() - Server.node[n1].fingerline[0].getSuccessor().getId();
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
  
    	    	     if(node.getId()< node.fingerline[i].getSuccessor().getId() && id> node.fingerline[i].getSuccessor().getId())
    	    	    	 {
    	    	    	 n = (int)node.fingerline[i].getSuccessor().getId();
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
    				   
    				   if(node.fingerline[i].getSuccessor().getId()>node.getId() || node.fingerline[i].getSuccessor().getId()<id)
    				   {
    					   n = (int)node.fingerline[i].getSuccessor().getId();
    				       break;
    				   }
    				   else n=node.getId();
    			   }
    		   }
    			}
    	  // System.out.println(n);
           return n;
    }
    
    public void join(Node node)
    {
    	if(ActiveNode.isActiveNode(node.getId())==true)
    	{
    		initFingerTable(node);
    		updateOthers();
    	}
    	else 
    	{
    		int i;
    		for(i=0;i<Fingerline.m;i++)
    		{
    			this.fingerline[i].setSuccessor(node);
    		}
    		this.setPredecessor(node);
    	}
    	
    }
    
    public boolean leave(Node node)
    {
    	
    	return true;
    }
    
    public void initFingerTable(Node node)
    {
      int i;
      for(i=0;i<0;i++)         // init start|end|interval
      {   
    	  this.fingerline[i]= new Fingerline();
    	  this.fingerline[i].setStart(this.id, i);
    	  this.fingerline[i].setEnd(this.id, i);
    	  this.fingerline[i].setInterval(this.fingerline[i].getStart(), this.fingerline[i].getEnd(), i);
      }
      this.setSuccessorsList(node.findSuccessor((int)node.fingerline[0].getStart()));
      this.fingerline[0].setSuccessor(Server.node[this.getSuccessorsList(0).getId()]);
      this.setPredecessor(this.getSuccessorsList(0).getPredecessor());
      this.getSuccessorsList(0).setPredecessor(node);
      for(i=0;i<Fingerline.m-1;i++)
      {
    	  double compare = 0;
    	  compare = this.getId() - this.fingerline[i].getSuccessor().getId();
    	  if((compare < 0 && (this.fingerline[i+1].getStart()>=this.getId() && this.fingerline[i+1].getStart()<this.fingerline[i].getSuccessor().getId()))|| (compare>0 && ( this.fingerline[i+1].getStart()>= this.getId() || this.fingerline[i+1].getStart()<this.fingerline[0].getSuccessor().getId())))
    	  {
    		  this.fingerline[i+1].setSuccessor(this.fingerline[i].getSuccessor());
    	  }      
    	  else {
    		  this.fingerline[i+1].setSuccessor(node.findSuccessor((int)this.fingerline[i].getStart()));  
    	  }
      }
    }
    
    public void updateOthers()
    {
    	int i;
    	Node p;
    	for(i=0;i<Fingerline.m;i++)
    	{
    		p = Server.node[this.findPredecessor((int)(this.getId()-Math.pow(2, i-1)))];
    		p.updateFingerTable(p,this,i);
    	}
    }
    public void updateFingerTable(Node p,Node node,int i) // pas encore fini
    {
      double compare = 0;
  	  compare = this.getId() - this.fingerline[i].getSuccessor().getId();
  	  if((compare < 0 && (this.fingerline[i+1].getStart()>=this.getId() && this.fingerline[i+1].getStart()<this.fingerline[i].getSuccessor().getId()))|| (compare>0 && ( this.fingerline[i+1].getStart()>= this.getId() || this.fingerline[i+1].getStart()<this.fingerline[0].getSuccessor().getId())))
  	  {
  		  this.fingerline[i].setSuccessor(node);
  		  p.setPredecessor(this.getPredecessor());
  		  p.updateFingerTable(p, node, i);
  	  }
    }
    
    public void run()
    {
    	
    }
}
