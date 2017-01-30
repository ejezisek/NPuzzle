package generatePatternDatabase;

public class PQItem {
	public final int squareVal;
	public final int numInSquare;
	public PQItem(int squareVal, byte numInSquare)
	{
		this.squareVal=squareVal;
		this.numInSquare=numInSquare;
	}
	public String toString()
	{
		return squareVal + " " + numInSquare;
	}
}
