package core.events;

/**
 * Combined interface for listening input.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface InputListener {
	/**
	 * Notification that a key was pressed
	 * 
	 * @param key The key code that was pressed
	 * @param c   The character of the key that was pressed
	 */
	public void keyPressed(int key, char c);

	/**
	 * Notification that a key was released
	 * 
	 * @param key The key code that was released
	 * @param c   The character of the key that was released
	 */
	public void keyReleased(int key, char c);

	/**
	 * Notification that a key was held down
	 * 
	 * @param key The key code that was held down
	 * @param c   The character of the key that was released
	 */
	public void keyRepeat(int key, char c);

	/**
	 * Notification that the mouse wheel position was updated
	 * 
	 * @param change The amount of the wheel has moved
	 */
	public void mouseWheelChanged(int x, int y);

	/**
	 * Notification that a mouse button was pressed.
	 * 
	 * @param button The index of the button (starting at 0)
	 * @param x      The x position of the mouse when the button was pressed
	 * @param y      The y position of the mouse when the button was pressed
	 */
	public void mousePressed(int button, int x, int y);

	/**
	 * Notification that a mouse button was released.
	 * 
	 * @param button The index of the button (starting at 0)
	 * @param x      The x position of the mouse when the button was released
	 * @param y      The y position of the mouse when the button was released
	 */
	public void mouseReleased(int button, int x, int y);

	/**
	 * Notification that a mouse button held down.
	 * 
	 * @param button The index of the button (starting at 0)
	 * @param x      The x position of the mouse when the button was released
	 * @param y      The y position of the mouse when the button was released
	 */
	public void mouseRepeat(int button, int x, int y);
}
