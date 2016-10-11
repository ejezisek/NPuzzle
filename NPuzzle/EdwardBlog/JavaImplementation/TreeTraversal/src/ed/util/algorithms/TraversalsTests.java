package ed.util.algorithms;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;



public class TraversalsTests {
	private static class getKthItemAction <T extends Node<T>> implements Action<T, String>
	{
		String value=null;
		int index=0;
		private int desiredIndex;
		private boolean complete;
		public getKthItemAction(int desiredIndex)
		{
			this.desiredIndex=desiredIndex;
			complete=false;
		}

		@Override
		public boolean complete() {
			// TODO Auto-generated method stub
			return complete;
		}

		@Override
		public String getValue() {
			// TODO Auto-generated method stub
			return value;
		}
		@Override
		public void performAction(T node) {
			index++;
			if(index==desiredIndex)
			{
				complete=true;
				value=node.toString();
			}
		}
		
	}
	
	private static class TestNode implements BinaryTreeTraversals.BinaryNode<TestNode>
	{
		private TestNode left;
		private TestNode right;
		private String value;
		private List<TestNode> visitedNodes;
		public TestNode(String value)
		{
			visitedNodes=new ArrayList<TestNode>();
			this.value=value;
		}
		public void setLeft(TestNode left)
		{
			this.left=left;
		}
		public void setRight(TestNode right)
		{
			this.right=right;
		}
		
		@Override
		public TestNode getLeft() {
			return left;
		}

		@Override
		public TestNode getRight() {
			return right;
		}
		
		@Override
		public String toString()
		{
			return value;
		}
		
		@Override
		public List<TestNode> getChildren() {
				TestNode[] nodes=new TestNode[2];
				nodes[0]=left;
				nodes[1]=right;
				return Arrays.asList(nodes);
		}
		@Override
		public TestNode[] getVisitedNodes() {
			return visitedNodes.toArray(new TestNode[0]);
		}
		@Override
		public int getCost() {
			return 1;
		}


	}
	public TestNode generateTree()
	{
		TestNode one=new TestNode("0");
		TestNode two=new TestNode("1");
		TestNode three=new TestNode("2");
		one.setLeft(two);
		one.setRight(three);
		return one;
	}
	
	@Test
	public void Preordertest() {
		TestNode root=generateTree();		
		String preorderTraversal = BinaryTreeTraversals.PreorderTraversal(root, new getKthItemAction<TestNode>(1));
		assertTrue("0".equals(preorderTraversal));
		assertTrue("1".equals(BinaryTreeTraversals.PreorderTraversal(root, new getKthItemAction<TestNode>(2))));
		assertTrue("2".equals(BinaryTreeTraversals.PreorderTraversal(root, new getKthItemAction<TestNode>(3))));
	}
	
	@Test
	public void Postordertest() {
		TestNode root=generateTree();
		assertTrue("1".equals(BinaryTreeTraversals.PostorderTraversal(root, new getKthItemAction<TestNode>(1))));
		assertTrue("2".equals(BinaryTreeTraversals.PostorderTraversal(root, new getKthItemAction<TestNode>(2))));
		assertTrue("0".equals(BinaryTreeTraversals.PostorderTraversal(root, new getKthItemAction<TestNode>(3))));
	}
	@Test
	public void Inordertest() {
		TestNode root=generateTree();
		assertTrue("1".equals(BinaryTreeTraversals.InorderTraversal(root, new getKthItemAction<TestNode>(1))));
		assertTrue("0".equals(BinaryTreeTraversals.InorderTraversal(root, new getKthItemAction<TestNode>(2))));
		assertTrue("2".equals(BinaryTreeTraversals.InorderTraversal(root, new getKthItemAction<TestNode>(3))));
	}

}
