package core.events;

import common.Console;

/**
 * Default system event handling class for curio-engine. Any given event loops
 * through all the listed events. If any events consumed when looping remaining
 * events wont be called.
 * 
 * @see Event
 * @see Window
 * @see Input
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class EventHandler implements InputListener, WindowListener {
	private static EventHandler instance = null;

	private EventContainer eventContainer = null;

	private EventHandler() {
		Console.fine(this, "Initialized.");
	}

	/**
	 * 
	 * @return The EventHandler singleton interface.
	 */
	public static EventHandler getInstance() {
		if (instance == null)
			instance = new EventHandler();
		return instance;
	}

	/**
	 * Set the current {@link EventContainer}.
	 * 
	 * @param eventContainer
	 */
	public void setEventContainer(EventContainer eventContainer) {
		this.eventContainer = eventContainer;
	}

	@Override
	public void keyPressed(int key, char c) {
		for (Event inputEvent : this.eventContainer.getEvents()) {
			if (inputEvent.active) {
				if (inputEvent.keyPressed(key, c) == true) {
					break;
				}
			}
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		for (Event inputEvent : this.eventContainer.getEvents()) {
			if (inputEvent.active) {
				if (inputEvent.keyReleased(key, c) == true) {
					break;
				}
			}
		}
	}

	@Override
	public void keyRepeat(int key, char c) {
		for (Event inputEvent : this.eventContainer.getEvents()) {
			if (inputEvent.active) {
				if (inputEvent.keyRepeat(key, c) == true) {
					break;
				}
			}
		}
	}

	@Override
	public void mouseWheelChanged(int x, int y) {
		for (Event inputEvent : this.eventContainer.getEvents()) {
			if (inputEvent.active) {
				if (inputEvent.mouseWheelChanged(x, y) == true) {
					break;
				}
			}
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		for (Event inputEvent : this.eventContainer.getEvents()) {
			if (inputEvent.active) {
				if (inputEvent.mousePressed(button, x, y) == true) {
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		for (Event inputEvent : this.eventContainer.getEvents()) {
			if (inputEvent.active) {
				if (inputEvent.mouseReleased(button, x, y) == true) {
					break;
				}
			}
		}
	}

	@Override
	public void mouseRepeat(int button, int x, int y) {
		for (Event inputEvent : this.eventContainer.getEvents()) {
			if (inputEvent.active) {
				if (inputEvent.mouseRepeat(button, x, y) == true) {
					break;
				}
			}
		}
	}

	@Override
	public void windowIconified() {
		for (Event event : this.eventContainer.getEvents()) {
			if (event.active) {
				if (event.windowIconified()) {
					break;
				}
			}
		}

	}

	@Override
	public void windowMaximized() {
		for (Event event : this.eventContainer.getEvents()) {
			if (event.active) {
				if (event.windowMaximized()) {
					break;
				}
			}
		}
	}

	@Override
	public void windowFocus(boolean focus) {
		for (Event event : this.eventContainer.getEvents()) {
			if (event.active) {
				if (event.windowFocus(focus)) {
					break;
				}
			}
		}
	}

	@Override
	public void windowResized(int width, int height) {
		for (Event event : this.eventContainer.getEvents()) {
			if (event.active) {
				if (event.windowResized(width, height)) {
					break;
				}
			}
		}
	}

	@Override
	public void windowDrop(String[] paths) {
		for (Event event : this.eventContainer.getEvents()) {
			if (event.active) {
				if (event.windowDrop(paths)) {
					break;
				}
			}
		}
	}
}
