package tests;

import common.Console;
import core.BasicGame;
import graphics.Color;
import graphics.renderer2d.Renderer2D;

/**
 * curio-engine benchmark. Updated 27/07/2020
 * 
 * @author Cem05
 */
public class Benchmark extends BasicGame {

	public static void main(String[] args) {
		Benchmark perfTest = new Benchmark();
		perfTest.windowSettings.vsync = false;
		perfTest.debugManagerSettings.debugPrint = true;
		perfTest.debugManagerSettings.log = true;
		perfTest.run();
	}

	private int maxQuadsX = 0;
	private int maxQuadsY = 0;
	private int resolution = 2;

	private int index = 0;
	private int tick = 0;
	private boolean reverse = true;

	@Override
	public void setup() {
		maxQuadsX = windowSettings.width / resolution;
		maxQuadsY = windowSettings.height / resolution;
		Console.info(this, "Rendering " + maxQuadsX * maxQuadsY + " textured triangles per frame.");
		registerFrameCounter();
	}

	@Override
	public void update() {
	}

	@Override
	public void fixedUpdate(float deltaTime) {
		if (tick >= 4) {
			exit();
		}
		if (reverse) {
			index++;
		} else {
			index--;
		}

		if (index > 255) {
			reverse = false;
		} else if (index < 0) {
			reverse = true;
			tick += 1;
		}
	}

	Color color = Color.black;

	@Override
	public void terminate() {

	}

	@Override
	public void onGUIRender(Renderer2D renderer2d) {
		for (int x = 0; x < maxQuadsX; x++) {
			for (int y = 0; y < maxQuadsY; y++) {
				color.set(index, 255 - index, 255 - index);

				renderer2d.fillRect(x * resolution, y * resolution, resolution, resolution, color);
			}
		}
	}
}
