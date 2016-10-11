package ed.util.algorithms;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ed.util.algorithms.Common.Goal;
import ed.util.algorithms.Common.Heuristic;

public class AStar {

	/**
	 * Takes in a Node<T> vb, a Heuristic using the Node<T>(This is an anonymous function) and a Goal.
	 * Returns a List of the visited nodes or null if it could not find the desired node.
	 * Note: This could run forever if it can't find the given node and there are an infinite number of nodes to process.
	 * @param vb
	 * @param h
	 * @param g
	 * @return
	 */
	public static <T extends Node<T>> List<T> AStar(T vb, Heuristic<T> h, Goal<T> g)
	{
		return null;
	}
	/**
	 * Returns a value associated with IDA*.
	 * If it is found, it will return a list of visited nodes.
	 * Otherwise it will return a heuristic or NOT FOUND.
	 *
	 * @param <T>
	 */
	private static class IDAStarRet <T extends Node<T>> {
		private SEARCHRETURN sr;
		private int newHeuristic;
		private List<T> visitedNodes;
		public SEARCHRETURN getSearchReturn()
		{
			return sr;
		}
		public void setSearchReturn(SEARCHRETURN sr)
		{
			this.sr=sr;
		}
		
		public void setHeuristic(int newHeuristic)
		{
			this.newHeuristic=newHeuristic;
		}
		public int getHeuristic()
		{
			return newHeuristic;
		}

		public void setVisitedNodes(List<T> visitedNodes)
		{
			this.visitedNodes=visitedNodes;
		}
		public List<T> getVisitedNodes()
		{
			return this.visitedNodes;
		}
		
	}

	private static enum SEARCHRETURN { BOUND, FOUND, NOT_FOUND };
	public static int nodeNum=0;
	/**
	 * This is a method for A*.  A* uses breadth first search to find a path of nodes and eventually reach the goal.
	 * It uses a priority queue to initially search for the nodes using the lowest value of heuristic h.
	 * T extends Node<T> is used to ensure that the type T is a node and is able to return a type T for the 
	 * getChildren command.
	 * The heuristic is a function that takes in the Node and returns an integer representing the heuristic.
	 * @param currentNode
	 * @param currentCost cost to reach node vb.
	 * @param bound
	 * @param h
	 * @param goal
	 * @return
	 */
	//TODO: Implement this.
	private static <T extends Node<T>> IDAStarRet<T> search(T currentNode, int currentCost, int bound, Heuristic<T> h, Goal<T> goal) {
		int estimatedCost=currentCost+h.getHeuristic(currentNode);
		IDAStarRet<T> ret=new IDAStarRet<T>();
		nodeNum++;
		if(nodeNum%5000000==0)
		{
			System.out.println(new Date());
			System.out.println(nodeNum);
		}
		//Check if the currentNode is the goal.
		if(goal.isGoal(currentNode))
		{			
			ret.setSearchReturn(SEARCHRETURN.FOUND);
			ret.setVisitedNodes(Arrays.asList(currentNode.getVisitedNodes()));
			return ret;
		}
		//If estimatedCost is greater than the bound, return and set the new bound appropriately.
		if(estimatedCost>bound)
		{
			ret.setSearchReturn(SEARCHRETURN.BOUND);
			ret.setHeuristic(estimatedCost);
			return ret;
		}
		

		
		//Set to an arbitrarily large value, to make sure that any available values replace this.
		int min=Integer.MAX_VALUE;

		//Iterate through all of the current nodes children.
		for(T successor:currentNode.getChildren())
		{
			//Get the return value for each of the children.
			IDAStarRet<T> t=search(successor, currentCost+successor.getCost(), bound, h, goal);
			switch(t.getSearchReturn())
			{
				case BOUND:
					if(t.getHeuristic()<min)
						min=t.getHeuristic();
					break;
				case FOUND:
					return t;
				case NOT_FOUND:
					continue;
			}
		}
		
		//If the minimum did not change, then the node could not be found.
		if(min==Integer.MAX_VALUE)
		{
			ret.setSearchReturn(SEARCHRETURN.NOT_FOUND);
		}
		else
		{
			ret.setHeuristic(min);
			ret.setSearchReturn(SEARCHRETURN.BOUND);
		}
		return ret;
	}
}

