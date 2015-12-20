package generatePatternDatabase;

public class LargeByteArray {
	private byte [][] byteArrays;
	public static final int MAXARRAYSIZE=1_000_000_000;
	public LargeByteArray(long value)
	{
		int numByteArrays=(int)(value/MAXARRAYSIZE)+1;
		byteArrays=new byte[numByteArrays][];
		
		for(int i=0; i<numByteArrays; i++)
		{
			if(value>MAXARRAYSIZE)
			{
				value-=MAXARRAYSIZE;
				byteArrays[i]=new byte[MAXARRAYSIZE];
			}
			else
			{
				byteArrays[i]=new byte[(int)value];
			}
		}
	}
	int getLeftIndex(int index)
	{
		if(index>0)
		{
			return index/MAXARRAYSIZE;
		}
		else
		{
			long newIndex=(index-Integer.MIN_VALUE)+(long)Integer.MAX_VALUE%MAXARRAYSIZE;
			int leftArrayIndex=Integer.MAX_VALUE/MAXARRAYSIZE;
			leftArrayIndex+=newIndex/MAXARRAYSIZE;
			return leftArrayIndex;
		}
	}
	int getRightIndex(int index)
	{
		if(index>0)
		{
			return (index%MAXARRAYSIZE);
		}
		else
		{
			long newIndex=(long)(index-Integer.MIN_VALUE)+Integer.MAX_VALUE%MAXARRAYSIZE;
			return (int)(newIndex%MAXARRAYSIZE);
		}
	}
	public void set(int index, byte value)
	{
		byteArrays[getLeftIndex(index)][getRightIndex(index)]=value;
	}
	
	public byte get(int index)
	{
		return byteArrays[getLeftIndex(index)][getRightIndex(index)];
	}
}
