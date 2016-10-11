import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Main {
	private static Set<SlidingSquare> visitedSquares=new HashSet<SlidingSquare>();
	
	/**
	 * Takes in a sliding square and returns the directions
	 * taken to solve it.
	 * @param sq
	 * @return
	 */
	public static List<SlidingSquare.DIRECTION> solveSlidingSquare_BruteForce(SlidingSquare sq)
	{
		Queue<SlidingSquare> q=new LinkedList<SlidingSquare>();
		q.add(sq);
		while(!q.isEmpty())
		{
			SlidingSquare ms=q.poll();
			for(SlidingSquare.DIRECTION d : SlidingSquare.DIRECTION.values())
			{
				SlidingSquare moved=ms.move(d);
				if(moved==null) 
					continue;
				if(!moved.isSolved())
				{
					q.add(moved);
				}
				else
					return moved.getPreviouslyVisited();
			}
		}
		return null;
	}
	
	/**
	 * Takes in a sliding square and returns the directions
	 * taken to solve it.
	 * @param sq
	 * @return
	 */
	public static List<SlidingSquare.DIRECTION> solveSlidingSquare_BFS(SlidingSquare sq)
	{
		Set<SlidingSquare> visitedSquares=new HashSet<SlidingSquare>();
		Queue<SlidingSquare> q=new LinkedList<SlidingSquare>();
		q.add(sq);
		while(!q.isEmpty())
		{
			SlidingSquare ms=q.poll();
			for(SlidingSquare.DIRECTION d : SlidingSquare.DIRECTION.values())
			{
				SlidingSquare moved=ms.move(d);
				if(moved==null) 
					continue;
				if(!moved.isSolved())
				{
					if(!visitedSquares.contains(moved))
					{
						visitedSquares.add(moved);
						q.add(moved);
					}
				}
				else
					return moved.getPreviouslyVisited();
			}
		}
		return null;
	}
	public static void main(String[] args) {
		SlidingSquare ms;
		Queue<SlidingSquare> q=new LinkedList<SlidingSquare>();
		q.add(new SlidingSquare((byte)3,(byte)3));
		System.out.println(q.peek());

		while(!q.isEmpty())
		{
			ms=q.poll();
			visitedSquares.add(ms);
			for(SlidingSquare.DIRECTION d:SlidingSquare.DIRECTION.values())
			{
				SlidingSquare moved=ms.move(d);
				if(moved==null) 
					continue;
				if(!moved.isSolved())
				{
					if(!visitedSquares.contains(moved))
						q.add(moved);
				}
				else
				{
					System.out.println(moved);
					System.out.println("Index: " + moved.getPreviouslyVisited().size());
					return;
				}
			}
			
		}
		System.out.println("Error");

	}

}
