import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main2 {
	private static Set<SlidingSquare> visitedSquares=new HashSet<SlidingSquare>();

	public static void main(String[] args) {
		SlidingSquare ms;
		Queue<SlidingSquare> q=new LinkedList<SlidingSquare>();
		q.add(new SlidingSquare((byte)4,(byte)4));
		System.out.println(q.peek());

		while(!q.isEmpty())
		{
			ms=q.poll();
			visitedSquares.add(ms);
			for(SlidingSquare.DIRECTION d:SlidingSquare.DIRECTION.values())
			{
				SlidingSquare moved=ms.move(d);
				if(moved==null || !moved.isSolved())
				{
					if(moved!=null)
					{
						if(!visitedSquares.contains(moved))
						q.add(moved);
					}
				}
				else
				{
					System.out.println(moved);
					moved.isSolved();
					System.out.println("Index: " + moved.getPreviouslyVisited().size());
					return;
				}
			}
			
		}
		System.out.println("Solution could not be found.");
	}

}
