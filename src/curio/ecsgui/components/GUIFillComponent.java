package ecsgui.components;

import graphics.Color;
import graphics.Texture;
import graphics.renderer2d.Renderer2D;

public class GUIFillComponent extends GUIComponent {
	public boolean render = true;

	public Color color = Color.white;
	public Texture texture;

	public GUIFillComponent(Color color) {
		this.color = color;
	}

	public GUIFillComponent(Texture texture) {
		this.texture = texture;
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onRender2D(Renderer2D renderer2D) {
		if (this.render == true) {
			if (texture != null) {
				renderer2D.fillRect(guiEntity.position.x, guiEntity.position.y, guiEntity.size.x, guiEntity.size.y,
						this.color, this.texture);
			} else {
				renderer2D.fillRect(guiEntity.position.x, guiEntity.position.y, guiEntity.size.x, guiEntity.size.y,
						this.color);
			}
		}
	}

	@Override
	public void applySettings() {

	}

	@Override
	public boolean onKeyPressed(int key, char c) {
		return false;
	}

	@Override
	public boolean onKeyReleased(int key, char c) {
		return false;
	}

	@Override
	public boolean onKeyRepeat(int key, char c) {
		return false;
	}

	@Override
	public boolean onMouseWheelChanged(int x, int y) {
		return false;
	}

	@Override
	public boolean onMousePressed(int button, int x, int y) {
		return false;
	}

	@Override
	public boolean onMouseReleased(int button, int x, int y) {
		return false;
	}

	@Override
	public boolean onMouseRepeat(int button, int x, int y) {
		return false;
	}

}
