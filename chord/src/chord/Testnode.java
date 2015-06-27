package chord;


public class Testnode {
   
   public static void main(String args[]){     
     int i; 
     Server.server();
     System.out.println("Just for testing");
     Server.node[7].join();  
     ActiveNode.addActiveNode(Server.node[7]);
     for(i=0;i<Fingerline.m;i++)
     System.out.println(Server.node[7].fingerline[i].getSuccessor().getId());
     Server.node[3].join();
     ActiveNode.addActiveNode(Server.node[3]);
     System.out.println(Server.node[3].getPredecessor());
     for(i=0;i<Fingerline.m;i++)
     {   
    	 System.out.println(Server.node[7].fingerline[i].getSuccessor().getId());
    	 System.out.println(Server.node[3].fingerline[i].getSuccessor().getId());
    	// System.out.println(Server.node[7].fingerline[i].getSuccessor().getId());
     }
     
}
   
}