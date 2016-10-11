package ed.util.algorithms;

public class BinaryTreeTraversals {
	

    
    /**
     * The Node is able to get the left node and the right node.  This is the 
     * requirement for a Binary Tree.  The Node extends Node<T>.  
     * This allows Action<T extends Node<T>> to use any public methods inside
     * Node<T> that we do not currently know about and when the traversal goes
     * left or right, it returns the correct Node type instead of returning a 
     * Node that can only go left and right.
     * @param <T>
     */
    public static interface  BinaryNode<T extends BinaryNode<T>> extends Node<T>
    {
    	public T getLeft();
    	public T getRight();
    }
     
    /**
     * This traverses nodes in a preorder fashion.
     * It calls nodes recursively from the left and then 
     * recursively calls the nodes to the right.
    **/
    public static <T extends BinaryNode<T>, K> K PreorderTraversal(T head, Action<T, K> action)
    {
    	if(head==null || action.complete())
    		return action.getValue();
    	action.performAction(head);
    	T left=head.getLeft();
    	PreorderTraversal(left, action);
    	T right=head.getRight();
    	PreorderTraversal(right, action);
    	return action.getValue();
    }
    
    /**
     * This traverses nodes in a post-order fashion.
    **/
    public static <T extends BinaryNode<T>, K> K PostorderTraversal(T head, Action<T,K> action)
    {
    	if(head==null || action.complete())
    		return action.getValue();
    	T left=head.getLeft();
    	PreorderTraversal(left, action);
    	T right=head.getRight();
    	PreorderTraversal(right, action);
    	action.performAction(head);
		return action.getValue();
    }
    
    /**
     * This traverses nodes in a in-order fashion.
    **/
    public static <T extends BinaryNode<T>, K> K InorderTraversal(T head, Action<T, K> action)
    {
    	if(head==null || action.complete())
    		return action.getValue();
    	T left=head.getLeft();
    	PreorderTraversal(left, action);
    	action.performAction(head);
    	T right=head.getRight();
    	PreorderTraversal(right, action);
		return action.getValue();
    }
}
