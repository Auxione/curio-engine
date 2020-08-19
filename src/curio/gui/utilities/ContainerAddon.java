package gui.utilities;

import graphics.renderer2d.Renderer2D;
import gui.GUIEnviroment;
import gui.entities.Container;

public abstract class ContainerAddon {
	public final Container container;
	public final GUIEnviroment enviroment;

	public ContainerAddon(GUIEnviroment enviroment, Container container) {
		this.container = container;
		this.enviroment = enviroment;
	}

	public abstract void render(Renderer2D renderer);

	public boolean mousePressed(int button, int x, int y) {
		return false;
	}

	public boolean mouseReleased(int button, int x, int y) {
		return false;
	}

	public abstract int getType();
}
