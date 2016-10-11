package generatePatternDatabase;

import java.util.Arrays;

public class SquareLookup {

	private int N;
	private int K;
	private StorageSystem s;
	/**
	 * 
	 * @param N - Represents the number of squares represented by the filename.
	 */
	public SquareLookup(int N, int squareSize, StorageSystem s)
	{
		this.N=N;
		this.K=squareSize-N;
		this.s=s;
	}

	private long location_l(long square)
	{
		long ret=0;
		for(int i=0; i<N; i++)
		{
			ret<<=4;
			byte val=(byte)(square&0x0F);
			square>>>=4;
			ret+=val;
		}
		return ret;
	}

	private long value_l(long square)
	{
		long ret=0;
		for(int i=0; i<N; i++)
		{
			square>>>=4;
		}
		for(int i=0; i<K; i++)
		{
			ret<<=4;
			byte val=(byte)(square&0x0F);
			square>>>=4;
			ret+=val;
		}
		return ret;
	}
	//This is based on N
	private String location(long squareCopy)
	{
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<N; i++)
		{
			byte val=(byte)(squareCopy&0x0F);
			squareCopy>>>=4;
			sb.append(val);
		}
		return sb.toString();
	}
	
	//This is based on N
	private String[] location(long[] vals)
	{
		String [] ret=new String[vals.length];
		StringBuilder [] retSB=new StringBuilder[vals.length];
		int j=0;
		for(long val2:vals)
		{
			retSB[j]=new StringBuilder();
			for(int i=0; i<N; i++)
			{
				byte val=(byte)(val2&0x0F);
				val2>>>=4;
				retSB[j].append(val);
			}
			j++;
		}
		for(int i=0; i<retSB.length; i++)
		{
			ret[i]=retSB[i].toString();
		}
		return ret;
	}
	
	private String[] value(long[] vals)
	{
		String [] ret=new String[vals.length];
		StringBuilder [] retSB=new StringBuilder[vals.length];
		int j=0;
		for(long val2:vals)
		{
			retSB[j]=new StringBuilder();
			for(int i=0; i<N; i++)
			{
				val2>>>=4;
			}

			for(int i=0; i<K; i++)
			{
				byte val=(byte)(val2&0x0F);
				val2>>>=4;
				retSB[j].append(val);
			}
			j++;
		}
		for(int i=0; i<retSB.length; i++)
		{
			ret[i]=retSB[i].toString();
		}
		return ret;
	}
	
	//This is based on K.
	private String value(long squareCopy)
	{
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<N; i++)
		{
			squareCopy>>>=4;
		}
		for(int i=0; i<K; i++)
		{
			byte val=(byte)(squareCopy&0x0F);
			squareCopy>>>=4;
			sb.append(val);
		}
		return sb.toString();
	}
	private class Square implements Comparable<Square>
	{
		public final String location;
		public final String destination;
		public final int numToDestination;
		private final Long locComp;
		private final Long destComp;
		private Square(long val, int numToDestination)
		{
			this.numToDestination=numToDestination;
			location=location(val);
			locComp=location_l(val);
			destination=value(val);
			destComp=value_l(val);
		}

		@Override
		public int compareTo(Square o) {
			int val=this.locComp.compareTo(o.locComp);
			if(val!=0) return val;
			return this.destComp.compareTo(o.destComp);
		}
	}
	public void add(long[] val, int []numToDestination)
	{
		Square [] squares=new Square[val.length];
		for(int i=0; i<val.length; i++)
		{
			squares[i]=new Square(val[i],numToDestination[i]);
		}
		Arrays.sort(squares);
		String [] locations=new String[val.length];
		String [] values=new String[val.length];
		int [] destinationValues=new int[val.length];
		
		for(int i=0; i<val.length ;i++)
		{
			locations[i]=squares[i].location;
			values[i]=squares[i].destination;
			if(locations[i].equals("1606646723") && values[i].equals("685666"))
			{
				System.out.println(locations[i]+" "+values[i]);
			}
			destinationValues[i]=squares[i].numToDestination;
		}
		s.addMultiple(locations, values, destinationValues);
	}
	public boolean[] exists(long []val)
	{
		return s.exists(location(val), value(val));
	}
}
