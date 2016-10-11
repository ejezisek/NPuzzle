import java.util.Arrays;
import java.util.List;

/**
 * This class creates a ixj square puzzle problem, with random starting squares.
 * The starting squares are found using a Knuth-Fischer-Yates shuffle.  
 * Please view the following blog post (http://blog.codinghorror.com/the-danger-of-naivete/)
 * for information on potential mistakes in the Shuffle.
 * The generated square should have a 50% chance of being solvable.
 */
public class SlidingSquare
{
	//The valid directions the square can be moved.
	public static enum DIRECTION {UP, LEFT, DOWN, RIGHT};
	
	//This is where the data for the fifteen square is stored.
	private byte [] squares;
	
	private byte height;
	private byte width;
	
	public byte [] getSquares()
	{
		return squares;
	}
	//This is the only position that can be moved.
	private Point emptyPosition;
	
	/**
	 * This has all the previously visited squares so that we can display
	 *  which choices were made to solve the magic square.
	 */
	private List<DIRECTION> previouslyVisited;

	/**
	 * Creates an ixj square with the elements in random spots.
	 */
	public SlidingSquare(byte height, byte width)
	{
		// Initial declarations.
		//previouslyVisited=new ArrayList<DIRECTION>();
		this.height=height;
		this.width=width;
		squares=new byte[height*width];
		byte [] randomValues=new byte[height*width];
		//Create the elements.
		for(byte i=0; i<randomValues.length; i++)
		{
			randomValues[i]=i;
		}
		
		//Shuffle the elements.
		for(int i=randomValues.length-1; i>0; i--)
		{
			byte val=(byte)(Math.random()*(i+1));
			byte temp=randomValues[i];
			randomValues[i]=randomValues[val];
			randomValues[val]=temp;
		}
		for(int i=0; i<height*width; i++)
		{
			squares[i]=randomValues[i];
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
	protected SlidingSquare(SlidingSquare ms)
	{
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
	
	/**
	 * Creates a new Square and moves it in direction d.
	 * The new square will have the current square in the previously visited list.
	 * This is done to prevent making a duplicate of the previous square twice.
	 * If the move cannot be made, returns null.
	 * @param d
	 * @return
	 */
	public SlidingSquare move(DIRECTION d)
	{
		if(canMove(d))
		{
			Point newPos=null;
			newPos=new Point(emptyPosition);
			newPos.move(d);
			SlidingSquare ms=new SlidingSquare(this);
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
	
	public List<DIRECTION> getPreviouslyVisited()
	{
		return previouslyVisited;
	}
	
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
			return "Width: "+widthPos +" Height: "+heightPos;
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
		SlidingSquare other = (SlidingSquare) obj;
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
		// TODO Auto-generated method stub
		return height;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

}