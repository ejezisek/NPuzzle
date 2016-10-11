package generatePatternDatabase;


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
	public static boolean exists(long val)
	{
		boolean exists=false;
		FindIterable<Document> iterable = collection.find(
		        new Document("_id", val));

		for(@SuppressWarnings("unused") Document b:iterable)
		{
			exists=true;
			break;
		}
		return exists;
	}
	private static void addMultipleToDB(Set<PatternDBSlidingSquareNode> multiples)
	{
		List<Document> toAddtoDb=new ArrayList<Document>();
		for(PatternDBSlidingSquareNode val:multiples)
		{
			Document doc=new Document("_id", val.toLong());
			doc.append("n", val.getNumInRoute());
			toAddtoDb.add(doc);
		}
		collection.insertMany(toAddtoDb);
	}
    public static  Boolean BFS(PatternDBSlidingSquareNode head, Action<PatternDBSlidingSquareNode, Boolean> action)
    {
    	Queue<PatternDBSlidingSquareNode> q=new LinkedList<PatternDBSlidingSquareNode>();
    	Queue<PatternDBSlidingSquareNode> qToAdd=new LinkedList<PatternDBSlidingSquareNode>();
    	HashSet<PatternDBSlidingSquareNode> contain=new HashSet<PatternDBSlidingSquareNode>();
    	contain.add(head);
    	q.add(head);
    	while(true)
    	{
    		if(q.isEmpty())
    		{
    			//Pull the first 100000 rows from the database
    			Document sort=new Document("natural", 1);
    			
    			FindIterable<Document> iterable = queueCollection.find().sort(sort).limit(100000);
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
    				break;
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
    			if(!contain.contains(val))
    			{
    				if(!exists(val.getSquares()))
    				{
    					contain.add(val);
    					if(dbQueue)
    					{
    						qToAdd.add(val);
    						if(qToAdd.size()>1000000)
    						{
    							addQueueToDb(qToAdd);
    						}
    					}
    					else
    					{
	    					q.add(val);
	    					if(q.size()>1000000)
	    					{
	    						//Add all of queue to the queueCollection.
	    						addQueueToDb(q);
	    					}
    					}
    					
    					if(contain.size()>1000000)
    					{
    						addMultipleToDB(contain);
    						contain.clear();
    					}
    				}
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
	public static void main(String[] args) {
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
		byte[] vals={1,2,3,4,5,6,6,6,9,6,6,6,13,6,6,0};
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
