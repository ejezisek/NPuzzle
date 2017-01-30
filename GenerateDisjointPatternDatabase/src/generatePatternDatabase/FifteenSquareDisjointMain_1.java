package generatePatternDatabase;


import java.io.IOException;

import ed.util.algorithms.Action;

public class FifteenSquareDisjointMain_1 {


    static long index=0;

    public static String defaultFileDir="/home/ejezisek/Projects/generatePatternDBFiles";
	public static void main(String[] args) {

		byte[] vals={1,2,3,4,5,6,6,6,7,6,6,6,8,6,6,0};
		//		byte[] vals={1,2,3,4,5,6,7,8,0};

    	int[] availableValues={0,1,2,3,4,5,7,8};

		PatternDBSlidingSquareUsingLong startingPos=new PatternDBSlidingSquareUsingLong((byte)4,(byte)4, vals, availableValues);
		System.out.println(startingPos);
		PatternDBSlidingSquareNode pdssn=new PatternDBSlidingSquareNode(startingPos);
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
		String filename = defaultFileDir+"/15Squareresults_disjoint_1.out";
		try {
			FifteenSquareBFS.BFS(pdssn, action, availableValues, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
