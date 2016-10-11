package ed.util.algorithms;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class that contains various tree traversals that work on any Node<T>.
 *
 */
public class TreeTraversals {
	/**
     * Uses BFS to visit all nodes.
    **/
    public static <T extends Node<T>, K> K BFS(T head, Action<T, K> action)
    {
    	Queue<T> q=new LinkedList<T>();
    	q.add(head);

    	while(!q.isEmpty())
    	{
    		T currentNode=q.poll();
    		action.performAction(currentNode);
    		if(action.complete())
    			return action.getValue();
    		for(T val:currentNode.getChildren())
    		{
    				q.add(val);
    		}
    	}
    	return action.getValue();
    }
}
