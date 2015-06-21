package chord;

public final class Server {
	final static Node[] node = new Node[4];
	
	public static void server()
	{
		int i;	   
		   for(i=0;i<4;i++)  //initial source information at each node
		   {
			  node[i] = new Node();
			  node[i].setId(i);
			  node[i].upload(i);
			  node[i].sourcekey[0]= Hashfunction.hashfunction(node[i].getSource(0));
		   }
		   for(i=0;i<4;i++)               //initial the successor List
			 {   
			     int k;
			     if(i==3){k = 0;}
			     else k = i+1;
				 int count = 3;
				 while(count!=0)
				 {
					 node[i].setSuccessorsList(node[k]);
					 if(k==3) {
						 k=0;
					     count--;
					 }
					 else {
						k++;
						count--;
					 }
				 }
				 
			 }
		   for(i=0;i<4;i++) //initial fingertable for each node
		   {   
			   int j;
			   for(j=0;j<Fingerline.m;j++)
			   {
			   node[i].fingerline[j]= new Fingerline();
			   node[i].fingerline[j].setStart(node[i].getId(),j);
			   node[i].fingerline[j].setEnd(node[i].getId(), j);
			   node[i].fingerline[j].setInterval(node[i].fingerline[j].getStart(),node[i].fingerline[j].getEnd(),j);
			   node[i].fingerline[j].setSuccessor(node[i], j);
			   }
			   
			 
		   }
	}
	
}
