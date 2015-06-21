package chord;


public class Testnode {
   
   public static void main(String args[]){     
     int location; 
     Server.server();
     System.out.println(Server.node[1].fingerline[1].getSuccessor());
     System.out.println("Just for testing");
     location = Server.node[1].findSuccessor(0);
     System.out.println("the successor of the id 0: is node"+location);     
     
}
   
}