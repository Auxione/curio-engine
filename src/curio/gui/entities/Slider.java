package gui.entities;

import common.math.MathUtils;
import core.Input;
import core.events.Event;
import graphics.renderer2d.Renderer2D;
import gui.GUIEntity;
import gui.utilities.Fill;

public class Slider extends GUIEntity {
	private boolean calculating = false;
	private Runnable runnable;
	private float ratio, dX;
	private int sliderWidth = 2;

	public Slider(int width, int height, Fill background, float ratio, Runnable runnable) {
		super(width, height, background);
		this.ratio = ratio;
		this.runnable = runnable;
	}

	@Override
	protected boolean onMousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_BUTTON_LEFT) {
			this.calculating = true;
			return true;
		}
		return false;
	}

	@Override
	protected boolean onMouseReleased(int button, int x, int y) {
		if (this.calculating) {
			this.calculating = false;
			return true;
		}
		return false;
	}

	@Override
	protected void onGUIUpdate(Renderer2D renderer2d) {
		if (calculating) {
			this.dX = Event.getInput().getMouseX() - super.rectTransform().position.x;
			this.ratio = this.dX / super.rectTransform().width;
			this.ratio = MathUtils.clamp(this.ratio, 0.0f, 1.0f);
			runnable.run();
		}

		renderer2d.fillRect(super.rectTransform().position.x + super.rectTransform().width * ratio - sliderWidth / 2,
				super.rectTransform().position.y, sliderWidth, super.rectTransform().height);
	}

	public void setRatio(float ratio) {
		this.ratio = MathUtils.clamp(ratio, 0.0f, 1.0f);
	}

	public float getRatio() {
		return ratio;
	}
}
