package common.geom;

import org.joml.Vector2f;

import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.Renderer2D;

public class Circle extends Shape2 implements DebugObject {
	public float radius;

	public Circle() {
		super(0);
	}

	public Circle(float radius) {
		super(0);
		this.radius = radius;
	}

	@Override
	public boolean contains(float x, float y) {
		return contains(super.defaultPoints[0], radius, x, y);
	}

	public static boolean contains(Circle circle, float x, float y) {
		return contains(circle.getDefaultPoints()[0], circle.radius, x, y);
	}

	public static boolean contains(Vector2f position, float radius, float x, float y) {
		return (Vector2f.distance(position.x, position.y, x, y) <= (radius / 2));
	}

	public static boolean contains(Circle circle1, Circle circle2) {
		return (circle1.getDefaultPoints()[0].distance(circle2.getDefaultPoints()[0]) <= (circle1.radius / 2 + circle2.radius / 2));
	}

	@Override
	public int[] getAxisIndices() {
		return null;
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, super.defaultPoints[0].x,
				super.defaultPoints[0].y);
		renderer2d.fillRectCentered(super.defaultPoints[0].x, super.defaultPoints[0].y, 2, 2, color);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[radius: ").append(radius).append("]");
		return sb.toString();
	}

}
