package core;

import common.Console;
import common.FrameCounter;
import common.utilities.NativeObjectManager;

import core.debug.DebugManager;
import core.debug.DebugManagerSettings;
import graphics.Color;
import graphics.renderer2d.Renderer2D;

/**
 * Simple game application for curio-engine. Implements {@link Runnable},
 * {@link GameCycle}.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public abstract class BasicGame extends Program implements GameCycle {
	private Renderer2D guiRenderer;

	private FrameCounter counter;

	private boolean exitRequested = false;

	public DebugManagerSettings debugManagerSettings = new DebugManagerSettings();

	protected boolean fixedUpdate = true;

	protected BasicGame() {
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
		DebugManager.applySettings(this.debugManagerSettings);

		this.counter = new FrameCounter(window.getTime(), 60, new Runnable() {
			@Override
			public void run() {
				DebugManager.print();
			}
		});

		this.setup();

		while (!this.window.closeRequest() && !this.exitRequested) {
			this.earlyUpdate();

			while (this.counter.doFixedUpdate(window.getTime()) && fixedUpdate) {
				this.fixedUpdate((float) this.counter.getDeltaTime());
				this.counter.advanceFixedUpdate();
			}
			this.window.pollEvents();

			this.update();

			this.lateUpdate();

			renderAndSwap();

			this.counter.count(window.getTime());
		}

		Console.fine("");
		this.terminate();
		NativeObjectManager.terminateAll();
	}

	private void renderAndSwap() {
		this.guiRenderer.bind();

		this.guiRenderer.beginScene();
		this.onGUIRender(this.guiRenderer);
		this.guiRenderer.endScene();
		
		this.window.swapBuffers();
		this.guiRenderer.clear();
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
	public void terminate() {
	}

	@Override
	public void onGUIRender(Renderer2D renderer) {
	}

	public final void registerFrameCounter() {
		DebugManager.register(this.counter);
	}

	public final void registerGUIRenderer() {
		DebugManager.register(this.guiRenderer);
	}

	public final void applySettings() {
		super.applySettings();
		this.guiRenderer.setSize(this.windowSettings.width, this.windowSettings.height);
		DebugManager.applySettings(debugManagerSettings);
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

	/**
	 * 
	 * @return the frames per second.
	 */
	public final int getFPS() {
		return this.counter.getFPS();
	}
}
