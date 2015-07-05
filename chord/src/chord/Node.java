package chord;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.SortedSet;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Node implements Runnable,Comparable<Object> {
	private int id;
	private String ip;
	private String port;
	public boolean active;
	private Node predecessor;
	private Queue<Node> successors = new ConcurrentLinkedQueue<Node>();
	private List<String> commands = new ArrayList<String>();
	private SortedSet<String> sourcekey = new TreeSet<String>();
	private HashMap<String, String> hashpredecessor = new HashMap<String, String>();

	public int compareTo(Object o) {
		Node n = (Node) o;

		int result = id > n.id ? 1 : (id == n.id ? 0 : -1);

		return result;

	}

	private List<Double> source = new ArrayList<Double>();
	Fingerline[] fingerline = new Fingerline[Fingerline.m];

	public void node(int id) {
		this.setId(id);
	}

	public void setPredecessor(Node node) {
		this.predecessor = node;
	}

	public Node getPredecessor() {
		return this.predecessor;
	}

	public void setSourcekey(String sourcekey) throws Exception {
		this.sourcekey.add(sourcekey);
	}

	public void setCommand(String command) {
		this.commands.add(command);
	}

	public String getCommand(int index) {
		return this.commands.get(index);
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return this.ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPort() {
		return this.port;
	}

	public void setSuccessorsList(Node node) {
		this.successors.add(node);
	}

	
	public int getSizeSuccessorsList() {
		return this.successors.size();
	}

	public void upload(double source) throws Exception {
		Node node;
		this.source.add(source);
		String sourcekey = null;
		sourcekey = Hashfunction.hashfunction(Double.toString(source));

		int pseudosourcekey = Integer.parseInt(sourcekey) % 8;

		node = findSuccessor(pseudosourcekey);
		node.saveSource(sourcekey);
	}

	public void saveSource(String sourcekey) throws Exception {
		this.setSourcekey(sourcekey);
	}

	public double getSource(int index) {
		return this.source.get(index);
	}

	public Node findSuccessor(int id) {
		int n1 = findPredecessor(id);
		return Server.node[(int) Server.node[n1].fingerline[0].getSuccessor().getId()];
	}

	public int findPredecessor(int id) {
		int n1 = this.getId();
		// System.out.println("the successor of n1 :"+Server.node[n1].fingerline[0].getSuccessor()+n1+id);
		// if(Server.node[n1].fingerline[0].getSuccessor()<Server.node[n1].getId())
		double compare = 0;
		compare = Server.node[n1].getId()- Server.node[n1].fingerline[0].getSuccessor().getId();
		while ((compare < 0 && (id > Server.node[n1].fingerline[0].getSuccessor().getId() || id <= Server.node[n1].getId())) || (compare > 0 && id > Server.node[n1].fingerline[0].getSuccessor().getId() && id <= Server.node[n1].getId())) {
			n1 = closestPrecedingFinger(Server.node[n1], id);
			compare = Server.node[n1].getId() - Server.node[n1].fingerline[0].getSuccessor().getId();
		}
		return n1;
	}

	public int closestPrecedingFinger(Node node, int id) {
		int i = 0;
		int n = 0;
		// System.out.println("id of the node:"+node.getId()+"\n"+"id look for:"+id);
		if (node.getId() == id) {
			n = node.getId();
		} else {
			if (node.getId() < id) {
				i = Fingerline.m - 1;

				for (; i >= 0; i--) {

					if (node.getId() < node.fingerline[i].getSuccessor().getId() && id > node.fingerline[i].getSuccessor().getId()) {
						n = (int) node.fingerline[i].getSuccessor().getId();
						// System.out.println(n);
						break;
					} else
						n = node.getId();
				}
			} else if (node.getId() > id) {
				i = Fingerline.m - 1;
				// System.out.println(node.fingerline[i].getSuccessor()+"\n"+node.getId());
				for (; i >= 0; i--) {

					if (node.fingerline[i].getSuccessor().getId() > node.getId() || node.fingerline[i].getSuccessor().getId() < id) {
						n = (int) node.fingerline[i].getSuccessor().getId();
						break;
					} else
						n = node.getId();
				}
			}
		}
		// System.out.println(n);
		return n;
	}
    
	public void joinNode()
	{
		if (!ActiveNode.activenode.isEmpty()) {
			int i;
			for (i = 0; i < Fingerline.m; i++) {
				this.fingerline[i] = new Fingerline();
				this.fingerline[i].setStart(this.id, i);
				this.fingerline[i].setEnd(this.id, i);
				this.fingerline[i].setInterval(this.fingerline[i].getStart(),this.fingerline[i].getEnd(), i);
			}
			int size = ActiveNode.activenode.size();
			Random random = new Random();
			int ref = random.nextInt(size);
			this.setPredecessor(null);
		    this.fingerline[0].setSuccessor(ActiveNode.activenode.get(ref).findSuccessor(this.getId()));
			ActiveNode.addActiveNode(this);
			this.active = true;
		}
		else {
			int i;
		    for (i = 0; i < Fingerline.m; i++) {
			this.fingerline[i] = new Fingerline();
			this.fingerline[i].setStart(this.id, i);
			this.fingerline[i].setEnd(this.id, i);
			this.fingerline[i].setInterval(this.fingerline[i].getStart(),this.fingerline[i].getEnd(), i);
			this.fingerline[i].setSuccessor(this);
			}
		    this.setPredecessor(this); 
		    ActiveNode.addActiveNode(this);
		    this.active = true;
	}
	}
	
	public void stablize()
	{
		Node x = new Node();
		x = this.fingerline[0].getSuccessor().getPredecessor();
		if(x.active==true)
		{
		double compare = 0;
		compare = this.getId()-this.fingerline[0].getSuccessor().getId();
		if ((compare < 0 && (x.getId() > this.getId() && x.getId() < this.fingerline[0].getSuccessor().getId()))	|| (compare >= 0 && ((x.getId()) > this.getId() || x.getId() < this.fingerline[0].getSuccessor().getId()))) 
		{
           this.fingerline[0].setSuccessor(x);  
		}
		}
		this.fingerline[0].getSuccessor().notifyothers(this);
	}
	
	public void notifyothers(Node node)
	{
		if(this.getPredecessor()==null)
		this.setPredecessor(node);	
		double compare = 0;
		compare = this.getPredecessor().getId()-this.getId();
		if(this.getPredecessor().active==false || (compare < 0 && (node.getId() > this.getPredecessor().getId()) && (node.getId() < this.getId())) || (compare >= 0 && (node.getId() > this.getPredecessor().getId() || node.getId() < this.getId())))
		{
			this.setPredecessor(node);
		}
	}
	
	public void fixFingers()
	{
		for(int next=1;next<Fingerline.m;next++)
		{
		if(ActiveNode.activenode.contains(Server.node[(int)this.fingerline[next].getStart()]))
		{
			this.fingerline[next].setSuccessor(Server.node[(int)this.fingerline[next].getStart()]);
		}
		this.fingerline[next].setSuccessor(this.findSuccessor((int)this.fingerline[next].getStart()));
	}
	}
	
	public void maintainSuccessorList()
	{
		this.successors.clear();
		Node s = new Node();
		s = this;
		int i;
	   	for(i=0;i<Fingerline.m;i++)
	   	{
	   		s = s.fingerline[0].getSuccessor();
	   		System.out.println(s.getId());
	   		this.successors.add(s);
	   	}
	}
	
	public void checkfailure()
	{
		if(this.fingerline[0].getSuccessor().active==false)
		{
		this.successors.remove();
		System.out.println(this.successors.peek().getId());
		this.fingerline[0].setSuccessor(this.successors.peek());
		}
	}
	/*  first version without concurrence 
	public void join() {
		if (!ActiveNode.activenode.isEmpty()) {
			int size = ActiveNode.activenode.size();
			Random random = new Random();
			int ref = random.nextInt(size);
			System.out.println("ref:" + ActiveNode.activenode.get(ref).getId());
			initFingerTable(ActiveNode.activenode.get(ref));
			updateOthers();
		} else {
			int i;
			for (i = 0; i < Fingerline.m; i++) {
				this.fingerline[i] = new Fingerline();
				this.fingerline[i].setStart(this.id, i);
				this.fingerline[i].setEnd(this.id, i);
				this.fingerline[i].setInterval(this.fingerline[i].getStart(),this.fingerline[i].getEnd(), i);
				this.fingerline[i].setSuccessor(this);
			}
			this.setPredecessor(this);
		}

	}
    */
	
	
    
	public void leave() {
		
		 this.active = false;
		 ActiveNode.activenode.remove(this);
	}

	/*
	public void initFingerTable(Node node) {
		int i;
		for (i = 0; i < Fingerline.m; i++) // init start|end|interval
		{
			this.fingerline[i] = new Fingerline();
			this.fingerline[i].setStart(this.id, i);
			this.fingerline[i].setEnd(this.id, i);
			this.fingerline[i].setInterval(this.fingerline[i].getStart(),this.fingerline[i].getEnd(), i);
		}
		if(!ActiveNode.activenode.contains(Server.node[(int)this.fingerline[0].getStart()]))
		this.setSuccessorsList(node.findSuccessor((int) this.fingerline[0].getStart()));
		else this.setSuccessorsList(Server.node[(int)this.fingerline[0].getStart()]);
		this.fingerline[0].setSuccessor(Server.node[this.getSuccessorsList(0).getId()]);
		this.setPredecessor(this.getSuccessorsList(0).getPredecessor());
		this.getSuccessorsList(0).setPredecessor(this);
		for (i = 0; i < (Fingerline.m - 1); i++) {
			double compare = 0;
			compare = this.getId() - this.fingerline[i].getSuccessor().getId();
			//System.out.println(this.fingerline[i].getSuccessor().getId());
			if ((compare < 0 && (this.fingerline[i + 1].getStart() >= this.getId() && this.fingerline[i + 1].getStart() <= this.fingerline[i].getSuccessor().getId())) || (compare > 0 && (this.fingerline[i + 1].getStart() >= this.getId() || this.fingerline[i + 1].getStart() <= this.fingerline[i].getSuccessor().getId()))) {
				this.fingerline[i + 1].setSuccessor(this.fingerline[i].getSuccessor());
			   // System.out.println(this.fingerline[i+1].getSuccessor().getId());
			} else {
				this.fingerline[i + 1].setSuccessor(node.findSuccessor((int) this.fingerline[i+1].getStart()));
			   // System.out.println(this.fingerline[i+1].getSuccessor().getId());
			}
		}
		
	}

	public void updateOthers() {
		int i;
		for (i = 0; i < Fingerline.m; i++) {
			Node p = new Node();
			int k = (int) (this.getId()-Math.pow(2, i));
			if(k<0) k = k +(int) Math.pow(2, Fingerline.m) ;
		    if(!ActiveNode.activenode.contains(Server.node[k]))
			p = Server.node[this.findPredecessor(k)];
		    else p = Server.node[k];
		    System.out.println("node for update:"+p.getId());
			p.updateFingerTable(this, i);
		}
		

	}

	public void updateFingerTable(Node node, int i) // pas encore fini
	{
		double compare = 0;
		compare = this.getId() - this.fingerline[i].getSuccessor().getId();
		if ((compare < 0 && (node.getId() > this.getId() && node.getId() < this.fingerline[i].getSuccessor().getId()))	|| (compare >= 0 && ((node.getId()) > this.getId() || node.getId() < this.fingerline[i].getSuccessor().getId()))) 
		{
			this.fingerline[i].setSuccessor(node);
			//System.out.println(this.fingerline[i].getSuccessor().id);
		Node p = new Node();
		p = this.predecessor;
		p.updateFingerTable(node, i);
	}
	}
	*/
	public void run() {
         this.joinNode();
         try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         int i=0;
         while(i<20) 
         {
        	 
        	 this.stablize();
        	 System.out.println("stablize"+this.getId()+i);
        	 if(i>13){
        	 for(int j=0;j<8;j=j+2)
        	 {
             if(Server.node[j].fingerline[0].getSuccessor()==null)
            	 System.out.print("successor:"+"null");
             else
            	 System.out.print("successor:"+Server.node[j].fingerline[0].getSuccessor().getId()+" ");
             if(Server.node[j].getPredecessor()==null)
            	 System.out.println("predecessor:"+"null ");
             else
            	 System.out.println("predecessor:"+Server.node[j].getPredecessor().id+" ");
        	 }
        	 }
        	 
        	 try {
     			Thread.sleep(1000);
     		} catch (InterruptedException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        	 this.fixFingers();
        	 try {
      			Thread.sleep(1000);
      		} catch (InterruptedException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
        	 this.maintainSuccessorList();
        	 try {
       			Thread.sleep(1000);
       		} catch (InterruptedException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
        	 this.checkfailure();
        	 try {
        			Thread.sleep(1000);
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	 i++;
         }		
         this.leave();
	}
}
