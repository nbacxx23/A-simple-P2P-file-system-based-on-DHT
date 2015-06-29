package chord;


public class Testnode {
   
   public static void main(String args[]){     
     int i; 
     Server.server();
     System.out.println("Just for testing");
     Server.node[3].join();  
     ActiveNode.addActiveNode(Server.node[3]);
     Server.node[7].join();     
     ActiveNode.addActiveNode(Server.node[7]);
     //System.out.println(Server.node[7].getPredecessor().getId());
     Server.node[5].join();
     ActiveNode.addActiveNode(Server.node[5]);
     Server.node[6].join();
     ActiveNode.addActiveNode(Server.node[6]);
     Server.node[0].join();
     ActiveNode.addActiveNode(Server.node[0]);
     for(i=0;i<Fingerline.m;i++)
     {   
    	 System.out.println("0:"+i+Server.node[0].fingerline[i].getSuccessor().getId());
    	 System.out.println("3:"+i+Server.node[3].fingerline[i].getSuccessor().getId());
    	 System.out.println("5:"+i+Server.node[5].fingerline[i].getSuccessor().getId());
    	 System.out.println("6:"+i+Server.node[6].fingerline[i].getSuccessor().getId());
    	 System.out.println("7:"+i+Server.node[7].fingerline[i].getSuccessor().getId());
     }
     System.out.println("Pre 0:"+Server.node[3].getPredecessor().getId());
     System.out.println("Pre 3:"+Server.node[3].getPredecessor().getId());
     System.out.println("Pre 5:"+Server.node[5].getPredecessor().getId());
     System.out.println("Pre 6:"+Server.node[6].getPredecessor().getId());
     System.out.println("Pre 7:"+Server.node[7].getPredecessor().getId());
     
}
   
}