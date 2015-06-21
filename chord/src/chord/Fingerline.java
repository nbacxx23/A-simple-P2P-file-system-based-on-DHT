package chord;

import java.util.HashMap;
import chord.Testnode;

public class Fingerline {
   private double start;
   private double successor;
   private double end;
   static final int m = 2;
   private double[][] interval= new double[m][2];
   private HashMap[] hashsuccessor = new HashMap[m];
   
   public void setStart(int id, int k)
	{
		this.start = (id + Math.pow(2, k))% (Math.pow(2, m));
	}
	public double getStart()
	{
		return this.start;
	}
	public void setSuccessor(Node node,int k)
	{
	    int size = node.getSizeSuccessorsList();
		int i=0;
		double compare = node.fingerline[k].getStart() - node.fingerline[k].getEnd();
	    if(compare < 0)
	    {
	       while(i<size) 
	    	{if((node.getSuccessorsList(i).getId()>=node.fingerline[k].getStart())&&(node.getSuccessorsList(i).getId()<=node.fingerline[k].getEnd()))
	      {
	         this.successor = node.getSuccessorsList(i).getId();
	         hashsuccessor[k] = new HashMap();
	         hashsuccessor[k].put(node.fingerline[k].getStart(), this.successor);
	         return;
	      }
	    	else {
	    		i++;
	    		continue;}}
	    this.successor = node.getSuccessorsList(0).getId();
	    hashsuccessor[k] = new HashMap();
        hashsuccessor[k].put(node.fingerline[k].getStart(), this.successor);
        return;
        
	    }
	    else 
	    {
	    	while(i<size) 
	    	{if((node.getSuccessorsList(i).getId()>=node.fingerline[k].getStart())|| (node.getSuccessorsList(i).getId()<=node.fingerline[k].getEnd()))
	      {
	         this.successor = node.getSuccessorsList(i).getId();
	         hashsuccessor[k] = new HashMap();
	         hashsuccessor[k].put(node.fingerline[k].getStart(), this.successor);
	         return;
	      }
	    	else {
	    		i++;
	    		continue;}}
	    this.successor = node.getSuccessorsList(0).getId();
	    hashsuccessor[k] = new HashMap();
        hashsuccessor[k].put(node.fingerline[k].getStart(), this.successor);
        return;
	    }
	}
	    
	public double getSuccessor()
	{
		return this.successor;
	}
	public void setEnd(int id,int k)
	{
		this.end = (id + Math.pow(2, (k+1)))% (Math.pow(2, m));
	}
	public double getEnd()
	{
		return this.end;
	}
	public void setInterval(double start,double end, int j)
	{
		this.interval[j][0]= start;
		this.interval[j][1]= end;
	}
	public double[] getIntervel(int j)
	{
		return this.interval[j];
	}
   
   
}
