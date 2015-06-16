package chord;

public class Hashfunction {
	public static int hashfunction(int source)
	   {
		 int hashkey;  
		 hashkey = source % 4;
		 return hashkey;
	   }
}
