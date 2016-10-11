package generatePatternDatabase;

import java.io.IOException;

import org.junit.Test;

public class Access9Square {

	@Test
	public void test() throws IOException {
		String filename = "/home/edward/hardDrive_2/Research/generatePatternDBFiles/9Squareresults.out";
		int [] availableValues={1,2,3,4,5,6,7,8,0};
		LargeByteArray l=new LargeByteArray(filename);

    	PatternDBSlidingSquareCollection tree=new PatternDBSlidingSquareCollection(availableValues, (byte)3, (byte)3,l);
    	byte[] dest={3,1,7,
    				2,8,4,
    				0,6,5};
    	System.out.println(tree.get(dest)-1);
    }

}
