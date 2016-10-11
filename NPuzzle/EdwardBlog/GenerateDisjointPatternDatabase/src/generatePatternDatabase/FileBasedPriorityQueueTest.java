package generatePatternDatabase;

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

import org.junit.Test;

public class FileBasedPriorityQueueTest {

	@Test
	public void test() throws IOException {
		String directory="newDir";
		File f=new File("./"+directory);
		f.mkdir();
		System.out.println("hello");
		System.out.println(f.getCanonicalPath());
		System.out.println(f.exists());
		FileBasedPriorityQueue fbpq=new FileBasedPriorityQueue(directory, 2, 1);
		fbpq.add(new PQItem(5, (byte)3));
		fbpq.add(new PQItem(6, (byte)2));
		fbpq.add(new PQItem(7, (byte)2));
		fbpq.add(new PQItem(1, (byte)2));
		fbpq.add(new PQItem(3, (byte)2));
		for(int i=0; i<5; i++)
		{
		PQItem pq=fbpq.poll();
		if(pq!=null)
		System.out.println(pq);
		}

	}

}
