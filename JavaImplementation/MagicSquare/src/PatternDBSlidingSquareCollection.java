

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PatternDBSlidingSquareCollection {
	private byte [] connectedByteNodes;
	private int[] availableValues;
	private byte height;
	private byte width;
	public static int maxValue=-1;
	private int otherVal=-1;
	public static byte[] convertToBytes(long square, byte height, byte width)
	{
		byte[] vals=new byte[height*width];
		for(int i=0; i<vals.length; i++)
		{
			vals[i]=(byte)(square&0x0F);
			square>>>=4;
		}
		return vals;
	}
	public PatternDBSlidingSquareCollection(int[] values, byte height, byte width, byte[] connectedByteNodes) 
	{
			this(values, height,width);
			this.connectedByteNodes=connectedByteNodes;
	}
	public PatternDBSlidingSquareCollection(int[] values, byte height, byte width) {
		this.availableValues=values;
		this.height=height;
		this.width=width;
		connectedByteNodes=new byte[getMax(values)];
		otherVal=height*width-values.length;
		if(height*width==values.length)
			maxValue=(int)factorial(height*width-1);
		else
			maxValue=(int)(factorial(height*width-1)/factorial(height*width-values.length));
	}
	public int getIndex(long values)
	{
		return getIndex(convertToBytes(values, height, width));
	}
	private static long factorial(int n)
	{
		long total=1;
		for(int i=1; i<=n; i++)
			total*=i;
		return total;
	}
	public int getMax(int[] values)
	{
		int maxIndex=0;
		int multiplier=height*width;
		
		for(int val:values)
		{
			maxIndex*=multiplier--;
			maxIndex+=multiplier;
		}

		return maxIndex+height*width;
	}
	public int getIndex(byte[] values)
	{
		byte [] valuesCopy=values.clone();
		int actualIndex=0;
		int multiplier=height*width;
		for(int val:availableValues)
		{
			actualIndex*=multiplier--;
			int i=0;
			for(int index=0; index<valuesCopy.length; index++)
			{
				if(val==valuesCopy[index])
				{
					valuesCopy[index]=-1;
					break;
				}
				if(valuesCopy[index]!=-1)
					i++;

			}
			actualIndex+=i;
		}

		return actualIndex;
	}
	public byte[] toBytes(int index)
	{
		Byte[] vals=new Byte[width*height-otherVal];
		int byteIndex=0;
		int tempMax=maxValue;
		for(int i=width*height-1; i>otherVal; i--)
		{
			byte currByte=(byte)(index/tempMax);
			vals[byteIndex++]=currByte;
			index%=tempMax;
			tempMax/=i;
		}
		vals[byteIndex]=(byte)index;
		for(int i=vals.length-1; i>=0; i--)
		{
			for(int j=i-1; j>=0; j--)
			{
				byte current=vals[i];
				if(current>=vals[j])
				{
					vals[i]++;
				}
			}
		}
		byte [] ret=new byte[16];
		for(int i=0; i<16; i++)
			ret[i]=6;
		int j=0;
		for(byte val:vals)
			ret[val]=(byte)availableValues[j++];
		return ret;
	}
	public void addNode(byte[] vals, byte value)
	{
		int index=getIndex(vals);
		connectedByteNodes[index]=(byte) (value+1);
	}
	public boolean exists(byte[] vals)
	{
		int index=getIndex(vals);
		return connectedByteNodes[index]!=0;
	}
	public byte get(byte[] vals)
	{
		int index=getIndex(vals);
		return get(index);
	}
	public byte get(int val)
	{
		return (byte) (connectedByteNodes[val]-2);

	}
	public void write(String filename) {
			try {
				Files.write(Paths.get(filename), connectedByteNodes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}
	
}
