package common.geom;

import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.LineRenderer;
import graphics.renderer2d.Renderer2D;

public class Rectangle extends Shape2 implements DebugObject {
	public float width, height;

	private final int[] axisIndices = new int[] { 0, 1, 1, 2 };

	public Rectangle(float width, float height) {
		super(4);
		this.width = width;
		this.height = height;
		reBuild();
	}

	public Rectangle(Rectangle source) {
		super(4);
		this.width = source.width;
		this.height = source.height;
		reBuild();
	}

	@Override
	public void reBuild() {
		super.defaultPoints[0].set(-width / 2, -height / 2);
		super.defaultPoints[1].set(+width / 2, -height / 2);
		super.defaultPoints[2].set(+width / 2, +height / 2);
		super.defaultPoints[3].set(-width / 2, +height / 2);
		super.reBuild();
	}

	@Override
	public boolean contains(float x, float y) {
		return contains(this, x, y);
	}

	public static boolean contains(float posx, float posy, float width, float height, float x, float y) {
		return (x >= posx - width / 2 && x <= posx + width / 2 && y >= posy - height / 2 && y <= posy + height / 2);
	}

	public static boolean contains(Rectangle rectangle, float x, float y) {
		if (Triangle.contains(rectangle.getDefaultPoints()[0], rectangle.getDefaultPoints()[1], rectangle.getDefaultPoints()[2], x, y)) {
			return true;
		}
		if (Triangle.contains(rectangle.getDefaultPoints()[2], rectangle.getDefaultPoints()[3], rectangle.getDefaultPoints()[0], x, y)) {
			return true;
		}
		return false;
	}

	public void draw(Renderer2D renderer2d, float width, Color color) {
		LineRenderer.render(renderer2d, width, super.defaultPoints[0], super.defaultPoints[1], color);
		LineRenderer.render(renderer2d, width, super.defaultPoints[1], super.defaultPoints[2], color);
		LineRenderer.render(renderer2d, width, super.defaultPoints[2], super.defaultPoints[3], color);
		LineRenderer.render(renderer2d, width, super.defaultPoints[3], super.defaultPoints[0], color);
	}

	@Override
	public int[] getAxisIndices() {
		return axisIndices;
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, super.defaultPoints[0].x,
				super.defaultPoints[0].y);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[width: ").append(this.width).append(" height: ").append(this.height).append("]");
		return sb.toString();
	}
}
