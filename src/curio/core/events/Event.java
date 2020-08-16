package core.events;

import core.Input;
import core.Window;

/**
 * Provides event based system for curio-engine. Contains keyPress, keyRelease,
 * keyRepeat for keyboard input; MousePress, mouseRelease, mouseRepeat,
 * mouseWheelChange for mouse input; Iconify, Maximize, Focus loss, Resize,
 * filepath drop for window events. Each event handled by {@link EventHandler}.
 * To consume the event when needed, return true.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public abstract class Event {
	/**
	 * activate or disable the event.
	 */
	public boolean active = true;
	public static Input input;
	public static Window window;

	/**
	 * Create new event then register it to {@link EventContainer}.
	 * 
	 * @param container
	 */
	public Event(EventContainer container) {
		container.registerEvent(this);
	}

	/**
	 * Set current {@link Input} and {@link Window} listeners.
	 * 
	 * @param input
	 * @param window
	 */
	public static final void set(Input input, Window window) {
		Event.input = input;
		Event.window = window;
	}

	/**
	 * @return the string from clipboard.
	 */
	public String getClipBoardContent() {
		return input.getClipboardContent();
	}

	/**
	 * Sets the clipboard content to given string.
	 * 
	 * @param string : The string to set.
	 */
	public void setClipBoardContent(String string) {
		input.setClipboardContent(string);
	}

	/**
	 * Set the cursor to given index.
	 * 
	 * @param index the index of the cursor in window.
	 */
	public void setCursor(int index) {
		window.setCursor(index);
	}

	/**
	 * Set the cursor mode.
	 * 
	 * @see Window#cursorMode(int)
	 * @param value : value to set.
	 */
	public void setCursorMode(int value) {
		window.cursorMode(value);
	}

	/**
	 * Called when change detected in mouseWheel. Return true to consume this event.
	 * 
	 * @see Event
	 * 
	 * @param x : x axis of the wheel.
	 * @param y : y axis of the wheel.
	 * @return true if event consumed.
	 */
	protected boolean mouseWheelChanged(int x, int y) {
		return false;
	};

	/**
	 * Called when mouse button pressed. Return true to consume this event.
	 * 
	 * @see Event
	 * @see Input
	 * 
	 * @param button : The Pressed mouse button.
	 * @param x      : x position of the mouse in application window.
	 * @param y      : y position of the mouse in application window.
	 * @return true if event consumed.
	 */
	protected boolean mousePressed(int button, int x, int y) {
		return false;
	};

	/**
	 * Called when mouse button released. Return true to consume this event.
	 * 
	 * @see Event
	 * @see Input
	 * 
	 * @param button : The released mouse button.
	 * @param x      : x position of the mouse in application window.
	 * @param y      : y position of the mouse in application window.
	 * @return true if event consumed.
	 */
	protected boolean mouseReleased(int button, int x, int y) {
		return false;
	};

	/**
	 * Called when mouse repeat sent from OS.OS usually sent this event when holding
	 * down the button. This event not called if user disabled this feature in OS.
	 * Return true to consume this event.
	 * 
	 * @see Event
	 * @see Input
	 * 
	 * @param button : The repeated mouse button.
	 * @param x      : x position of the mouse in application window.
	 * @param y      : y position of the mouse in application window.
	 * @return true if event consumed.
	 */
	protected boolean mouseRepeat(int button, int x, int y) {
		return false;
	};

	/**
	 * Called when key pressed. Return true to consume this event.
	 * 
	 * @see Event
	 * @see Input
	 * 
	 * @param key : The pressed key from OS.
	 * @param c   : The character of the pressed key.
	 * @return true if event consumed.
	 */
	protected boolean keyPressed(int key, char c) {
		return false;
	};

	/**
	 * Called when key released. Return true to consume this event.
	 * 
	 * @see Event
	 * @see Input
	 * 
	 * @param key : The released key from OS.
	 * @param c   : The character of the released key.
	 * @return true if event consumed.
	 */
	protected boolean keyReleased(int key, char c) {
		return false;
	};

	/**
	 * Called when key repeat sent from OS. OS usually sent this event when holding
	 * down the key. This event not called if user disabled this feature in OS.
	 * Return true to consume this event.
	 * 
	 * @see Event
	 * @see Input
	 * 
	 * @param key : The repeated key from OS.
	 * @param c   : The character of the repeated key.
	 * @return true if event consumed.
	 */
	protected boolean keyRepeat(int key, char c) {
		return false;
	}

	/**
	 * Called when the window is iconified. Iconifying window in OS also loses focus
	 * to window, because of this event, {@link Event#windowFocus(boolean focus)}
	 * called with false parameter.
	 * 
	 * @see Event
	 * @see Window
	 * @return true if event consumed.
	 */
	protected boolean windowIconified() {
		return false;
	}

	/**
	 * Called when the window is Maximized. Disabled when {@link Window} does not
	 * allow window resizing.
	 * 
	 * @see Event
	 * @see Window
	 * @return true if event consumed.
	 */
	protected boolean windowMaximized() {
		return false;
	}

	/**
	 * Called when the window focus lost.
	 * 
	 * @see Event
	 * @see Window
	 * 
	 * @param focus : false when window focus lost.
	 * @return true if event consumed.
	 */
	protected boolean windowFocus(boolean focus) {
		return false;
	}

	/**
	 * Called when the window resized if {@link Window} allows resizing. This method
	 * also halts the engine loop when resizing.
	 * 
	 * @see Event
	 * @see Window
	 * 
	 * @param width  : The new width of the window.
	 * @param height : The new height of the window.
	 * @return true if event consumed.
	 */
	protected boolean windowResized(int width, int height) {
		return false;
	}

	/**
	 * Called when the user drop files to application window.
	 * 
	 * @see Event
	 * @see Window
	 * 
	 * @param count : The number of items dropped into window.
	 * @param paths : The paths of the each dropped item.
	 * @return true if event consumed.
	 */
	protected boolean windowDrop(String[] paths) {
		return false;
	}
}
