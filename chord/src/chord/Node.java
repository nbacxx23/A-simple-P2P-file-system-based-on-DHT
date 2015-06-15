package chord;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    private int id ;
    private String ip;	
	private String port;
	private List<Node> successors = null;
	
	int sourcekey[]= new int[4];
	
	HashMap[] hashsuccessor = new HashMap[2];
	int[] source = new int[4];
	Fingerline[] fingerline = new Fingerline[Fingerline.m]; 
	
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return this.id;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public String getIp()
	{
		return this.ip;
	}
	public void setPort(String port)
	{
		this.port = port;
	}
	public String getPort()
	{
		return this.port;
	}
	public void setSuccessorsList(Node node)
	{
		this.successors.add(node);
	}
	public Node getSuccessorsList(int index)
	{
		return (Node)this.successors.get(index);
	}
	public void lookup(int sourcekey)
	{
        
	}
	public void upload(int source,int index)
	{
		this.source[index]=source;
	}
}
