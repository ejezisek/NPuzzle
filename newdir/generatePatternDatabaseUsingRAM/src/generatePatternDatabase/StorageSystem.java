package generatePatternDatabase;

public interface StorageSystem {
	public void addMultiple(String[] location, String []value, int[] numToDestination);
public boolean[] exists(String[] location, String[] value);
}
