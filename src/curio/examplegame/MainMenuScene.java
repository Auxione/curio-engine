package examplegame;

import core.scenesys.Scene;
import core.scenesys.SceneManager;
import ecsgui.GUIComponentEvent;
import ecsgui.GUIEntity;
import ecsgui.GUIFactory;
import ecsgui.components.GUIComponent;
import graphics.Color;
import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;

public class MainMenuScene extends Scene {
	public GUIEntity playButton;

	public MainMenuScene() {
	}

	@Override
	protected void onLoad() {
		// create new event to call when button pressed.
		GUIComponentEvent guiComponentEvent = new GUIComponentEvent() {
			@Override
			public void componentActivated(GUIComponent source) {

			}

			@Override
			public void componentDeactivated(GUIComponent source) {
				// load new scene change to that scene and unload this.
				SceneManager.getInstance().load(1);
				SceneManager.getInstance().change(1);
				SceneManager.getInstance().unload(0);
			}
		};
		// Create new guiEntity

		playButton = GUIFactory.createButton(this, guiComponentEvent, Color.red, FontData.getLast(), "Play", 5);
		// set its position to 250, 50.
		playButton.position.set(250, 50);
	}

	@Override
	public void update() {
		// update guiEntity each frame ...
		playButton.update();
	}

	@Override
	public void render2D(Renderer2D renderer2D) {
		// ... and render it.
		playButton.render(renderer2D);
	}

	@Override
	protected void onUnload() {
		System.out.println("This is a unload notification for main menu scene.");
	}
}
