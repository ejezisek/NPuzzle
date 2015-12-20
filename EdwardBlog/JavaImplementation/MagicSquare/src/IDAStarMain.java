import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ed.util.algorithms.Common;
import ed.util.algorithms.IDAStarNew;
import ed.util.algorithms.Node;

public class IDAStarMain {
	public static class PDWithManhattanDistanceWithLinearConflict implements Common.Heuristic<SlidingSquareNode>
	{			
		private Common.Heuristic<SlidingSquareNode> ManhattanDistanceWithLinearConflict;
		private Common.Heuristic<SlidingSquareNode> patternDatabase;
		
		public PDWithManhattanDistanceWithLinearConflict()
		{
			ManhattanDistanceWithLinearConflict=HeuristicFactory.MANHATTANDISTANCEWITHLINEARCONFLICT.get();
			patternDatabase=HeuristicFactory.PATTERNDATABASE.get();
		}
		@Override
		public int getHeuristic(SlidingSquareNode item) {
			return Math.max(ManhattanDistanceWithLinearConflict.getHeuristic(item), patternDatabase.getHeuristic(item));
		}		
	}
	public static class ManhattanDistanceWithLinearConflict implements Common.Heuristic<SlidingSquareNode>
	{			
		List<Common.Heuristic<SlidingSquareNode>> heuristics;
		private Common.Heuristic<SlidingSquareNode> ManhattanDistance;
		private Common.Heuristic<SlidingSquareNode> LinearConflict;
		
		public ManhattanDistanceWithLinearConflict()
		{
			heuristics=new ArrayList<Common.Heuristic<SlidingSquareNode>>();
			LinearConflict=(new LinearConflict());
			ManhattanDistance=(new ManhattanDistanceHeuristic());
		}
		@Override
		public int getHeuristic(SlidingSquareNode item) {
			return ManhattanDistance.getHeuristic(item)+LinearConflict.getHeuristic(item);
		}		
	}
	public static class LinearConflict implements Common.Heuristic<SlidingSquareNode>
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
		public int getHeuristic(SlidingSquareNode item) {
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
	public static class PatternDBHeuristic<T extends Node<T>> implements Common.Heuristic<T>
	{
		private PatternDatabase<T> pd;
		public PatternDBHeuristic(PatternDatabase<T> pd)
		{
			this.pd=pd;
		}
		@Override
		public int getHeuristic(T item) {
			if(pd.getHeuristic(item)!=0)
				return pd.getHeuristic(item);
			else
				return pd.getResult(item);
		}
		public static interface PatternDatabase <T extends Node<T>> extends Common.Heuristic<T>
		{
			public int getHeuristic(T item);
			public int getResult(T item);
		}
	}
	public static byte[] getBytesFromFile(String filename) throws IOException
	{
		RandomAccessFile f=new RandomAccessFile(filename, "r");
		byte [] bytes=new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return bytes;
	}
	public static class NineSquarePatternDatabase_Byte implements PatternDBHeuristic.PatternDatabase<SlidingSquareNode>
	{
		private PatternDBSlidingSquareCollection coll;

		public NineSquarePatternDatabase_Byte(byte [] values)
		{
			int[] arr={1,2,3,4,5,6,7,8,0};
			coll=new PatternDBSlidingSquareCollection(arr, (byte)3, (byte)3, values); 
		}

		@Override
		public int getHeuristic(SlidingSquareNode item) {
			return getResult(item);
		}

		@Override
		public int getResult(SlidingSquareNode item) {
			return coll.get(item.getSquares());
		}	

	}
	public static class FifteenSquarePatternDatabase_Byte implements PatternDBHeuristic.PatternDatabase<SlidingSquareNode>
	{
		private NineSquarePatternDatabase_Byte ns;
		private PatternDBSlidingSquareCollection coll;

		public FifteenSquarePatternDatabase_Byte(byte [] values, NineSquarePatternDatabase_Byte ns)
		{
			this.ns=ns;
			int[] availableValues={0,1,2,3,4,5,9,13};
			coll=new PatternDBSlidingSquareCollection(availableValues, (byte)4, (byte)4, values);
		}
		
		@Override
		public int getHeuristic(SlidingSquareNode item) {
			int index=coll.get(item.getSquares());
			if(index==0)
			{
				return getResult(item);
			}
			else return index;
		}

		@Override
		public int getResult(SlidingSquareNode item) {
			// TODO Auto-generated method stub
			return ns.getResult(item);
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
		long currTimeMilis=System.currentTimeMillis();
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date());
		//byte[]vals={11,4,0,8,9,7,10,6,14,15,2,1,5,12,3,13};
		byte [] vals={3, 10, 12, 13, 2, 14, 6, 11, 9, 8 , 15,0, 5,7,1,4};
		//byte [] vals={11,1,5,4,12,6,13,8,10,9,15,2,0,7,14,3};
		SlidingSquare sq=new SlidingSquare((byte)4,(byte)4);
		//SlidingSquare sq=new SlidingSquare((byte)4,(byte)4, vals);
		//SlidingSquare sq=new SlidingSquare((byte)4,(byte)4);
		SlidingSquareNode b=new SlidingSquareNode(sq);
		System.out.println(b);
		System.out.println(b.getCommaDelimited());

		if(!b.isSolvable())
		{
			System.out.println("NOT SOLVABLE");
			return;
		}
		Common.Heuristic<SlidingSquareNode> h=new ManhattanDistanceHeuristic();
		//System.out.println(h.getHeuristic(b));
		SlidingSquareNode[] res=IDAStarNew.IDA_Star(b, HeuristicFactory.PATTERNDATABASEWITHMANHATTANDISTANCEWITHLINEARCONFLICT.get(), new Common.Goal<SlidingSquareNode>() {
			@Override
			public boolean isGoal(SlidingSquareNode n) {
				// TODO Auto-generated method stub
				return n.isSolved();
			}

		});
		if(res==null)
			System.out.println("NOT FOUND");
		else
		{
			System.out.println(res.length);
			for(SlidingSquareNode v:res)
				System.out.println(v);
		}
		System.out.println(new Date());
		System.out.println(IDAStarNew.nodeNum);
		System.out.println((System.currentTimeMillis()-currTimeMilis)/1000.0);
	}

}
