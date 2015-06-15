package chord;

public class Fingerline {
   private double start;
   private double successor;
   private double end;
   private double[][] interval= new double[m][2];
   public void setStart(int id, int k)
	{
		this.start = (id + Math.pow(2, k))% (Math.pow(2, m));
	}
	public double getStart()
	{
		return this.start;
	}
	public void setSuccessor(int successor)
	{
		this.successor = successor;
	}
	public double getSuccessor()
	{
		return this.successor;
	}
	public void setEnd(int id,int k)
	{
		this.end = id + Math.pow(2, (k+1))% (Math.pow(2, m));
	}
	public double getEnd()
	{
		return this.end;
	}
	public void setInterval(double start,double end, int j)
	{
		this.interval[j][0]= start;
		this.interval[j][1]= end;
	}
	public double getIntervel()
	{
		return this.successor;
	}
   static final int m = 2;
   public void setFingerline(){
	
   }
}
