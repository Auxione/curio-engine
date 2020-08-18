package examplegame;

import java.awt.Font;

import core.EngineSettings;
import core.SceneBasedGame;
import core.scenesys.SceneManager;
import graphics.Blending;
import graphics.Color;
import graphics.renderer2d.FontData;

public class Main extends SceneBasedGame {

	public static void main(String[] args) {
		Main exampleGame = new Main();

		// Set parameters
		EngineSettings.renderer = EngineSettings.RENDERER_OPENGL;
		EngineSettings.window = EngineSettings.WINDOW_GLFW;
		EngineSettings.audio = EngineSettings.AUDIO_OPENAL;

		exampleGame.windowSettings.title = "ExampleGame";
		exampleGame.windowSettings.width = 800;
		exampleGame.windowSettings.height = 600;
		exampleGame.windowSettings.monitorPositionX = 100;
		exampleGame.windowSettings.monitorPositionY = 50;
		exampleGame.windowSettings.samples = 0;

		exampleGame.windowSettings.refreshRate = 60;
		exampleGame.windowSettings.vsync = false;

		exampleGame.windowSettings.resizeable = false;
		exampleGame.windowSettings.fullscreen = false;

		// run the application with the parameters
		exampleGame.run();
	}

	public Main() {
	}

	public static int score = 0;
	public static int lifes = 3;

	@Override
	public void setup() {
		// set the background to white color.
		setBackground(Color.white);

		// Create new font to display score.
		FontData.createFromAWT(new Font("Arial", Font.PLAIN, 20));
		new MainMenuScene();
		new PlayScene();
		Blending.getInstance().set(true);
		// change to scene in index 0
		SceneManager.getInstance().load(0);
		SceneManager.getInstance().change(0);
	}
}
