package generatePatternDatabase;

public class PQItem {
	public final int squareVal;
	public final int numInSquare;
	public PQItem(int squareVal, byte numInSquare)
	{
		if(this.squareVal<0)
		{
			System.out.println("LOL");
		}
		this.squareVal=squareVal;
		this.numInSquare=numInSquare;
	}
}
