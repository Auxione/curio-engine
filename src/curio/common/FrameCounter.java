package common;

import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.Renderer2D;

public class FrameCounter implements DebugObject {
	public static boolean debug = false;
	public int fixedUpdates = 60;

	private long gameTick = 0;

	private double nowTime = 0;
	private double deltaTime = 0;
	private double limitFPS = 1.0 / fixedUpdates;
	private int updates = 0;
	private int frames = 0;
	private int timer = 0;
	private double lastTime;

	private int lastFPS;

	private Runnable runnable;

	public FrameCounter(double currentTime, int fixedUpdates, Runnable runnable) {
		setFixedUpdateRate(fixedUpdates);
		this.lastTime = currentTime;
		this.timer = (int) lastTime;
		this.runnable = runnable;
	}

	public void setFixedUpdateRate(int fixedUpdates) {
		this.fixedUpdates = fixedUpdates;
		this.limitFPS = 1.0 / fixedUpdates;
	}

	public void advanceFixedUpdate() {
		this.updates++;
		this.deltaTime--;
	}

	public boolean doFixedUpdate(double currentTime) {
		this.nowTime = currentTime;
		this.deltaTime += (this.nowTime - this.lastTime) / this.limitFPS;
		this.lastTime = this.nowTime;
		return this.deltaTime >= 1.0;
	}

	public void count(double currentTime) {
		this.frames++;
		this.gameTick++;

		if (currentTime - timer > 1.0) {
			timer++;
			if (this.updates + 1 < fixedUpdates) {
				StringBuilder sb = new StringBuilder();
				Console.severe(sb.append("FixedUpdates falling behind! ").append(this.updates).append(" from ")
						.append(this.fixedUpdates).toString());
			}
			runnable.run();

			this.lastFPS = this.frames;
			this.updates = 0;
			this.frames = 0;
		}
	}

	public long getGameTick() {
		return gameTick;
	}

	public int getFPS() {
		return this.lastFPS;
	}

	public double getDeltaTime() {
		return deltaTime;
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, 32, 32);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		return sb.append("FPS: ").append(getFPS()).toString();
	}
}
