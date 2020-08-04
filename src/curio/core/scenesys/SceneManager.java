package core.scenesys;

import java.util.ArrayList;

import common.Console;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import core.SceneBasedGame;
import core.events.EventHandler;

/**
 * Scene manager class for curio-engine. Default scene handler for
 * {@link SceneBasedGame}.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public final class SceneManager implements NativeObject {
	private ArrayList<Scene> sceneList = new ArrayList<Scene>();

	private Scene activeScene;
	private SceneBasedGame sceneBasedGame;

	private static SceneManager instance;

	/**
	 * 
	 * @return the singleton instance of the {@link SceneManager}
	 */
	public static SceneManager getInstance() {
		if (instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}

	private SceneManager() {
		NativeObjectManager.register(this);
	}

	/**
	 * Register the given scene to SceneManager.
	 * 
	 * @param scene the scene to register.
	 */
	public final void register(Scene scene) {
		this.sceneList.add(scene);
		Console.info(this, "New Scene registered with ID: " + this.sceneList.indexOf(scene));
	}

	/**
	 * Checks the scene activity by calling {@link Scene#activity(SceneManager)}.
	 */
	public final void checkActivity() {
		for (Scene scene : sceneList) {
			scene.activity(this);
		}
	}

	/**
	 * Changes the scene to given Scene index and sets the {@link EventContainer} in
	 * the {@link EventHandler#setEventContainer(EventContainer)} to changed scene.
	 * 
	 * @see EventHandler
	 * @see EventContainer
	 * 
	 * @param index index of the scene.
	 * @return the changed {@link Scene}.
	 */
	public final Scene change(int index) {
		return change(get(index));
	}

	public final Scene change(Scene scene) {
		this.activeScene = scene;
		EventHandler.getInstance().setEventContainer(this.activeScene);
		Console.info(this, "Scene changed to " + scene);
		return this.activeScene;
	}

	/**
	 * Returns the scene in given index.
	 * 
	 * @param index the index of the scene in SceneManager.
	 * @return the scene in index.
	 */
	public final Scene get(int index) {
		return this.sceneList.get(index);
	}

	/**
	 * Checks if the scene is loaded then Loads scene by calling
	 * {@link Scene#setup()}
	 * 
	 * @see Scene
	 * 
	 * @param index : The index of the scene in SceneManager.
	 */
	public final void load(int index) {
		load(get(index));
	}

	/**
	 * Checks if the scene is loaded then Loads scene by calling
	 * {@link Scene#setup()}
	 * 
	 * @see Scene
	 * 
	 * @param index : The index of the scene in SceneManager.
	 */
	public final void load(Scene scene) {
		if (scene.isLoaded()) {
			Console.info(this, "Scene with index of " + scene + " reloaded.");
		}
		scene.setup();
	}

	/**
	 * Checks the scene if loaded first. If scene loaded then unloads the scene by
	 * calling {@link Scene#terminate()}.
	 * 
	 * @param index : The index of the scene in SceneManager.
	 */
	public final void unload(int index) {
		Scene scene = get(index);
		if (!scene.isLoaded()) {
			Console.severe(this, "Scene with ID of " + index + " never loaded.");
			return;
		}
		scene.terminate();
		Console.info(this, "Scene with ID of " + index + " unloaded.");
	}

	/**
	 * Checks the scene if loaded first. If scene loaded then unloads the scene by
	 * calling {@link Scene#terminate()}.
	 * 
	 * @param index : The index of the scene in SceneManager.
	 */
	public final void unload(Scene scene) {
		if (!scene.isLoaded()) {
			Console.severe(this, "Scene with ID of " + scene + " never loaded.");
			return;
		}
		scene.terminate();
		Console.info(this, "Scene with ID of " + scene + " unloaded.");
	}

	/**
	 * 
	 * @return the currently active {@link Scene}.
	 */
	public final Scene getActiveScene() {
		return this.activeScene;
	}

	/**
	 * Terminates all the scenes in SceneManager by calling
	 * {@link Scene#terminate()}.
	 * 
	 * @see SceneManager#unLoadScene
	 */
	@Override
	public final void terminate() {
		for (Scene scene : sceneList) {
			scene.terminate();
		}
	}

	@Override
	public String toString() {
		return "ActiveScene: ID= " + this.sceneList.indexOf(this.activeScene) + " .Scene count: "
				+ this.sceneList.size();
	}

	public final void setSceneBasedGame(SceneBasedGame sceneBasedGame) {
		this.sceneBasedGame = sceneBasedGame;
	}

	/**
	 * 
	 * @return the SceneBasedGame this manager attached to.
	 */
	public final SceneBasedGame getSceneBasedGame() {
		return sceneBasedGame;
	}
}
