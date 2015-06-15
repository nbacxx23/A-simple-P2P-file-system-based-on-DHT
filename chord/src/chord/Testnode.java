package chord;
import java.io.*;
import java.util.HashMap;
import chord.Node;

public class Testnode {
      
   
   public static void main(String args[]){
   Node[] node = new Node[4];
   
   int i;	   
   for(i=0;i<4;i++)  //initial source information at each node
   {
	  node[i] = new Node();
	  node[i].setId(i);
	  node[i].upload(i, 0);
	  node[i].sourcekey[0]= hashfunction(node[i].source[0]);
   }
     for(i=0;i<4;i++)
   {   
	   int j;
	   for(j=0;j<Fingerline.m;j++)  //initial fingertable for each node
	   {
	   node[i].fingerline[j].setStart(node[i].getId(),j);
	   node[i].fingerline[j].setEnd(node[i].getId(), j);
	   node[i].fingerline[j].setInterval(node[i].fingerline[j].getStart(),node[i].fingerline[j].getEnd(),j);
	   }
	   
	 for(i=0;i<4;i++)               //initial the successor List
	 {
		 int k = i;
		 int count = 4;
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
   }
}
   public static int hashfunction(int source)
   {
	 int hashkey;  
	 hashkey = source % 4;
	 return hashkey;
   }
   
   
}