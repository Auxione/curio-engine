package gui.entities;

import graphics.Color;
import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;
import graphics.renderer2d.StringRenderer;
import gui.GUIEntity;
import gui.utilities.Fill;

public class Label extends GUIEntity {
	public String labelString;
	public Color labelColor;

	public StringRenderer stringRenderer;

	public Label(int width, int height, Fill background, FontData fontdata, String labelString, Color labelColor) {
		super(width, height, background);
		this.labelString = labelString;
		this.labelColor = labelColor;
		this.stringRenderer = new StringRenderer(fontdata);
	}

	@Override
	public void onGUIUpdate(Renderer2D renderer2d) {
		this.stringRenderer.render(renderer2d, this.labelString, this.labelColor, rectTransform().position.x,
				rectTransform().position.y);
	}

	@Override
	public void onTransformApply() {
		this.stringRenderer.maxTextWidth = (int) rectTransform().width;
		this.stringRenderer.maxTextHeight = (int) rectTransform().height;
		this.stringRenderer.charHorizontalOffset = 0;
		this.stringRenderer.charVerticalOffset = 0;
	}
}
