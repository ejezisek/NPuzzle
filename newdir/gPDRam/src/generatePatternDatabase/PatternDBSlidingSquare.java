package generatePatternDatabase;
import java.util.Arrays;

/**
 * This class creates a ixj square puzzle problem, with random starting squares.
 * The starting squares are found using a Knuth-Fischer-Yates shuffle.  
 * Please view the following blog post (http://blog.codinghorror.com/the-danger-of-naivete/)
 * for information on potential mistakes in the Shuffle.
 * The generated square should have a 50% chance of being solvable.
 */
public class PatternDBSlidingSquare
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
	private byte [] squares;

	private byte height;
	private byte width;
	private byte numInRoute;
	public byte [] getSquares()
	{
		return squares;
	}
	//This is the only position that can be moved.
	private Point emptyPosition;
	private PatternDBSlidingSquare parent;
	

	/**
	 * Creates an ixj square with the elements in random spots.
	 */
	public PatternDBSlidingSquare(byte height, byte width)
	{
		// Initial declarations.
		//previouslyVisited=new ArrayList<DIRECTION>();
		numInRoute=1;
		this.height=height;
		this.width=width;
		byte [] vals={1,2,3,4,5,-1,-1,-1,9,-1,-1,-1,13,-1,-1,0};
		squares=vals;

		for(int i=0; i<height*width; i++)
		{
			if(squares[i]==0)
			{
				emptyPosition=new Point(i/width, i%width);
				System.out.println(emptyPosition);
			}
		}
		
	}
	
	/**
	 * Duplicate a Sliding square.
	 * @param ms
	 */
	protected PatternDBSlidingSquare(PatternDBSlidingSquare ms)
	{
		numInRoute=(byte) (ms.numInRoute+1);
		this.height=ms.height;
		this.width=ms.width;
		emptyPosition=new Point(ms.emptyPosition);
		squares=ms.squares.clone();
	}
	
	/**
	 * Check to see if the fifteen square is in the proper order.
	 * @return
	 */
	public boolean isSolved()
	{
		int r=1;
		for(byte cell : squares)
			if(r==cell || cell==0)
				r++;
			else
				return false;
		return true;
	}
	public byte numInRoute()
	{
		return numInRoute;
	}
	public PatternDBSlidingSquare [] getRoute()
	{
		PatternDBSlidingSquare[] slidingSquares=new PatternDBSlidingSquare[numInRoute()];
		return getRoute(slidingSquares, slidingSquares.length-1);
	}
	private PatternDBSlidingSquare [] getRoute(PatternDBSlidingSquare [] holder, int index)
	{
		holder[index]=this;
		if(index!=0)
			return parent.getRoute(holder, index-1);
		else
			return holder;
	}

	/**
	 * Creates a new Square and moves it in direction d.
	 * The new square will have the current square in the previously visited list.
	 * This is done to prevent making a duplicate of the previous square twice.
	 * If the move cannot be made, returns null.
	 * @param d
	 * @return
	 */
	public PatternDBSlidingSquare move(DIRECTION d)
	{
		if(canMove(d))
		{
			Point newPos=null;
			newPos=new Point(emptyPosition);
			newPos.move(d);
			PatternDBSlidingSquare ms=new PatternDBSlidingSquare(this);
			//ms.previouslyVisited.add(d);
			ms.swap(emptyPosition, newPos);
			ms.parent=this;
			return ms;
		}
		else
			return null;
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		int i=0;
		for(byte cell : squares)
		{
			sb.append(cell);
			sb.append('\t');
			if((i+1)%width==0)
				sb.append("\n");
			i++;
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
	

	
	/**
	 * This function swaps two points in the magic square.  The only point that
	 * can be swapped is the "emptyPosition".
	 * @param one
	 * @param two
	 */
	private void swap(Point one, Point two)
	{
		
		byte temp=squares[one.getPos()];
		//The square can't be swapped.
		if(temp!=0 && squares[two.getPos()]!=0)
			return;
		squares[one.getPos()]=squares[two.getPos()];
		squares[two.getPos()]=temp;
		if(temp==0)
		{
			emptyPosition=two;
		}
		else if(squares[one.getPos()]==0)
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
		public Point(int heightPos, int widthPos)
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
		public int getPos()
		{
			return heightPos*width+widthPos;
		}
		public String toString()
		{
			return "i: "+widthPos +" j: "+heightPos;
		}
		private int widthPos;
		private int heightPos;
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(squares);
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
		PatternDBSlidingSquare other = (PatternDBSlidingSquare) obj;
		if (!Arrays.equals(squares, other.squares))
			return false;
		return true;
	}

	public String getCommaDelimited()
	{
			StringBuilder sb=new StringBuilder();

			for(byte cell:squares)
			{
				sb.append(cell);
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

	public PatternDBSlidingSquare getParent() {
		return parent;
	}

	public void setParent(PatternDBSlidingSquare parent2) {
		this.parent=parent2;
	}
	public int countInversions()
	{
		byte [] newSquares=squares.clone();
		int numInversions=0;
		for(byte i=0; i<newSquares.length; i++)
		{
			if(newSquares[i]==0) continue;
			for(byte j=(byte) (i+1); j<newSquares.length; j++)
			{
				if(newSquares[j]==0) continue;
				if(newSquares[j]<newSquares[i])
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
	// Uses the formula from http://cseweb.ucsd.edu/~ccalabro/essays/15_puzzle.pdf
	// I don't think this is correct.
	public boolean isSolvableNotWorking()
	{
		int s=0;
		byte [] newSquares=squares.clone();
		for(byte i=0; i<newSquares.length;)
		{
			if(newSquares[i]!=i)
			{
				byte temp=newSquares[i];
				newSquares[i]=i;
				i=temp;
				s=1-s;
			}
			else
				i++;
		}
		return s==0;
	}
	private long toLong(byte[] squares)
	{
		long sum=0;
		int index=0;
		for(byte v:squares)
		{
			long multiple=1<<index;
			sum+=v*multiple;
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
		long sum=0;
		int index=0;
		for(byte v:squares)
		{
			long multiple=1<<index;
			sum+=v*multiple;
			index+=4;
		}
		return sum;
	}
}