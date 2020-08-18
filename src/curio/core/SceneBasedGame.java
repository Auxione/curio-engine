package core;

import java.util.logging.Level;

import common.Console;
import common.FrameCounter;
import common.utilities.NativeObjectManager;
import core.debug.DebugManager;
import core.debug.DebugManagerSettings;
import core.scenesys.SceneManager;
import graphics.Color;
import graphics.renderer2d.Renderer2D;

/**
 * Scene based game application for curio-engine. Implements {@link Runnable},
 * 
 * @see SceneManager
 * @see Scene
 * @author Mehmet Cem Zarifoglu
 *
 */
public abstract class SceneBasedGame extends Program implements Runnable {
	private Renderer2D guiRenderer;
	private FrameCounter counter;

	private boolean exitRequested = false;
	public DebugManagerSettings debugManagerSettings = new DebugManagerSettings();

	public boolean fixedUpdate = true;

	/**
	 * Create scene based game instance from default engine settings.
	 */
	protected SceneBasedGame() {
		Console.createInstance(Level.ALL);
	}

	/**
	 * Run the application.
	 * 
	 */
	@Override
	public void run() {
		Console.info(this, "Launching.");
		initWindow();
		Console.fine("");

		this.guiRenderer = Renderer2D.createInstance(windowSettings.width, windowSettings.height);
		Console.fine("");

		SceneManager sceneManager = SceneManager.getInstance();
		sceneManager.setSceneBasedGame(this);

		this.setup();
		Console.fine("");

		if (sceneManager.getActiveScene() == null) {
			Console.severe(this, "No scene selected.");
			this.exitRequested = true;
		}

		this.counter = new FrameCounter(window.getTime(), 60, new Runnable() {
			@Override
			public void run() {
				DebugManager.print();
			}
		});

		DebugManager.applySettings(this.debugManagerSettings);

		while (!this.window.closeRequest() && !this.exitRequested) {
			if (sceneManager.getActiveScene() == null) {
				Console.severe(this, "Active scene is null!");
				break;
			}
			sceneManager.checkActivity();

			sceneManager.getActiveScene().earlyUpdate();
			while (this.counter.doFixedUpdate(window.getTime()) && fixedUpdate) {
				sceneManager.getActiveScene().fixedUpdate((float) this.counter.getDeltaTime());
				this.counter.advanceFixedUpdate();
			}
			this.window.pollEvents();

			sceneManager.getActiveScene().update();
			sceneManager.getActiveScene().lateUpdate();

			this.guiRenderer.bind();
			this.guiRenderer.beginScene();
			sceneManager.getActiveScene().onScreenRender(this.guiRenderer);
			DebugManager.draw(this.guiRenderer);

			this.guiRenderer.endScene();

			this.window.swapBuffers();
			this.guiRenderer.clear();
			this.counter.count(window.getTime());
		}
		Console.fine("");
		NativeObjectManager.terminateAll();
	}

	/**
	 * Not used in SceneBasedGame.
	 */
	@Override
	public void update() {

	}

	/**
	 * Set the background color for renderer2D.
	 * 
	 * @param color
	 */
	public final void setBackground(Color color) {
		this.guiRenderer.setBackground(color);
	}

	/**
	 * Exit from the game loop.
	 */
	protected final void exit() {
		this.exitRequested = true;
	}

	public final void registerFrameCounter() {
		DebugManager.register(this.counter);
	}

	public final void registerGUIRenderer() {
		DebugManager.register(this.guiRenderer);
	}

	public final void applySettings() {
		super.applySettings();
		DebugManager.applySettings(debugManagerSettings);
		this.guiRenderer.setSize(windowSettings.width, windowSettings.height);
	}

	/**
	 * 
	 * @return the frames per second.
	 */
	public final int getFPS() {
		return this.counter.getFPS();
	}
}
