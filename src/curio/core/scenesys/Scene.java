package core.scenesys;

import java.util.ArrayList;

import common.Console;
import core.GameCycle;
import core.events.Event;
import core.events.EventContainer;
import graphics.renderer2d.Renderer2D;

/**
 * Scene system for curio-engine. The scene has {@link GameCycle} and
 * {@link EventContainer} implementation. In order to add a scene to game,
 * simply extend this class. {@link SceneManager} handles the scene loading,
 * game loop and scene unloading.
 * 
 * @see SceneManager
 * @author Mehmet Cem Zarifoglu
 *
 */
public abstract class Scene implements GameCycle, EventContainer {
	private boolean loaded = false;
	private ArrayList<Event> eventList = new ArrayList<Event>();

	/**
	 * Create a Scene object and register to {@link SceneManager}.
	 * 
	 * @see SceneManager
	 */
	protected Scene() {
		SceneManager.getInstance().register(this);
	}

	/**
	 * Register the given Event to this scene.
	 * 
	 * @see Event
	 */
	@Override
	public final void registerEvent(Event event) {
		this.eventList.add(event);
		Console.fine(this, "New Event " + event + " registered.");
	}

	/**
	 * @return all the events registered to this scene in {@link ArrayList}
	 */
	@Override
	public final ArrayList<Event> getEvents() {
		return this.eventList;
	}

	/**
	 * Calls {@link onLoad} then sets the loaded flag to true. This method called
	 * automatically by loading scenes by {@link SceneManager#loadScene(int)}.
	 */
	@Override
	public final void setup() {
		this.onLoad();
		this.loaded = true;
	}

	/**
	 * Checks scene activity. This method called every game tick.
	 * 
	 * @param sceneManager
	 */
	public void activity(SceneManager sceneManager) {
	}

	@Override
	public void earlyUpdate() {
	}

	@Override
	public void fixedUpdate(float deltaTime) {
	}

	@Override
	public void lateUpdate() {
	}

	@Override
	public void renderScreen(Renderer2D renderer) {
	}

	/**
	 * terminates this scene by calling {@link onUnload()} and set the loaded flag
	 * to false. This method called automatically by unloading scenes by
	 * {@link SceneManager#unLoadScene(int)}.
	 */
	@Override
	public final void terminate() {
		this.onUnload();
		this.loaded = false;
	}

	/**
	 * Scene loading method.
	 */
	protected abstract void onLoad();

	/**
	 * Scene unloading method.
	 */
	protected void onUnload() {
	};

	/**
	 * @return true if the scene is loaded.
	 */
	public final boolean isLoaded() {
		return loaded;
	}
}
