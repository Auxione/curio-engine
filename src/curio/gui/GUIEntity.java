package gui;

import graphics.renderer2d.Renderer2D;
import gui.utilities.Allignable;
import gui.utilities.Fill;
import gui.utilities.RectTransform;

public abstract class GUIEntity implements Allignable {
	private boolean active = true;
	private final RectTransform rectTransform;
	public GUIEnviroment enviroment;

	public boolean drawBackgorund = true;
	public final Fill background;

	public GUIEntity(int width, int height, Fill background) {
		this.rectTransform = new RectTransform(width, height);
		this.background = background;
	}

	public final void render(Renderer2D renderer2d) {
		if (!isActive()) {
			return;
		}

		if (drawBackgorund) {
			renderer2d.fillRect(this.rectTransform.position.x, this.rectTransform.position.y, this.rectTransform.width,
					this.rectTransform.height, this.background.color, this.background.texture,
					this.background.textureCoordinate);
		}
		this.onGUIUpdate(renderer2d);
	}

	public final boolean contains(int x, int y) {
		return rectTransform.contains(x, y);
	}

	public final void reverseActiveState() {
		if (this.isActive()) {
			deactivate();
		} else {
			activate();
		}
	}

	public final void activate() {
		this.active = true;
		updateTransform();
		this.onActivation();
	}

	public final void deactivate() {
		this.active = false;
		this.onDeactivation();
	}

	public final void updateTransform() {
		this.rectTransform.update();
		this.onTransformApply();
	}

	public final boolean isActive() {
		return active;
	}

	@Override
	public RectTransform rectTransform() {
		return this.rectTransform;
	}

	public final boolean keyPressed(int key, char c) {
		if (!isActive()) {
			return false;
		}
		return this.onKeyPressed(key, c);
	}

	public final boolean keyReleased(int key, char c) {
		if (!isActive()) {
			return false;
		}
		return this.onKeyReleased(key, c);
	}

	public final boolean keyRepeat(int key, char c) {
		if (!isActive()) {
			return false;
		}
		return this.onKeyRepeat(key, c);
	}

	public final boolean mousePressed(int button, int x, int y) {
		if (!isActive()) {
			return false;
		}
		return this.onMousePressed(button, x, y);
	}

	public final boolean mouseReleased(int button, int x, int y) {
		if (!isActive()) {
			return false;
		}
		return this.onMouseReleased(button, x, y);
	}

	public final boolean mouseRepeat(int button, int x, int y) {
		if (!isActive()) {
			return false;
		}
		return this.onMouseRepeat(button, x, y);
	}

	public final boolean mouseWheelChanged(int x, int y) {
		if (!isActive()) {
			return false;
		}
		return this.onMouseWheelChanged(x, y);
	}

	protected void onActivation() {
	}

	protected void onDeactivation() {
	}

	protected void onTransformApply() {
	}

	protected boolean onKeyPressed(int key, char c) {
		return false;
	}

	protected boolean onKeyReleased(int key, char c) {
		return false;
	}

	protected boolean onKeyRepeat(int key, char c) {
		return false;
	}

	protected boolean onMousePressed(int button, int x, int y) {
		return false;
	}

	protected boolean onMouseReleased(int button, int x, int y) {
		return false;
	}

	protected boolean onMouseRepeat(int button, int x, int y) {
		return false;
	}

	protected boolean onMouseWheelChanged(int x, int y) {
		return false;
	}

	protected abstract void onGUIUpdate(Renderer2D renderer2d);

}
