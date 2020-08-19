package gui.utilities;

import graphics.Color;
import graphics.renderer2d.Renderer2D;
import gui.GUIEnviroment;
import gui.entities.Container;

public class Frame extends ContainerAddon {
	public static final Color frameColor = new Color(Color.black);
	public static final int frameWidth = 4;

	public Frame(GUIEnviroment enviroment, Container container) {
		super(enviroment, container);
	}

	@Override
	public void render(Renderer2D renderer) {
		int x = super.container.rectTransform().position.x;
		int y = super.container.rectTransform().position.y;
		int width = super.container.rectTransform().width;
		int height = super.container.rectTransform().height;

		renderer.fillRect(x - frameWidth, y - frameWidth, width + frameWidth * 2, frameWidth, frameColor);
		renderer.fillRect(x + width, y, frameWidth, height, frameColor);
		renderer.fillRect(x - frameWidth, y, frameWidth, height, frameColor);
		renderer.fillRect(x - frameWidth, y + height, width + frameWidth * 2, frameWidth, frameColor);
	}

	@Override
	public int getType() {
		return 1;
	}

}
