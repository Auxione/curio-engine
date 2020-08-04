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
		super.modifiedPoints[0].set(-width / 2, -height / 2);
		super.modifiedPoints[1].set(+width / 2, -height / 2);
		super.modifiedPoints[2].set(+width / 2, +height / 2);
		super.modifiedPoints[3].set(-width / 2, +height / 2);
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
		if (Triangle.contains(rectangle.getPoints()[0], rectangle.getPoints()[1], rectangle.getPoints()[2], x, y)) {
			return true;
		}
		if (Triangle.contains(rectangle.getPoints()[2], rectangle.getPoints()[3], rectangle.getPoints()[0], x, y)) {
			return true;
		}
		return false;
	}

	public void draw(Renderer2D renderer2d, float width, Color color) {
		LineRenderer.render(renderer2d, width, super.modifiedPoints[0], super.modifiedPoints[1], color);
		LineRenderer.render(renderer2d, width, super.modifiedPoints[1], super.modifiedPoints[2], color);
		LineRenderer.render(renderer2d, width, super.modifiedPoints[2], super.modifiedPoints[3], color);
		LineRenderer.render(renderer2d, width, super.modifiedPoints[3], super.modifiedPoints[0], color);
	}

	@Override
	public int[] getAxisIndices() {
		return axisIndices;
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, super.modifiedPoints[0].x,
				super.modifiedPoints[0].y);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[width: ").append(this.width).append(" height: ").append(this.height).append("]");
		return sb.toString();
	}
}
