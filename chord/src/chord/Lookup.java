package chord;

public class Lookup {
    public static int findSuccessor(Node node,int id)
    {
      int n1 = findPredecessor(node,id);
      return Testnode.node[n1].getSuccessorsList(0).getId();
    }
    public static int findPredecessor(Node node,int id)
    {
    	int n1= node.getId();
    	while(id<=n1 || id>Testnode.node[n1].getSuccessorsList(0).getId() )
    		n1 = closestPrecedingFinger (Testnode.node[n1],id);
    	return n1;
    }
    public static int closestPrecedingFinger(Node node,int id)
    {
    	   int i;
    	   int n=0;
    	   if(node.getId()==id)
    	   {n=node.getId();}
    	   else{
    		   if(node.getId()<id)
    			{
    			   for(i=Fingerline.m-1;i<0;i--)
    	    	    {
    	    	     if(node.getId()<=node.fingerline[i].getSuccessor() && id>=node.fingerline[i].getSuccessor())
    	    	    	 n = (int)node.fingerline[i].getSuccessor();
    	    	    }
    			  }
    		   else {
    			   for(i=Fingerline.m-1;i<0;i--)
    			   {
    				   if(node.fingerline[i].getSuccessor()>node.getId() || node.getId()<node.fingerline[i].getSuccessor())
    					   n = (int)node.fingerline[i].getSuccessor();
    			   }
    		   }
    			}
    	   return n;
    	    
    	   }
       
  }


