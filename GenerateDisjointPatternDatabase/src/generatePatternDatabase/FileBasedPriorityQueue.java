package generatePatternDatabase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class FileBasedPriorityQueue {
	private HashMap<Integer, ArrayList<PQItem>> items;
	private int currentLength;
	int queueSize=0;
	int maxLength=0;
	private int maxEntries;
	private int readEntries;
	private int numBytesPerEntry=4;
	private String queueDirectory;
	public FileBasedPriorityQueue(String queueDirectory)
	{
		this(queueDirectory, 30_000_000, 500_000);
	}
	public FileBasedPriorityQueue(String queueDirectory, int maxEntries, int readEntries)
	{
		currentLength=0;
		items=new HashMap<Integer, ArrayList<PQItem>>();	
		this.queueDirectory=queueDirectory;
		this.maxEntries=maxEntries;
		this.readEntries=readEntries;
		cleanDirectory(queueDirectory);
	}
	public void cleanDirectory(String filename)
	{
		File file = new File(filename);
        File[] files = file.listFiles(); 
        for (File f:files) 
        {if (f.isFile() && f.exists()) 
            { f.delete();
            }else{
} }
	}

	public void add(PQItem pq) throws IOException
	{
		ArrayList<PQItem> pqList=items.get(pq.numInSquare);
		maxLength=Math.max(pq.numInSquare, maxLength);
		if(pqList==null)
		{
			pqList=new ArrayList<PQItem>();
			items.put(pq.numInSquare, pqList);
		}
		pqList.add(pq);
		queueSize++;
		if(queueSize>maxEntries)
		{
			while(queueSize>readEntries)
			{
				queueSize=writeItemFromQueue(queueSize);
			}
		}
	}
	
	private int writeItemFromQueue(int queueSize) throws IOException {
		//Iterate through all of the entries above me.
		IntStream.builder();
		OptionalInt maximum=items.keySet().stream().mapToInt(Integer::intValue).max();
		if(maximum.isPresent())
		{
			for(int i=maximum.getAsInt(); i>=currentLength; i--)
			{
				File f=new File(queueDirectory+"/"+i);

				ArrayList<PQItem> pqItemList=items.get(i);
				if(pqItemList==null)
				{
					continue;
				}
				RandomAccessFile raf=new RandomAccessFile(f, "rw");
				while(pqItemList.size()>0 && queueSize>readEntries)
				{
					for(int j=pqItemList.size()-1; j>=0; j--)
					{
						PQItem pqItem=pqItemList.get(j);
						//Write the item. and decrease the queue.
						writeItem(pqItem, i, raf);
						queueSize--;
						pqItemList.remove(j);
					}	
				}
				pqItemList.trimToSize();
				raf.close();
			}
		}
		return queueSize;
	}
	
	private void writeItem(PQItem pqItem, int i, RandomAccessFile raf) throws IOException {
		raf.writeInt(pqItem.squareVal);
	}
	public PQItem poll() throws IOException
	{

		List<PQItem> pqList=items.get(currentLength);

		if(pqList==null)
		{

			if(items.size()==0)
				return null;
			else
			{
				items.remove(currentLength);
				currentLength++;
				return poll();
			}
		}
		if(pqList.size()>0)
		{
			PQItem ret=pqList.get(pqList.size()-1);
			pqList.remove(pqList.size()-1);
			queueSize--;
			return ret;
		}
		else
		{
			//Read in up to <maxLength> entries.
			File f=new File(queueDirectory+"/"+currentLength);
			if(!f.exists())
			{
				items.remove(currentLength);
				currentLength++;
				if(currentLength>maxLength)
					return null;
			}
			else
			{
				RandomAccessFile raf=new RandomAccessFile(f, "rw");
				try{
					int numRead=0;
					if(raf.length()>0)
					{
						if(raf.length()>readEntries*numBytesPerEntry)
						{
							numRead=readEntries;
						}
						else
						{
							numRead=(int) (raf.length()/numBytesPerEntry);
						}

						readItems(raf, numRead, pqList, currentLength);
					}
					else
					{
						items.remove(currentLength);
						currentLength++;
					}
				}
				finally
				{
					if(raf!=null)
					raf.close();
				}
			}
		}
		return poll();
	}
	private void readItems(RandomAccessFile raf, int numItems, List<PQItem> pqList, int currentLength2) throws IOException
	{
		System.out.println(raf.length());
		raf.seek(raf.length()-numItems*numBytesPerEntry);
		for(int i=0; i<numItems; i++)
		{
			int squareVal=raf.readInt();
			PQItem pq=new PQItem(squareVal, (byte) currentLength2);
			pqList.add(pq);
			queueSize++;
		}
		int newLength=(int) (raf.length()-numItems*numBytesPerEntry);
		raf.setLength(newLength);
	}

}
