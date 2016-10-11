import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ed.util.algorithms.Common;
import ed.util.algorithms.IDAStar;

public class IDAStarMain {
	public static class ManhattanDistanceWithLinearConflict implements Common.Heuristic<SlidingSquareNode>
	{
		@Override
		public int getHeuristic(SlidingSquareNode item) {
			List<Common.Heuristic<SlidingSquareNode>> heuristics=new ArrayList<Common.Heuristic<SlidingSquareNode>>();
			heuristics.add(new LinearConflict());
			heuristics.add(new ManhattanDistanceHeuristic());
			int heuristicValue=0;
			for(Common.Heuristic<SlidingSquareNode> h:heuristics)
			{
				heuristicValue+=h.getHeuristic(item);
			}
			return heuristicValue;
		}		
	}
	public static class LinearConflict implements Common.Heuristic<SlidingSquareNode>
	{
		private int numLinearConflict(int i, byte [] squares, int width)
		{
			//We define the starting row ie 0/width as 1.
			int currentRow=i/width+1;
			int numConflict=0;
			if(squares[i]<=currentRow*width && squares[i]>(currentRow-1)*width)
			{
				for(int j=i+1; j<currentRow*width; j++)
				{
					if(squares[j]<=currentRow*width && squares[j]>(currentRow-1)*width)
					{
						if(squares[j]<squares[i])
							numConflict++;
					}
				}
			}
			return numConflict;
		}
		@Override
		public int getHeuristic(SlidingSquareNode item) {
			byte [] squares=item.getSquares();
			int width=item.getWidth();
			int numConflict=0;
			for(int i=0; i<squares.length; i++)
			{
				numConflict+=numLinearConflict(i, squares, width);
			}
			//Each conflict causes at a minimum of two extra moves to be made.
			return numConflict*2;
		}
		
	}
	public static class ManhattanDistanceHeuristic implements Common.Heuristic<SlidingSquareNode>
	{
		private int getDifference(int i, int j, int value, int columns, int height)
		{
			//Convert the value to i and j coordinates.
			int valuei;
			int valuej;
			if(value!=0)
			{
				valuei=(value-1)/columns;
				valuej=(value-1)%(columns);
			}
			else
			{
				return 0;
			}
			return Math.abs(valuei-i)+Math.abs(valuej-j);
		}
		@Override
		public int getHeuristic(SlidingSquareNode item) {
			byte[] squares=item.getSquares();
			int heuristicsum=0;
			int height=item.getHeight();
			int width=item.getWidth();

			for(int i=0; i<squares.length; i++)
			{
				heuristicsum+=getDifference(i/width,i%width, squares[i], width, height);
			}
			return heuristicsum;
		}	
	}
	
	public static void main(String[] args) {
		System.out.println(new Date());
		byte [] vals={3, 10, 12, 13, 2, 14, 6, 11, 9, 8 , 15,0, 5,7,1,4};
		SlidingSquareNode b=new SlidingSquareNode((byte)4,(byte)4);
		System.out.println(b);

		System.out.println(b.getCommaDelimited());
		Common.Heuristic<SlidingSquareNode> h=new ManhattanDistanceHeuristic();
		System.out.println(h.getHeuristic(b));
		List<SlidingSquareNode> res=IDAStar.IDA_Star(b, new ManhattanDistanceWithLinearConflict(), new Common.Goal<SlidingSquareNode>() {
			@Override
			public boolean isGoal(SlidingSquareNode n) {
				// TODO Auto-generated method stub
				return n.isSolved();
			}

		});
		if(res==null)
		{
		}
		else
		{
			System.out.println(res.size());
			for(SlidingSquareNode i:res)
				System.out.println(i);

		}
		System.out.println(new Date());
	}

}
