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
	private Renderer2D renderer2D;
	private FrameCounter counter;

	private boolean exitRequested = false;

	public DebugManagerSettings debugManagerSettings = new DebugManagerSettings();

	protected boolean fixedUpdate = true;

	protected BasicGame() {
	}

	/**
	 * Create basic game instance from given engine settings.
	 * 
	 * @param engineSettings : The settings to apply.
	 */
	protected BasicGame(EngineSettings engineSettings) {
		this.engineSettings = engineSettings;
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

		this.renderer2D = Renderer2D.createInstance(this.engineSettings.renderer, windowSettings.width,
				windowSettings.height);

		this.setup();
		Console.fine("");

		this.counter = new FrameCounter(window.getTime(), 60, new Runnable() {
			@Override
			public void run() {
				DebugManager.print();
			}
		});

		DebugManager.applySettings(this.debugManagerSettings, this.renderer2D, this.counter);
		
		while (!this.window.closeRequest() && !this.exitRequested) {
			this.earlyUpdate();

			while (this.counter.doFixedUpdate(window.getTime()) && fixedUpdate) {
				this.fixedUpdate((float) this.counter.getDeltaTime());
				this.counter.advanceFixedUpdate();
			}
			this.window.pollEvents();

			update();

			this.lateUpdate();

			this.renderer2D.bind();
			this.renderer2D.beginScene();
			this.render2D(this.renderer2D);
			DebugManager.draw(this.renderer2D);

			this.renderer2D.endScene();

			this.window.swapBuffers();
			this.window.clear();
			this.counter.count(window.getTime());
		}

		Console.fine("");
		this.terminate();
		NativeObjectManager.terminateAll();
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

	/**
	 * Set the background color for renderer2D.
	 * 
	 * @param color
	 */
	public final void setBackground(Color color) {
		if (this.renderer2D != null) {
			this.renderer2D.setBackground(color);
		}
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
