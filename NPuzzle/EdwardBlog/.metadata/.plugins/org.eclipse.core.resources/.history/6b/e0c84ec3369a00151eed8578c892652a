package generatePatternDatabase;
import java.nio.ByteBuffer;

/**
 * This class creates a ixj square puzzle problem, with random starting squares.
 * The starting squares are found using a Knuth-Fischer-Yates shuffle.  
 * Please view the following blog post (http://blog.codinghorror.com/the-danger-of-naivete/)
 * for information on potential mistakes in the Shuffle.
 * The generated square should have a 50% chance of being solvable.
 */
public class PatternDBSlidingSquareUsingLong
{
	//The valid directions the square can be moved.	
	public static enum DIRECTION {UP, LEFT, DOWN, RIGHT;
		public DIRECTION getOpposite()
		{
			switch(this)
			{
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			case UP:
				return DOWN;
			}
			return null;
		}
	};
	//This is where the data for the fifteen square is stored.
	private long squares;
	private byte height;
	private byte width;
	private byte numInRoute;
	public long getSquares()
	{
		return squares;
	}
	//This is the only position that can be moved.
	private Point emptyPosition;
	
	public PatternDBSlidingSquareUsingLong(long value, int integer)
	{
		this.squares=value;
		this.numInRoute=(byte)integer;
		this.height=4;
		this.width=4;
		long squareCopy=squares;
		for(int i=0; i<width*height; i++)
		{
			byte val=(byte)(squareCopy&0x0F);
			squareCopy>>>=4;
			if(val==0)
				emptyPosition=new Point((byte)(i/width), (byte)(i%width));

		}

	}
	/**
	 * Creates an ixj square with the elements in random spots.
	 */
	public PatternDBSlidingSquareUsingLong(byte height, byte width)
	{
		// Initial declarations.
		//previouslyVisited=new ArrayList<DIRECTION>();
		numInRoute=1;
		this.height=height;
		this.width=width;
		byte [] vals={1,2,3,4,6,6,6,6,6,6,6,6,6,6,6,0};
		squares=toLong(vals);

		for(int i=0; i<height*width; i++)
		{
			if(vals[i]==0)
			{
				emptyPosition=new Point((byte)(i/width), (byte)(i%width));
				System.out.println(emptyPosition);
			}
		}
		
	}
	
	/**
	 * Creates an ixj square with the elements in random spots.
	 */
	public PatternDBSlidingSquareUsingLong(byte height, byte width, byte[]vals)
	{
		// Initial declarations.
		//previouslyVisited=new ArrayList<DIRECTION>();
		numInRoute=1;
		this.height=height;
		this.width=width;
		squares=toLong(vals);
		for(int i=0; i<height*width; i++)
		{
			if(vals[i]==0)
			{
				emptyPosition=new Point((byte)(i/width), (byte)(i%width));
				System.out.println(emptyPosition);
			}
		}
		
	}
	
	/**
	 * Duplicate a Sliding square.
	 * @param ms
	 */
	protected PatternDBSlidingSquareUsingLong(PatternDBSlidingSquareUsingLong ms)
	{
		numInRoute=(byte) (ms.numInRoute+1);
		this.height=ms.height;
		this.width=ms.width;
		emptyPosition=new Point(ms.emptyPosition);
		squares=ms.squares;
	}
	
	/**
	 * Check to see if the fifteen square is in the proper order.
	 * @return
	 */
	public boolean isSolved()
	{
		return false;
	}
	public byte numInRoute()
	{
		return numInRoute;
	}

	/**
	 * Creates a new Square and moves it in direction d.
	 * The new square will have the current square in the previously visited list.
	 * This is done to prevent making a duplicate of the previous square twice.
	 * If the move cannot be made, returns null.
	 * @param d
	 * @return
	 */
	public PatternDBSlidingSquareUsingLong move(DIRECTION d)
	{
		if(canMove(d))
		{
			Point newPos=null;
			newPos=new Point(emptyPosition);
			newPos.move(d);
			PatternDBSlidingSquareUsingLong ms=new PatternDBSlidingSquareUsingLong(this);
			//ms.previouslyVisited.add(d);
			ms.swap(emptyPosition, newPos);
			return ms;
		}
		else
			return null;
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		long squareCopy=squares;
		for(int i=0; i<width*height; i++)
		{
			byte val=(byte)(squareCopy&0x0F);
			squareCopy>>>=4;
			sb.append(val);
			sb.append('\t');
			if((i+1)%width==0)
				sb.append("\n");
		}
		return sb.toString();
	}
	
	/*public List<DIRECTION> getPreviouslyVisited()
	{
		return previouslyVisited;
	}*/
	/**
	 * This is protected to allow the Sliding Square node to access it.
	 * @param d
	 * @return
	 */
	protected boolean canMove(DIRECTION d)
	{
		switch(d)
		{
		case DOWN:
			if(emptyPosition.heightPos==height-1)
				return false;
			break;
		case LEFT:
			if(emptyPosition.widthPos==0)
				return false;
			break;
		case RIGHT:
			if(emptyPosition.widthPos==width-1)
				return false;
			break;
		case UP:
			if(emptyPosition.heightPos==0)
				return false;
			break;			
		}
		return true;
	}
	
	private void assignSquareVal(byte pos, byte val)
	{
		long bitPosVal=(long)15<<(pos*4);

		bitPosVal^=-1;
		squares&=bitPosVal;
		squares|=(long)val<<((pos)*4);
	}
	public byte getSquareVal(byte pos)
	{
		int numSpaces=width*height;
		return (byte)((squares<<(numSpaces*4-(pos+1)*4))>>>((numSpaces-1)*4));
	}
	/**
	 * This function swaps two points in the magic square.  The only point that
	 * can be swapped is the "emptyPosition".
	 * @param one
	 * @param two
	 */
	private void swap(Point one, Point two)
	{
		byte onePos=one.getPos();
		byte twoPos=two.getPos();
		byte temp= getSquareVal(onePos);
		byte temp2=getSquareVal(twoPos);
		if(temp!=0 && temp2!=0)
		{
			//The square can't be swapped.
			return;
		}
		assignSquareVal(onePos, temp2);
		assignSquareVal(twoPos, temp);
		if(temp==0)
		{
			emptyPosition=two;
		}
		else if(temp2==0)
		{
			emptyPosition=one;
		}
	}


	private class Point
	{
		public Point(Point p)
		{
			this(p.heightPos, p.widthPos);
		}
		public Point(byte heightPos, byte widthPos)
		{
			this.widthPos=widthPos;
			this.heightPos=heightPos;
		}
		public void move(DIRECTION d)
		{
			switch(d)
			{
			case DOWN:
				this.heightPos++;
				break;
			case LEFT:
				this.widthPos--;
				break;
			case RIGHT:
				this.widthPos++;
				break;
			case UP:
				this.heightPos--;
				break;			
			}
		}
		public byte getPos()
		{
			return (byte) (heightPos*width+widthPos);
		}
		public String toString()
		{
			return "i: "+widthPos +" j: "+heightPos;
		}
		private byte widthPos;
		private byte heightPos;
	}
	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (squares ^ (squares >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatternDBSlidingSquareUsingLong other = (PatternDBSlidingSquareUsingLong) obj;
		if (squares != other.squares)
			return false;
		return true;
	}

	public String getCommaDelimited()
	{
			StringBuilder sb=new StringBuilder();
			int numSpaces=width*height;
			for(byte i=0; i<numSpaces; i++)
			{
				byte cell=this.getSquareVal(i);
				sb.append(cell);
				if(i<numSpaces-1)
				sb.append(',');
			}
			return sb.toString();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}


	public int countInversions()
	{
		int numInversions=0;
		int numSpaces=width*height;
		for(byte i=0; i<numSpaces; i++)
		{
			if(this.getSquareVal(i)==0) continue;
			for(byte j=(byte) (i+1); j<numSpaces; j++)
			{
				if(this.getSquareVal(j)==0) continue;
				if(this.getSquareVal(j)<this.getSquareVal(i))
					numInversions++;
			}
		}
		return numInversions;
	}
	//https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
	public boolean isSolvable()
	{
		if(width%2!=0)
		{
			return isInversionsEven();
		}
		else
		{
			//%2 means on an odd row since it starts from 0 as opposed to 1.
			if(emptyPosition.heightPos%2==0)
			{
				return !isInversionsEven();
			}
			else
				return isInversionsEven();
		}
	}
	public boolean isInversionsEven()
	{
		return countInversions()%2==0;
	}
	
	private long toLong(byte[] squares)
	{
		long sum=0;
		int index=0;
		for(byte v:squares)
		{
			sum+=(long)v<<index;
			index+=4;
		}
		return sum;
	}
	/**
	 * Not valid for larger than 16 squares since a long can only hold 2^64 values.
	 * @return
	 */
	public long toLong()
	{
		return squares;
	}
	public static void printBytes(byte[] vals)
	{
		for(byte val:vals)
			printByte(val);
		System.out.println();
	}
	public static void printByte(byte val)
	{
		byte valCopy=val;
		
		for(int i=0; i<8; i++)
		{
			System.out.print((valCopy>>7)&0x1);
			valCopy<<=1;
		}
		System.out.print(" ");
		
	}
	public static byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
	public void printLong(long val)
	{
		printBytes(longToBytes(val));
	}
}