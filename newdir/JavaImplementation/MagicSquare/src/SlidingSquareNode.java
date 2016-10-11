import java.util.ArrayList;
import java.util.List;

import ed.util.algorithms.Node;

public class SlidingSquareNode  implements Node<SlidingSquareNode>{

	private SlidingSquare proxy;

	public SlidingSquareNode(SlidingSquare s)
	{
		proxy=new SlidingSquare(s);
		proxy.setParent(s.getParent());
	}

	public boolean isSolvable() { return proxy.isSolvable(); }
	
	@Override
	public List<SlidingSquareNode> getChildren() {
		List<SlidingSquareNode> theChildren=new ArrayList<SlidingSquareNode>(4);
		for(SlidingSquare.DIRECTION d:SlidingSquare.DIRECTION.values())
		{
			SlidingSquare sq=proxy.move(d);
			if(sq==null)
				continue;
			SlidingSquareNode moved=new SlidingSquareNode(sq);
			theChildren.add(moved);
		}
		return theChildren;
	}
	
	public byte[] getSquares() {
		return proxy.getSquares();
	}
	public String getCommaDelimited() {
		return proxy.getCommaDelimited();
	}
	public boolean isSolved() {
		return proxy.isSolved();
	}

	public String toString()
	{
		return proxy.toString();
	}
	public int getHeight() {
		return proxy.getHeight();
	}
	public int getWidth() {
		return proxy.getWidth();
	}
	@Override
	public SlidingSquareNode[] getVisitedNodes() {
		SlidingSquare [] route=proxy.getRoute();
		SlidingSquareNode[] nodes=new SlidingSquareNode[route.length];
		int i=0;
		for(SlidingSquare s: route)
			nodes[i++]=new SlidingSquareNode(s);
		return nodes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((proxy == null) ? 0 : proxy.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SlidingSquareNode other = (SlidingSquareNode) obj;
		if (proxy == null) {
			if (other.proxy != null)
				return false;
		} else if (!proxy.equals(other.proxy))
			return false;
		return true;
	}
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 1;
	}
}
