package ecsgui.components;

import common.utilities.TextInput;
import core.events.EventContainer;
import graphics.renderer2d.Renderer2D;

public class GUITextfieldComponent extends GUIComponent {
	private TextInput textInput;

	public GUITextfieldComponent(EventContainer eventContainer) {
		textInput = new TextInput(eventContainer);
	}

	public GUITextfieldComponent(EventContainer eventContainer, Runnable runnable) {
		textInput = new TextInput(eventContainer, runnable);
	}

	public String getText() {
		return textInput.getString();
	}

	public void setText(String text) {
		textInput.setText(text);
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onRender2D(Renderer2D renderer2d) {
	}

	@Override
	public void applySettings() {
	}

	@Override
	public boolean onKeyPressed(int key, char c) {
		this.textInput.keyPressed(key, c);
		return true;
	}

	@Override
	public boolean onKeyReleased(int key, char c) {
		this.textInput.keyReleased(key, c);
		return true;
	}

	@Override
	public boolean onKeyRepeat(int key, char c) {
		this.textInput.keyRepeat(key, c);
		return true;
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
