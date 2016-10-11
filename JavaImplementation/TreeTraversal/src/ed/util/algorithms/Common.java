package ed.util.algorithms;

public class Common {
	public static interface Heuristic <T>
	{
		public int getHeuristic(T item);
	}
	public static interface Goal <T>
	{
		public boolean isGoal(T n);
	}
	
}
