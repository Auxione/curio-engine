package gui.entities;

import common.utilities.TextInput;
import core.events.Event;
import graphics.Color;
import graphics.renderer2d.FontData;
import gui.utilities.Fill;

public class Inputbox extends Label {
	private TextInput textInput;

	public Inputbox(int width, int height, Fill background, FontData fontdata, String labelString, Color color) {
		super(width, height, background, fontdata, labelString, color);
		this.textInput = new TextInput(Event.getInput());
		this.textInput.setText(labelString);
	}

	public Inputbox(int width, int height, Fill background, FontData fontdata, String labelString, Color color,
			Runnable runnable) {
		super(width, height, background, fontdata, labelString, color);
		this.textInput = new TextInput(Event.getInput(), runnable);
		this.textInput.setText(labelString);
	}

	@Override
	public boolean onKeyPressed(int key, char c) {
		boolean out = textInput.onKeyPressed(key, c);
		this.labelString = textInput.getString();
		return out;
	}

	@Override
	public boolean onKeyReleased(int key, char c) {
		return textInput.onKeyReleased(key, c);
	}

	@Override
	public boolean onKeyRepeat(int key, char c) {
		boolean out = textInput.onKeyRepeat(key, c);
		this.labelString = textInput.getString();
		return out;
	}
}
