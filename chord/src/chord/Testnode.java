package chord;


public class Testnode {
   
   public static void main(String args[]) throws InterruptedException{     
     int i; 
     Server.server();
     System.out.println("Just for testing");
     Thread[] myThread = new Thread[8]; 
     myThread[0]= new Thread(Server.node[0]);
     myThread[0].start();
     Thread.sleep(20000);
     for(i=2;i<8;i=i+2)
     {
    	 myThread[i]= new Thread(Server.node[i]);
    	 myThread[i].start();
    	 Thread.sleep(1000);
     }
  
     for(i=0;i<8;i=i+2)
     {
    	 myThread[i].join();
     }
   
     for(i=0;i<Fingerline.m;i++)
     {   
    	 System.out.println("0:"+i+Server.node[0].fingerline[i].getSuccessor().getId());
    	 System.out.println("2:"+i+Server.node[2].fingerline[i].getSuccessor().getId());
    	 System.out.println("4:"+i+Server.node[4].fingerline[i].getSuccessor().getId());
    	 System.out.println("6:"+i+Server.node[6].fingerline[i].getSuccessor().getId());
     }
     System.out.println("Pre 0:"+Server.node[0].getPredecessor().getId());
     System.out.println("Pre 2:"+Server.node[2].getPredecessor().getId());
     System.out.println("Pre 4:"+Server.node[4].getPredecessor().getId());
     System.out.println("Pre 6:"+Server.node[6].getPredecessor().getId());
     
}
   
}