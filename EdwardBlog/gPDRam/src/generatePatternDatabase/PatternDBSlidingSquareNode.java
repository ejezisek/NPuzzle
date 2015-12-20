package generatePatternDatabase;
import java.util.ArrayList;
import java.util.List;

import ed.util.algorithms.Node;
import generatePatternDatabase.PatternDBSlidingSquareUsingLong.DIRECTION;

public class PatternDBSlidingSquareNode  implements Node<PatternDBSlidingSquareNode>{
	private PatternDBSlidingSquareUsingLong proxy;

	public PatternDBSlidingSquareNode(PatternDBSlidingSquareUsingLong s)
	{
		proxy=s;
	}

	public boolean isSolvable() { return proxy.isSolvable(); }

	@Override
	public List<PatternDBSlidingSquareNode> getChildren() {

		List<PatternDBSlidingSquareNode> theChildren=new ArrayList<PatternDBSlidingSquareNode>(4);
		for(PatternDBSlidingSquareUsingLong.DIRECTION d:PatternDBSlidingSquareUsingLong.DIRECTION.values())
		{
			PatternDBSlidingSquareUsingLong sq=proxy.move(d);
			if(sq==null)
				continue;
			//if(!exists(sq.getSquares()))
			{
				PatternDBSlidingSquareNode moved=new PatternDBSlidingSquareNode(sq);

				theChildren.add(moved);
			}
		}
		return theChildren;
	}
	
	public long getSquares() {
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
	public int getNumInRoute()
	{
		return proxy.numInRoute();
	}
	@Override
	public PatternDBSlidingSquareNode[] getVisitedNodes() {
		return null;
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
		PatternDBSlidingSquareNode other = (PatternDBSlidingSquareNode) obj;
		if (proxy == null) {
			if (other.proxy != null)
				return false;
		} else if (!proxy.equals(other.proxy))
			return false;
		return true;
	}
	public long toLong()
	{
		return proxy.toLong();
	}
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 1;
	}


}
