package ecsgui.components;

import core.Input;
import ecsgui.GUIComponentEvent;
import graphics.renderer2d.Renderer2D;

public class GUIButtonComponent extends GUIComponent {
	private GUIComponentEvent guiEvent;

	public GUIButtonComponent(GUIComponentEvent guiEvent) {
		this.guiEvent = guiEvent;
	}

	@Override
	public boolean onMousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_BUTTON_LEFT) {
			guiEvent.componentActivated(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean onMouseReleased(int button, int x, int y) {
		if (button == Input.MOUSE_BUTTON_LEFT) {
			guiEvent.componentDeactivated(this);
			return true;
		}
		return false;
	}

	@Override
	public void applySettings() {
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onRender2D(Renderer2D renderer2D) {
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
	public boolean onMouseRepeat(int button, int x, int y) {
		return false;
	}
}
