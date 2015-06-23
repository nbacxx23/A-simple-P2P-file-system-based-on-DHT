package chord;


public class Testnode {
   
   public static void main(String args[]){     
     int i; 
     Server.server();
     System.out.println("Just for testing");
     Server.node[7].join(Server.node[3]);  
     ActiveNode.addActiveNode(Server.node[7]);
     Server.node[3].join(Server.node[7]);
     for(i=0;i<Fingerline.m;i++)
     System.out.println(Server.node[3].fingerline[i].getSuccessor().getId());
}
   
}