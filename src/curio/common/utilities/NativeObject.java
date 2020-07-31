package common.utilities;

/**
 * NativeObject interface for auto resource cleanup.
 * 
 * @see NativeObjectManager
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface NativeObject {
	public void terminate();
}
