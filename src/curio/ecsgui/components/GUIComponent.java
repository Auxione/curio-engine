package ecsgui.components;

import ecsgui.GUIEntity;
import graphics.renderer2d.Renderer2D;

public abstract class GUIComponent {
	protected GUIEntity guiEntity;

	public final void setEntity(GUIEntity guiEntity) {
		this.guiEntity = guiEntity;
	}

	public final GUIEntity getEntity() {
		return this.guiEntity;
	}

	public abstract void onUpdate();

	public abstract void onRender2D(Renderer2D renderer2D);

	public abstract boolean onKeyPressed(int key, char c);

	public abstract boolean onKeyReleased(int key, char c);

	public abstract boolean onKeyRepeat(int key, char c);

	public abstract boolean onMouseWheelChanged(int x, int y);

	public abstract boolean onMousePressed(int button, int x, int y);

	public abstract boolean onMouseReleased(int button, int x, int y);

	public abstract boolean onMouseRepeat(int button, int x, int y);

	public abstract void applySettings();
}
