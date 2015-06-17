package chord;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    private int id ;
    private String ip;	
	private String port;
	private List<Node> successors = new ArrayList<Node>();
	
	int sourcekey[]= new int[4];
	
	private List<Integer> source = new ArrayList<Integer>();
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
		return this.successors.get(index);
	}
    public int getSizeSuccessorsList()
    {
	    return this.successors.size(); 
    }
	public void upload(int source)
	{
		this.source.add(source);
	}
	public int getSource(int index)
	{
		return this.source.get(index);
	}
}
