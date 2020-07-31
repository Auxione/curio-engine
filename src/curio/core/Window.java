package core;

import common.utilities.ImageBuffer;
import core.events.WindowListener;
import vendor.glfw.GLFW_Window;

/**
 * Window interface for curio-engine.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface Window {
	public static Window createInstance(int type) {
		return new GLFW_Window();
	}

	/**
	 * Clears the frame buffer.
	 */
	public void clear();

	/**
	 * swaps screen buffer with the off screen frame buffer.
	 */
	public void swapBuffers();

	/**
	 * poll input and window events.
	 */
	public void pollEvents();

	/**
	 * 
	 * @return true if user closed the application window.
	 */
	public boolean closeRequest();

	/**
	 * Cursor is visible.
	 */
	public static int CURSOR_NORMAL = 0;
	/**
	 * Cursor is hidden.
	 */
	public static int CURSOR_HIDDEN = 1;
	/**
	 * Cursor is disabled.
	 */
	public static int CURSOR_DISABLED = 2;

	/**
	 * Changes cursor mode.
	 * 
	 * @param i : one of the {@link CURSOR_NORMAL}, {@link CURSOR_HIDDEN},
	 *          {@link CURSOR_DISABLED}
	 */
	public void cursorMode(int i);

	/**
	 * Set the cursor to given index
	 * 
	 * @param index : The index of the cursor.
	 */
	public void setCursor(int index);

	/**
	 * Create new cursor from {@link ImageBuffer}, offset it with given parameters.
	 * 
	 * @param imageBuffer : The {@link ImageBuffer} to create cursor.
	 * @param xOffset     : The x offset of the cursor image.
	 * @param yOffset     : The y offset of the cursor image.
	 */
	public void createCursor(ImageBuffer imageBuffer, int xOffset, int yOffset);

	/**
	 * Bring the window to top.
	 */
	public void showWindow();

	/**
	 * Hide the application window.
	 */
	public void hideWindow();

	/**
	 * Iconify the application window.
	 */
	public void iconifyWindow();

	/**
	 * Request attention from user.
	 */
	public void requestAttention();

	/**
	 * Get double-precision timer from window.
	 */
	public double getTime();

	/**
	 * 
	 * @return true if window has user focus.
	 */
	public boolean isFocused();

	/**
	 * 
	 * @return true if window is iconified.
	 */
	public boolean isIconified();

	/**
	 * 
	 * @return true if window is maximized.
	 */
	public boolean isMaximized();

	/**
	 * Returns the monitor name from given index.
	 * 
	 * @param index : The index of the monitor. Default monitor index is 0.
	 * @return the name of the monitor.
	 */
	public String getMonitorName(int index);

	/**
	 * Set application Icon from given ImageBuffer
	 * 
	 * @param imageBuffer : the {@link ImageBuffer} to set application icon.
	 */
	public void setIcon(ImageBuffer imageBuffer);

	/**
	 * Set {@link WindowListener} to this window.
	 * 
	 * @param displayListener : The listener to set.
	 */
	public void setListener(WindowListener displayListener);

	/**
	 * Apply the given {@link WindowSettings} to window.
	 * 
	 * @param windowSettings : The settings to apply.
	 */
	public void applySettings(WindowSettings windowSettings);

	/**
	 * Run the window.
	 * 
	 * @param windowSettings : The settings to apply before running.
	 */
	public void run(WindowSettings windowSettings);

}
