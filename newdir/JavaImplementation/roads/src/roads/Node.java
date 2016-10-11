package roads;

import java.util.List;

public interface Node<T extends Node<T>> {
	public List<T> getChildren();
	public T[] getVisitedNodes();
	public int getCost();
}
