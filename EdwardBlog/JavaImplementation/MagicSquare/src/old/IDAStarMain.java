package old;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ed.util.algorithms.Common;
import ed.util.algorithms.IDAStar;

public class IDAStarMain {
	public static class ManhattanDistanceWithLinearConflict implements Common.Heuristic<SlidingSquareNodeOld>
	{			
		List<Common.Heuristic<SlidingSquareNodeOld>> heuristics;
		private Common.Heuristic<SlidingSquareNodeOld> ManhattanDistance;
		private Common.Heuristic<SlidingSquareNodeOld> LinearConflict;
		
		public ManhattanDistanceWithLinearConflict()
		{
			heuristics=new ArrayList<Common.Heuristic<SlidingSquareNodeOld>>();
			LinearConflict=(new LinearConflict());
			ManhattanDistance=(new ManhattanDistanceHeuristic());
		}
		@Override
		public int getHeuristic(SlidingSquareNodeOld item) {
			return ManhattanDistance.getHeuristic(item)+LinearConflict.getHeuristic(item);
		}		
	}
	public static class LinearConflict implements Common.Heuristic<SlidingSquareNodeOld>
	{
		private int numLinearConflict(int i, byte [] squares, int width, int height)
		{
			//We define the starting row ie 0/width as 1.
			int currentRow=i/width+1;
			int numConflict=0;
			if(squares[i]==0)
				return 0;
			if(squares[i]<=currentRow*width && squares[i]>(currentRow-1)*width)
			{
				for(int j=i+1; j<currentRow*width; j++)
				{
					if(squares[j]<=currentRow*width && squares[j]>(currentRow-1)*width && squares[j]!=0)
					{
						if(squares[j]<squares[i])
							numConflict++;
					}
				}
			}
			int currentColumn=i%width;
			if((squares[i]-1)%width==currentColumn)
			{
				for(int j=i+width; j<width*height; j+=width)
				{
					if((squares[j]-1)%width==currentColumn && squares[j]!=0)
					{
						if(squares[j]<squares[i])
							numConflict++;
					}
				}
			}
			return numConflict;
		}
		@Override
		public int getHeuristic(SlidingSquareNodeOld item) {
			byte [] squares=item.getSquares();
			int width=item.getWidth();
			int height=item.getHeight();
			int numConflict=0;
			
			for(int i=0; i<squares.length; i++)
			{
				numConflict+=numLinearConflict(i, squares, width, height);
			}
			//Each conflict causes at a minimum of two extra moves to be made.
			return numConflict*2;
		}
		
	}
	public static class ManhattanDistanceHeuristic implements Common.Heuristic<SlidingSquareNodeOld>
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
		public int getHeuristic(SlidingSquareNodeOld item) {
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
		//byte[]vals={11,4,0,8,9,7,10,6,14,15,2,1,5,12,3,13};
		//byte [] vals={3, 10, 12, 13, 2, 14, 6, 11, 9, 8 , 15,0, 5,7,1,4};
		byte [] vals={11,1,5,4,12,6,13,8,10,9,15,2,0,7,14,3};
		//SlidingSquare sq=new SlidingSquare((byte)4,(byte)4);
		SlidingSquareOld sq=new SlidingSquareOld((byte)4,(byte)4, vals);
		//SlidingSquareNode sq=new SlidingSquareNode((byte)4,(byte)4);
		SlidingSquareNodeOld b=new SlidingSquareNodeOld(sq);
		System.out.println(b);
		System.out.println(b.getCommaDelimited());

		if(!b.isSolvable())
		{
			System.out.println("NOT SOLVABLE");
			return;
		}
		Common.Heuristic<SlidingSquareNodeOld> h=new ManhattanDistanceHeuristic();
		System.out.println(h.getHeuristic(b));
		List<SlidingSquareNodeOld> res=IDAStar.IDA_Star(b, new ManhattanDistanceWithLinearConflict(), new Common.Goal<SlidingSquareNodeOld>() {
			@Override
			public boolean isGoal(SlidingSquareNodeOld n) {
				// TODO Auto-generated method stub
				return n.isSolved();
			}

		});
		if(res==null)
			System.out.println("NOT FOUND");
		else
		{
			System.out.println(res.size());
			for(SlidingSquareNodeOld v:res)
				System.out.println(v);
		}
		
		System.out.println(new Date());
		System.out.println(IDAStar.nodeNum);
	}

}
