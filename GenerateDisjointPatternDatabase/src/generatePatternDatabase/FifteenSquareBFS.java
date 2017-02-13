package generatePatternDatabase;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

import ed.util.algorithms.Action;

public class FifteenSquareBFS {
    public static  Boolean BFS(PatternDBSlidingSquareNode head, Action<PatternDBSlidingSquareNode, Boolean> action, int[] availableValues, String filename) throws IOException
    {
    	int i=0;
    	PatternDBSlidingSquareTriePrototype prototype=new PatternDBSlidingSquareTriePrototype(head.getHeight(), head.getWidth(), availableValues);
    	prototype.add(head.getSquares(), (byte) head.getNumInRoute());
    	PatternDBSlidingSquareCollection trie=prototype.getTrie();
		String directory="/home/ejezisek/Projects/priorityQueueDir";
		File f=new File(directory);
		f.mkdir();
    	FileBasedPriorityQueue q=new FileBasedPriorityQueue(directory);
    	q.add(new PQItem(trie.getIndex(head.getSquares()), (byte)head.getNumInRoute()));
    	int distance=0;
    	while(true)
    	{
    		i++;
    		PQItem curr=q.poll();
    		if(curr==null)
    			q.poll();
    		if(curr==null)
    			break;
    		PatternDBSlidingSquareNode currentNode=new PatternDBSlidingSquareNode(curr.squareVal, prototype);
    		if(currentNode.getNumInRoute()==-1)
    		{
    			System.out.println(i);
    			System.out.println("Error");
    		}
    		action.performAction(currentNode);
    		if(action.complete())
    			return action.getValue();
    		for(PatternDBSlidingSquareNode val:currentNode.getChildren())
    		{
    			if(!prototype.exists(val.getSquares()))
    			{
        			if(val.getNumInRoute()>distance || val.getNumInRoute()<distance-1)
        			{
        				distance=val.getNumInRoute();
        				System.out.println(distance);
        			}
    				prototype.add(val.getSquares(), (byte)val.getNumInRoute());
    		    	q.add(new PQItem(trie.getIndex(val.getSquares()), (byte)val.getNumInRoute()));
    			}
    		}
    	}
    	prototype.write(filename);
    	return action.getValue();
    }
}
