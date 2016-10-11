package generatePatternDatabase;


import java.io.IOException;

import ed.util.algorithms.Action;

public class NineSquareMain {
	public static void main(String[] args) throws IOException {

		byte[] vals={1,2,3,4,5,6,7,8,0};
		int [] availableValues={1,2,3,4,5,6,7,8,0};

		PatternDBSlidingSquareUsingLong startingPos=new PatternDBSlidingSquareUsingLong((byte)3,(byte)3, vals, availableValues);
		System.out.println(startingPos);
		PatternDBSlidingSquareNode pdssn=new PatternDBSlidingSquareNode(startingPos);
	//	System.out.println(startingPos);
		Action<PatternDBSlidingSquareNode, Boolean> action=new Action<PatternDBSlidingSquareNode, Boolean>()
		{
			int i=0;
			@Override
			public void performAction(PatternDBSlidingSquareNode node) {
				if(i++%1000000==0)
					System.out.println(i);
			}

			@Override
			public boolean complete() {
				return false;
			}

			@Override
			public Boolean getValue() {
				return null;
			}

		};
		String filename = "/home/edward/hardDrive_2/Research/generatePatternDBFiles/9Squareresults.out";

		FifteenSquareBFS.BFS(pdssn, action, availableValues, filename);
	}

}
