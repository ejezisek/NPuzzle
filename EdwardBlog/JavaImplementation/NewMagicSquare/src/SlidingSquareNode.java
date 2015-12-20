import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ed.util.algorithms.Node;

public class SlidingSquareNode  implements Node<SlidingSquareNode>{

	private HashSet<SlidingSquare> visitedNodes;
	private SlidingSquare proxy;
	public SlidingSquareNode(byte height, byte width) {
		proxy=new SlidingSquare(height, width);
		visitedNodes=new HashSet<SlidingSquare>();
	}
	public SlidingSquareNode(SlidingSquare s)
	{
		proxy=new SlidingSquare(s);
		visitedNodes=new HashSet<SlidingSquare>();
	}
	@Override
	public SlidingSquareNode[] getChildren() {
		List<SlidingSquareNode> theChildren=new ArrayList<SlidingSquareNode>();
		for(SlidingSquare.DIRECTION d:SlidingSquare.DIRECTION.values())
		{
			SlidingSquare sq=proxy.move(d);
			if(sq==null)
				continue;

			if(!visitedNodes.contains(sq))
			{
				SlidingSquareNode moved=new SlidingSquareNode(sq);
				theChildren.add(moved);
			}
		}
		return theChildren.toArray(new SlidingSquareNode[0]);
	}
	public void addVisitedNodes(SlidingSquareNode[] nodes) {
		for(SlidingSquareNode n:nodes)
		{
			addVisitedNode(n);
		}
	}
	public void addVisitedNode(SlidingSquareNode node) {
		visitedNodes.add(node.proxy);
	}
	public void addVisitedNodes(List<SlidingSquareNode> nodes) {
		for(SlidingSquareNode n:nodes)
		{
			addVisitedNode(n);
		}		
	}
	public byte[] getSquares() {
		// TODO Auto-generated method stub
		return proxy.getSquares();
	}
	public String getCommaDelimited() {
		// TODO Auto-generated method stub
		return proxy.getCommaDelimited();
	}
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return proxy.isSolved();
	}

	public String toString()
	{
		return proxy.toString();
	}
	public int getHeight() {
		// TODO Auto-generated method stub
		return proxy.getHeight();
	}
	public int getWidth() {
		// TODO Auto-generated method stub
		return proxy.getWidth();
	}
	@Override
	public SlidingSquareNode[] getVisitedNodes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 1;
	}
}
