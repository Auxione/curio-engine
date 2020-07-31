package core.events;

/**
 * Interface for listening window callback.
 * 
 * @see Window
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface WindowListener {
	public void windowIconified();

	public void windowMaximized();

	public void windowFocus(boolean focus);

	public void windowResized(int width, int height);

	public void windowDrop(String[] paths);
}
