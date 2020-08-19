package gui.entities;

import common.math.MathUtils;
import graphics.Color;
import graphics.renderer2d.Renderer2D;
import gui.GUIEntity;
import gui.utilities.Fill;

public class Bar extends GUIEntity {
	public Color barColor;
	private float ratio;

	public Bar(int width, int height, Fill background, float ratio, Color barColor) {
		super(width, height, background);
		set(ratio);
		this.barColor = new Color(barColor);
	}

	public void set(float ratio) {
		this.ratio = MathUtils.clamp(ratio, 0.0f, 1.0f);
	}

	public final float getRatio() {
		return this.ratio;
	}

	@Override
	public void onGUIUpdate(Renderer2D renderer2d) {
		renderer2d.fillRect(super.rectTransform().position.x, super.rectTransform().position.y,
				super.rectTransform().width * ratio, super.rectTransform().height, this.barColor);
	}

}
