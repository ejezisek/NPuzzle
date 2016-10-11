package generatePatternDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LargeByteArray {
	private byte [][] byteArrays;
	public static final int MAXARRAYSIZE=1_000_000_000;
	public LargeByteArray(String filename) throws FileNotFoundException, IOException
	{
		File f=new File(filename);
		try(FileInputStream fin=new FileInputStream(f))
		{
			int numByteArrays=(int)(f.length()/MAXARRAYSIZE)+1;
			byteArrays=new byte[numByteArrays][];
			int i=0;
			long length=f.length();
			while(length>MAXARRAYSIZE)
			{
				byte[] vals=new byte[MAXARRAYSIZE];
				fin.read(vals);
				byteArrays[i++]=vals;
				length-=MAXARRAYSIZE;
			}
			byte[] vals=new byte[(int) length];
			fin.read(vals);
			byteArrays[i]=vals;
		}
	}
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
	public static int getLeftIndex(int index)
	{
		if(index>=0)
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
	public static int getRightIndex(int index)
	{

		if(index>=0)
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

	public void write(String filename) throws IOException
	{
		FileOutputStream fos=null;
		try{
		 fos=new FileOutputStream(filename);
		
		 for(int i=0; i<byteArrays.length; i++)
		 {
		 	fos.write(byteArrays[i]);
		 }
		}
		finally
		{
			if(fos!=null)
			fos.close();
		}
	}
	public byte [] getArray(int index)
	{
		return byteArrays[index];
	}
}
