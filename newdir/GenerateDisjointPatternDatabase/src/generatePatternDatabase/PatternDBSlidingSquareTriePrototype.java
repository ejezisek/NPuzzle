package generatePatternDatabase;

public class PatternDBSlidingSquareTriePrototype {
	private PatternDBSlidingSquareCollection trie;
	private byte height;
	private byte width;
	public final int[] availableValues;
	public PatternDBSlidingSquareTriePrototype(byte height, byte width, int [] values)
	{
		trie=new PatternDBSlidingSquareCollection(values, height, width);
		this.height=height;
		this.width=width;
		availableValues=values;
	}
	public byte getHeight() {
		return height;
	}
	public byte getWidth() {
		return width;
	}
	public boolean exists(byte [] vals)
	{
		if(vals.length!=height*width)
			return false;
		else
		{
			return trie.exists(vals);
		}
	}

	public byte[] convertToBytes(long square)
	{
		return PatternDBSlidingSquareCollection.convertToBytes(square, height, width);
	}
	
	public boolean exists(long square)
	{
		return exists(convertToBytes(square));
	}

	public void add(long square, Byte value)
	{
		add(convertToBytes(square), value);
	}
	public void add(byte [] vals, Byte value)
	{
		trie.addNode(vals, value);
	}
	public void write(String filename) {
		trie.write(filename);
	}
	public int get(Integer i) {
		return trie.get(trie.toBytes(i));
	}
	public PatternDBSlidingSquareCollection getTrie() {
		// TODO Auto-generated method stub
		return trie;
	}
}
