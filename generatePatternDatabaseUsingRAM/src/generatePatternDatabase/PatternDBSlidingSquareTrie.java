package generatePatternDatabase;

import java.util.HashMap;

public class PatternDBSlidingSquareTrie {
	public HashMap<Byte, PatternDBSlidingSquareTrie> connectedNodes;
	private Byte value;
	public Byte getValue()
	{
		return value;
	}
	public PatternDBSlidingSquareTrie(byte value)
	{
		this.value=value;
	}
	public PatternDBSlidingSquareTrie()
	{
		value=null;
		connectedNodes=new HashMap<Byte, PatternDBSlidingSquareTrie>();
	}
	
}
