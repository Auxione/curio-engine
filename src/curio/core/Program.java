package core;

import java.util.ArrayList;
import java.util.logging.Level;

import common.Console;
import common.utilities.NativeObjectManager;
import core.events.Event;
import core.events.EventContainer;
import core.events.EventHandler;

public abstract class Program implements Runnable, EventContainer {
	public Input input;
	public Window window;

	public WindowSettings windowSettings = new WindowSettings();
	public EngineSettings engineSettings = new EngineSettings();

	private ArrayList<Event> eventList = new ArrayList<Event>();

	public Program() {
		Console.createInstance(Level.ALL);
	}

	@Override
	public void registerEvent(Event event) {
		this.eventList.add(event);
		Console.fine(this, "New Event " + event + " registered.");
	}

	@Override
	public ArrayList<Event> getEvents() {
		return eventList;
	}

	protected void initWindow() {
		this.window = Window.createInstance(this.engineSettings.window);
		this.input = Input.createInstance(this.engineSettings.window);

		this.window.run(windowSettings);
		this.input.run(this.window);

		EventHandler.getInstance().setEventContainer(this);

		this.input.setListener(EventHandler.getInstance());
		this.window.setListener(EventHandler.getInstance());
		Event.set(this.input, this.window);
	}

	/**
	 * Run the application.
	 * 
	 */
	@Override
	public void run() {
		initWindow();
		setup();
		while (!this.window.closeRequest()) {
			this.window.pollEvents();

			update();

			this.window.swapBuffers();
			this.window.clear();
		}
		NativeObjectManager.terminateAll();
	}

	public abstract void setup();

	public abstract void update();
}
