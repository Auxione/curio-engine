package gui.utilities;

import java.util.ArrayList;
import org.joml.Vector2i;

import core.debug.DebugDraw;
import core.debug.DebugManager;
import core.debug.DebugPrint;
import graphics.Color;
import graphics.renderer2d.Renderer2D;

public class RectTransform implements DebugDraw, DebugPrint {
	public final Vector2i localPosition = new Vector2i();
	public int width, height;

	public final Vector2i position = new Vector2i();
	private RectTransform parent = null;
	private ArrayList<RectTransform> childs = new ArrayList<RectTransform>();

	public RectTransform(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public RectTransform(int width, int height, RectTransform parent) {
		this(width, height);
		setParent(parent);
	}

	public final void setParent(RectTransform parent) {
		this.parent = parent;
		parent.childs.add(this);
	}

	public final void removeParent() {
		parent.childs.remove(this);
		this.parent = null;
	}

	public boolean hasParent() {
		return this.parent != null;
	}

	protected void calculateParent() {
		this.position.zero();
		if (this.parent != null) {
			this.parent.calculateParent();
			this.position.add(this.parent.position);
		}
		this.position.add(localPosition);
	}

	public final void update() {
		calculateParent();
	}

	public final void reset() {
		this.localPosition.zero();
	}

	public final boolean contains(float x, float y) {
		return (x >= this.position.x && x <= this.position.x + this.width && y >= this.position.y
				&& y <= this.position.y + this.height);
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, this.localPosition.x, this.localPosition.y);
		renderer2d.fillRect(this.position.x, this.position.y, this.width, this.height, color);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Local x: ").append(this.localPosition.x).append(" y: ").append(this.localPosition.y)
				.append("][World x: ").append(this.position.x).append(" y: ").append(this.position.y).append("]");
		return sb.toString();
	}
}
