import org.junit.Test;

import junit.framework.Assert;

public class SlidingSquareTest {

	@Test
	public void test() {
		SlidingSquare s=new SlidingSquare((byte)4,(byte)4);
		System.out.println(s);
		s=s.move(SlidingSquare.DIRECTION.LEFT);
		System.out.println(s);
	}
	@Test
	public void inversionTest()
	{

		byte [] squares={13,10,11,6,5,7,4,8,1,12,14,9, 3,15,2,0};
		SlidingSquare s=new SlidingSquare((byte)4,(byte)4, squares);
		System.out.println(s);
		Assert.assertEquals(59, s.countInversions());
		Assert.assertEquals(false, s.isSolvable());

		byte [] squares0={1,3,2,4,8,6,7,5,12,10,11,9,13,14,15, 0};
		s=new SlidingSquare((byte)4,(byte)4, squares0);
		System.out.println(s.countInversions());

		Assert.assertEquals(false, s.isSolvable());
		byte [] squares2={13,10,11,6,5,7,4,8,1,12,14,0, 3,15,2,9};
		s=new SlidingSquare((byte)4,(byte)4, squares2);
		System.out.println(s.countInversions());
		Assert.assertEquals(false, s.isSolvable());

		byte[]vals={4,1,7,3,13,15,5,10,2,0,9,11,6,12,8,14};
		s=new SlidingSquare((byte)4,(byte)4, vals);
		Assert.assertEquals(true, s.isSolvable());
		
		byte []vals2={11,4,0,8,9,7,10,6,14,15,2,1,5,12,3,13};
		s=new SlidingSquare((byte)4,(byte)4, vals2);
		System.out.println(s);
		System.out.println(s.countInversions());
		Assert.assertEquals(false, s.isSolvable());
		
		byte [] vals3={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		s=new SlidingSquare((byte)4,(byte)4, vals3);
		IDAStarMain.ManhattanDistanceWithLinearConflict mdwlc;
		mdwlc=new IDAStarMain.ManhattanDistanceWithLinearConflict();
		System.out.println(mdwlc.getHeuristic(new SlidingSquareNode(s)));
	}

}
