package examplegame;

import core.scenesys.Scene;
import core.scenesys.SceneManager;
import graphics.Color;
import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;
import gui.GUIEntity;
import gui.GUIEnviroment;
import gui.entities.Button;
import gui.entities.Container;
import gui.entities.Label;
import gui.layouts.EdgeLayout;
import gui.utilities.Fill;
import gui.utilities.GUIEvent;

public class MainMenuScene extends Scene {
	GUIEnviroment guiEnv;

	public MainMenuScene() {
	}

	@Override
	protected void onLoad() {
		guiEnv = GUIEnviroment.createInstance(this, FontData.getLast());
		// Create new guiEntity
		Container container = new Container(128, 128, new Fill(Color.transparent));
		guiEnv.register(container);
		container.rectTransform().localPosition.set(400 - 64, 300 - 64);

		Button button = new Button(128, 64, new Fill(Color.limeGreen), new GUIEvent() {

			@Override
			public void deactivated(GUIEntity entity) {

			}

			@Override
			public void activated(GUIEntity entity) {
				SceneManager.getInstance().load(1);
				SceneManager.getInstance().change(1);
				SceneManager.getInstance().unload(0);
			}
		});
		EdgeLayout.allign(button, container, 8, 2);
		container.add(button);
		Label label = new Label(128, 64, new Fill(Color.transparent), FontData.getLast(), "Play", Color.red);
		EdgeLayout.allign(button, container, 8, 2);

		container.add(label);
		
		guiEnv.updateTransforms();
	}

	@Override
	public void update() {

	}

	@Override
	public void onScreenRender(Renderer2D renderer2D) {
		guiEnv.render(renderer2D);
	}

	@Override
	protected void onUnload() {
		System.out.println("This is a unload notification for main menu scene.");
	}

}
