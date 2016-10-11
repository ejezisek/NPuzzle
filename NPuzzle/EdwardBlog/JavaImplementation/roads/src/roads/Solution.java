package roads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Solution {
	public static class Road implements Node<Road>
	{
		private int value;
		public Road(int value)
		{
			children=new ArrayList<Road>();
			this.value=value;
		}
		public List<Road> children;
		public void addChild(Road item)
		{
			children.add( item);
		}
		@Override
		public List<Road> getChildren() {
			return children;
		}

		@Override
		public Road[] getVisitedNodes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCost() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
    public int solution(int[] A, int[] B, int K) {
    	HashMap<Integer, Road> roads=new HashMap<Integer, Road>();
    	for(int i=0; i<A.length; i++)
    	{
    		Road aRoad=new Road(A[i]);
    		Road bRoad=new Road(B[i]);
    		aRoad.addChild(bRoad);
    		bRoad.addChild(aRoad);
    	}
    }

}
