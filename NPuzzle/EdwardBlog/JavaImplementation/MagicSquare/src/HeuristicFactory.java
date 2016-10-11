import java.io.IOException;

import ed.util.algorithms.Common;

public enum HeuristicFactory {
	MANHATTANDISTANCE, MANHATTANDISTANCEWITHLINEARCONFLICT, PATTERNDATABASE, PATTERNDATABASEWITHMANHATTANDISTANCEWITHLINEARCONFLICT;
	private HeuristicFactory()
	{
		
	}
	public Common.Heuristic<SlidingSquareNode> get()
	{
		switch(this)
		{
		case MANHATTANDISTANCE:
			return new IDAStarMain.ManhattanDistanceHeuristic();
		case MANHATTANDISTANCEWITHLINEARCONFLICT:
			return new IDAStarMain.ManhattanDistanceWithLinearConflict();
		case PATTERNDATABASE:
		{
			String fifteenFile="/home/edward/Research/generatePatternDBFiles/15Squareresults.out";
			String nineFile="/home/edward/Research/generatePatternDBFiles/9Squareresults.out";
			IDAStarMain.NineSquarePatternDatabase_Byte n;
			try {
				n = new IDAStarMain.NineSquarePatternDatabase_Byte(IDAStarMain.getBytesFromFile(nineFile));
				return new IDAStarMain.FifteenSquarePatternDatabase_Byte(IDAStarMain.getBytesFromFile(fifteenFile), n);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		case PATTERNDATABASEWITHMANHATTANDISTANCEWITHLINEARCONFLICT:
			return new IDAStarMain.PDWithManhattanDistanceWithLinearConflict();
			
		default:
			return null;
		
		}
	}
}
