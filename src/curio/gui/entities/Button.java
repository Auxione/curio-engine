package gui.entities;

import graphics.renderer2d.Renderer2D;
import gui.GUIEntity;
import gui.utilities.Fill;
import gui.utilities.GUIEvent;

public class Button extends GUIEntity {
	private boolean isHold = false;
	private GUIEvent event;

	public boolean toggle = false;
	private boolean toggleState = false;

	public Button(int width, int height, Fill background, GUIEvent event) {
		super(width, height, background);
		this.event = event;
	}

	@Override
	protected void onGUIUpdate(Renderer2D renderer2d) {

	}

	@Override
	protected boolean onMousePressed(int button, int x, int y) {
		if (!toggle) {
			this.event.activated(this);
			this.isHold = true;
			return true;
		} else {
			toggleState = !toggleState;
			if (toggleState) {
				this.event.activated(this);
			} else {
				this.event.deactivated(this);
			}
			return true;
		}
	}

	@Override
	protected boolean onMouseReleased(int button, int x, int y) {
		if (!toggle) {
			this.event.deactivated(this);
			this.isHold = false;
			return true;
		}
		return false;
	}

	public final boolean isHold() {
		return isHold;
	}

	@Override
	protected void onDeactivation() {
		this.isHold = false;
	}
}
