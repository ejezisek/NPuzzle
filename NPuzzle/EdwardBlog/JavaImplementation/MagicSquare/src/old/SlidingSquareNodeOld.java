package old;
import java.util.ArrayList;
import java.util.List;

import ed.util.algorithms.Node;

public class SlidingSquareNodeOld  implements Node<SlidingSquareNodeOld>{

	private SlidingSquareOld proxy;

	public SlidingSquareNodeOld(SlidingSquareOld s)
	{
		proxy=new SlidingSquareOld(s);
		proxy.setParent(s.getParent());
	}

	public boolean isSolvable() { return proxy.isSolvable(); }
	@Override
	public List<SlidingSquareNodeOld> getChildren() {
		List<SlidingSquareNodeOld> theChildren=new ArrayList<SlidingSquareNodeOld>(4);
		for(SlidingSquareOld.DIRECTION d:SlidingSquareOld.DIRECTION.values())
		{
			SlidingSquareOld sq=proxy.move(d);
			if(sq==null)
				continue;
			SlidingSquareNodeOld moved=new SlidingSquareNodeOld(sq);
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
	public SlidingSquareNodeOld[] getVisitedNodes() {
		SlidingSquareOld [] route=proxy.getRoute();
		SlidingSquareNodeOld[] nodes=new SlidingSquareNodeOld[route.length];
		int i=0;
		for(SlidingSquareOld s: route)
			nodes[i++]=new SlidingSquareNodeOld(s);
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
		SlidingSquareNodeOld other = (SlidingSquareNodeOld) obj;
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
