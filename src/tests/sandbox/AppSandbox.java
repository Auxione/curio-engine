package sandbox;

import core.BasicGame;
import graphics.renderer2d.Renderer2D;

public class AppSandbox extends BasicGame {
	public static void main(String[] args) {
		AppSandbox appSandbox = new AppSandbox();
		appSandbox.debugManagerSettings.debugPrint = false;
		appSandbox.debugManagerSettings.debugDraw = true;
		appSandbox.debugManagerSettings.registerFrameCounter = true;
		appSandbox.debugManagerSettings.log = false;

		appSandbox.engineSettings.renderer = 3;

		appSandbox.windowSettings.title = "Sandbox";
		appSandbox.windowSettings.width = 1280;
		appSandbox.windowSettings.height = 720;
		appSandbox.windowSettings.monitorPositionX = 10;
		appSandbox.windowSettings.monitorPositionY = 50;
		appSandbox.windowSettings.samples = 0;
		appSandbox.windowSettings.refreshRate = 60;
		appSandbox.windowSettings.vsync = false;
		appSandbox.windowSettings.fullscreen = false;
		appSandbox.windowSettings.resizeable = false;
		appSandbox.windowSettings.noAPI = false;

		appSandbox.run();
	}

	@Override
	public void setup() {

	}

	@Override
	public void update() {

	}

	@Override
	public void fixedUpdate(float deltaTime) {
	}

	@Override
	public void render2D(Renderer2D renderer2d) {
	}
}
