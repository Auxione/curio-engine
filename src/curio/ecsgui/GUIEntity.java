package ecsgui;

import java.util.ArrayList;

import org.joml.Vector2f;

import common.math.MathUtils;
import core.events.Event;
import core.events.EventContainer;
import ecsgui.components.GUIComponent;
import graphics.renderer2d.Renderer2D;

public class GUIEntity extends Event {
	public Vector2f position = new Vector2f();
	public Vector2f size = new Vector2f(128, 32);

	private ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();
	private boolean focus = false;
	private static GUIEntity currentFocusedGUIObject = null;

	public GUIEntity(EventContainer eventContainer) {
		super(eventContainer);
	}

	public final void add(GUIComponent guiComponent) {
		this.components.add(guiComponent);
		guiComponent.setEntity(this);
	}

	public final void applySettings() {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).applySettings();
		}
	}

	protected final void setFocus(boolean focus) {
		if (focus) {
			if (currentFocusedGUIObject != null) {
				currentFocusedGUIObject.setFocus(false);
			}
			currentFocusedGUIObject = this;
		} else {
			if (currentFocusedGUIObject == this) {
				currentFocusedGUIObject = null;
			}
		}
		this.focus = focus;
	}

	public final boolean hasFocus() {
		return focus;
	}

	public final ArrayList<GUIComponent> getComponents() {
		return components;
	}

	public final void update() {
		if (super.active == true) {
			for (int i = 0; i < components.size(); i++) {
				components.get(i).onUpdate();
			}
		}
	}

	public final void render(Renderer2D renderer2D) {
		if (super.active == true) {
			for (int i = 0; i < components.size(); i++) {
				components.get(i).onRender2D(renderer2D);
			}
		}
	}

	@Override
	public final boolean keyPressed(int key, char c) {
		if (super.active && hasFocus()) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).onKeyPressed(key, c)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public final boolean keyReleased(int key, char c) {
		if (super.active && hasFocus()) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).onKeyReleased(key, c)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public final boolean keyRepeat(int key, char c) {
		if (super.active && hasFocus()) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).onKeyRepeat(key, c)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public final boolean mousePressed(int button, int x, int y) {
		if (super.active
				&& MathUtils.RectangleContains(x, y, this.position.x, this.position.y, this.size.x, this.size.y)) {
			setFocus(true);
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).onMousePressed(button, x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public final boolean mouseReleased(int button, int x, int y) {
		if (super.active && hasFocus()
				&& MathUtils.RectangleContains(x, y, this.position.x, this.position.y, this.size.x, this.size.y)) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).onMouseReleased(button, x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public final boolean mouseRepeat(int button, int x, int y) {
		if (super.active && hasFocus()
				&& MathUtils.RectangleContains(x, y, this.position.x, this.position.y, this.size.x, this.size.y)) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).onMouseRepeat(button, x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public final boolean mouseWheelChanged(int x, int y) {
		if (super.active && hasFocus()
				&& MathUtils.RectangleContains(x, y, this.position.x, this.position.y, this.size.x, this.size.y)) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).onMouseWheelChanged(x, y)) {
					return true;
				}
			}
		}
		return false;
	}
}
