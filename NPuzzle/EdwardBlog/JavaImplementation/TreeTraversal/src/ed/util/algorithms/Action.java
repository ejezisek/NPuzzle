package ed.util.algorithms;

/**
 * This action has two template values.  The template value T
 * ensures that any Nodes operations can be supported by
 * any action.  The template value K allows the Action to return any value
 * in the getValue operation.  This should allow the preorder traversal to 
 * be reused for many different types of problems.
 * @param <T>
 * @param <K>
 */
public interface Action<T extends Node<T>, K>
{
	public void performAction(T node);
	public boolean complete();
	public K getValue();
}