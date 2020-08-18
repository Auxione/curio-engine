package examplegame;

import common.math.MathUtils;
import common.math.Transform2;
import core.scenesys.Scene;
import core.scenesys.SceneManager;
import graphics.Color;

import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;
import graphics.renderer2d.StringRenderer;
import physics.Ray2;
import physics.collision.SAT2;

public class PlayScene extends Scene {
	public PlayerRocket procket;
	public Asteroid[] asteroids;

	public StringRenderer scoreDisplay;

	public static int windowWidth;
	public static int windowHeight;

	@Override
	public void onLoad() {
		Main.lifes = 3;
		Main.score = 0;

		// Get window width and height from window settings.
		PlayScene.windowWidth = SceneManager.getInstance().getSceneBasedGame().windowSettings.width;
		PlayScene.windowHeight = SceneManager.getInstance().getSceneBasedGame().windowSettings.height;

		// create new player rocket entity.
		this.procket = new PlayerRocket();

		// set its position to center of the window.
		this.procket.transform.localPosition.set(windowWidth / 2, windowHeight / 2);

		// create new StringRenderer object with last created fontData to render score
		// in game.
		this.scoreDisplay = new StringRenderer(FontData.getLast());

		// init 10 asteroids to the gameworld.
		this.asteroids = new Asteroid[10];

		for (int i = 0; i < asteroids.length; i++) {
			this.asteroids[i] = new Asteroid();
			spawnRandom(this.asteroids[i]);
		}
	}

	@Override
	public void update() {
	}

	@Override
	public void fixedUpdate(float deltaTime) {
		// update the rocket physics with fixedUpdate
		this.procket.fixedUpdate(deltaTime);

		// check the rocket if it out of screen space
		teleport(this.procket.transform);

		for (int i = 0; i < asteroids.length; i++) {
			Asteroid asteroid = asteroids[i];

			// update the asteroid physics
			asteroid.fixedUpdate(deltaTime);

			// Screen space check for asteroid
			teleport(asteroid.transform);

			// Calculate collisions with simple circle collision method. If rocket and
			// asteroid collided kill the asteroid and increase score by 1.
			// Spawn new asteroid in random position
			if (SAT2.check(this.procket.rectangle, asteroid.rectangle)) {
				Main.lifes--;
				if (Main.lifes <= 0) {
					SceneManager.getInstance().load(0);
					SceneManager.getInstance().change(0);
					Ray2.clear();
				}
				spawnRandom(asteroid);
				this.procket.spawn(windowWidth / 2, windowHeight / 2);
			}
		}
	}

	public static void spawnRandom(Asteroid asteroid) {
		asteroid.spawn(MathUtils.random(0, windowWidth), MathUtils.random(0, windowHeight));
	}

	public void teleport(Transform2 transform2) {
		if (transform2.worldPosition.x > windowWidth) {
			transform2.localPosition.x = 0;
		} else if (transform2.worldPosition.x < 0) {
			transform2.localPosition.x = windowWidth;
		}

		if (transform2.worldPosition.y > windowHeight) {
			transform2.localPosition.y = 0;
		} else if (transform2.worldPosition.y < 0) {
			transform2.localPosition.y = windowHeight;
		}
	}

	@Override
	public void onScreenRender(Renderer2D renderer2D) {
		// render rocket and asteroids
		this.procket.render(renderer2D);
		for (int i = 0; i < asteroids.length; i++) {
			this.asteroids[i].render(renderer2D);
		}
		// render the score in top left of the screen with darkRed color
		this.scoreDisplay.render(renderer2D, (" Lifes: " + Main.lifes + " Score: " + Main.score), Color.darkRed, 0, 0);
	}
}
