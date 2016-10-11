import org.junit.Test;

public class SlidingSquareTest {

	@Test
	public void test() {
		SlidingSquare s=new SlidingSquare((byte)4,(byte)4);
		System.out.println(s);
		s=s.move(SlidingSquare.DIRECTION.LEFT);
		System.out.println(s);
	}

}
