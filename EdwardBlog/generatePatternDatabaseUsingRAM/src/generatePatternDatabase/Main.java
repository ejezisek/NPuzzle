package generatePatternDatabase;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ed.util.algorithms.Action;

public class Main {
	private static boolean dbQueue=false;
	private static String defaultFileLocation="/home/edward/Research/generatePatternDBFiles/";
	private static SquareLookup squareLookup=new SquareLookup(10, 16, new FileSystem(defaultFileLocation));
	private static int dbInsertNum=1000000;
	private static int qInsertNum=100000;
	private static int qAddNum=10000;
	private static int checkMaxSize=100000;
	public static byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}

	public static String longToFileString(long squareCopy)
	{
		int width=4;
		int height=4;
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<width*height; i++)
		{
			byte val=(byte)(squareCopy&0x0F);
			squareCopy>>>=4;
			sb.append(val);
		}
		return sb.toString();
	}
	public static boolean[] exists(long[] val)
	{
		return squareLookup.exists(val);
	}
	private static void addMultipleToDB(Set<PatternDBSlidingSquareNode> multiples) throws IOException
	{
		System.out.println("Adding multiple.");
		long [] vals=new long[multiples.size()];
		int [] nIR=new int[multiples.size()];
		int i=0;
		for(PatternDBSlidingSquareNode val:multiples)
		{
			vals[i]=val.getSquares();
			nIR[i]=val.getNumInRoute();
			i++;
		}
		squareLookup.add(vals, nIR);
	}
    public static  Boolean BFS(PatternDBSlidingSquareNode head, Action<PatternDBSlidingSquareNode, Boolean> action) throws IOException
    {
    	Queue<PatternDBSlidingSquareNode> q=new LinkedList<PatternDBSlidingSquareNode>();
    	Queue<PatternDBSlidingSquareNode> qToAdd=new LinkedList<PatternDBSlidingSquareNode>();
    	HashSet<PatternDBSlidingSquareNode> contain=new HashSet<PatternDBSlidingSquareNode>();
    	contain.add(head);
    	q.add(head);
    	int currentLength=1;
    	while(true)
    	{
    		if(q.isEmpty())
    		{
 
    			//Pull the first 100000 rows from the database
    			Document sort=new Document("natural", 1);
    			
    			FindIterable<Document> iterable = queueCollection.find().sort(sort).limit(qAddNum);
    			long maxVal=0;
    			for(Document doc:iterable)
    			{
    				PatternDBSlidingSquareUsingLong tmp=new PatternDBSlidingSquareUsingLong(doc.getLong("_id"), doc.getInteger("n"));
    				q.add(new PatternDBSlidingSquareNode(tmp));
    				maxVal=Math.max(maxVal, doc.getLong("natural"));
    			}
    			BasicDBObject query=new BasicDBObject("natural", new BasicDBObject("$lt", maxVal+1));

    			queueCollection.deleteMany(query);
    			if(q.isEmpty())
    			{
    				if(qToAdd.isEmpty())
    				{
    					break;
    				}
    				else
    				{
    					q=qToAdd;
    					qToAdd=new LinkedList<PatternDBSlidingSquareNode>();
    				}
    			}
    		}
    		PatternDBSlidingSquareNode currentNode=q.poll();
    		action.performAction(currentNode);
    		if(action.complete())
    			return action.getValue();
    		for(PatternDBSlidingSquareNode val:currentNode.getChildren())
    		{
   				boolean [] results=squareLookup.exists(vals);
   				for(int i=0; i<results.length; i++)
   				{
   					if(!PatternDBSlidingSquareTrieFlyweight.COLLECTION.exists(val.getSquares()))
   					{
   						
        					contain.add(arr[i]);
        					if(dbQueue)
        					{
        						qToAdd.add(arr[i]);
        						if(qToAdd.size()>qInsertNum)
        						{
        							addQueueToDb(qToAdd);
        						}
        					}
        					else
        					{
    	    					q.add(arr[i]);
    	    					if(q.size()>qInsertNum)
    	    					{
    	    						addQueueToDb(q);
    	    					}
        					}
        					
        					if(contain.size()>dbInsertNum)
        					{
        						addMultipleToDB(contain);
        						contain.clear();
        					}
    					}
    				}
    				toCheck.clear();
    				currentLength=val.getNumInRoute();
    				toCheck.add(val);
    			}

    		}
    	}
    	addMultipleToDB(contain);
    	return action.getValue();
    }
    static long index=0;
	private static void addQueueToDb(Queue<PatternDBSlidingSquareNode> q) {
		List<Document> toAddtoDb=new ArrayList<Document>();
		for(PatternDBSlidingSquareNode val:q)
		{
		Document doc=new Document("_id", val.toLong());
		doc.append("natural", index++);
		doc.append("n", val.getNumInRoute());
		toAddtoDb.add(doc);
		}
		queueCollection.insertMany(toAddtoDb);		
		q.clear();
	}
	private static MongoClient mc;
	private static MongoCollection<Document> collection;
	private static MongoCollection<Document> queueCollection;
	public static void main(String[] args) throws IOException {
	
		if(mc==null)
		{
			mc=new MongoClient("localhost");
			MongoDatabase db=mc.getDatabase("mydb");
			collection=db.getCollection("npuzzleCollection");
			collection.drop();
			queueCollection=db.getCollection("queue");
			queueCollection.drop();
			BasicDBObject index=new BasicDBObject("natural", 1);
			queueCollection.createIndex(index);
		}
		byte[] vals={1,2,3,4,5,6,6,6,7,6,6,6,8,6,6,0};
		PatternDBSlidingSquareUsingLong startingPos=new PatternDBSlidingSquareUsingLong((byte)4,(byte)4, vals);
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
		Main.BFS(pdssn, action);
	}

}
