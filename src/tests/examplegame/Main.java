package examplegame;

import java.awt.Font;

import core.EngineSettings;
import core.SceneBasedGame;
import core.scenesys.SceneManager;
import graphics.Color;
import graphics.renderer2d.FontData;

public class Main extends SceneBasedGame {

	public static void main(String[] args) {
		EngineSettings engineSettings = new EngineSettings();

		// Set parameters
		engineSettings.renderer = EngineSettings.RENDERER_OPENGL;
		engineSettings.window = EngineSettings.WINDOW_GLFW;
		engineSettings.audio = EngineSettings.AUDIO_OPENAL;

		Main exampleGame = new Main(engineSettings);

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

	public Main(EngineSettings engineSettings) {
		super(engineSettings);
	}

	public static int score = 0;
	public static int lifes = 3;
	
	@Override
	public void setup() {
		// set the background to white color.
		setBackground(Color.white);

		// Create new font to display score.
		FontData.createFontDataFromAWT(new Font("Arial", Font.PLAIN, 20));
		new MainMenuScene();
		new PlayScene();

		// change to scene in index 0
		SceneManager.getInstance().load(0);
		SceneManager.getInstance().change(0);
	}
}
