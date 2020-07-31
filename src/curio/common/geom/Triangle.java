package common.geom;

import org.joml.Vector2f;

import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.LineRenderer;
import graphics.renderer2d.Renderer2D;

public class Triangle extends Shape2 implements DebugObject {
	public Vector2f a = new Vector2f();
	public Vector2f b = new Vector2f();
	public Vector2f c = new Vector2f();
	private final int[] axisIndices = new int[] { 0, 1, 1, 2, 2, 0 };

	public Triangle(Vector2f a, Vector2f b, Vector2f c) {
		super(3);
		this.a.set(a);
		this.b.set(b);
		this.c.set(c);
		reBuild();
	}

	public Triangle(float ax, float ay, float bx, float by, float cx, float cy) {
		super(3);
		this.a.set(ax, ay);
		this.b.set(bx, by);
		this.c.set(cx, cy);
		reBuild();
	}

	public Triangle(Triangle source) {
		super(source);
		this.a.set(source.a);
		this.b.set(source.b);
		this.c.set(source.c);
	}

	@Override
	public boolean contains(float x, float y) {
		return contains(super.getPoints()[0], super.getPoints()[1], super.getPoints()[2], x, y);
	}

	public static boolean contains(Triangle triangle, float pointx, float pointy) {
		return contains(triangle.a, triangle.b, triangle.c, pointx, pointy);
	}

	private static float sign(float x, float y, Vector2f p2, Vector2f p3) {
		return (x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (y - p3.y);
	}

	public static boolean contains(Vector2f a, Vector2f b, Vector2f c, float x, float y) {
		float d1, d2, d3;
		boolean has_neg, has_pos;

		d1 = sign(x, y, a, b);
		d2 = sign(x, y, b, c);
		d3 = sign(x, y, c, a);

		has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
		has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

		return !(has_neg && has_pos);
	}

	public void draw(Renderer2D renderer2d, float width, Color color) {
		LineRenderer.render(renderer2d, width, super.modifiedPoints[0], super.modifiedPoints[1], color);
		LineRenderer.render(renderer2d, width, super.modifiedPoints[1], super.modifiedPoints[2], color);
		LineRenderer.render(renderer2d, width, super.modifiedPoints[2], super.modifiedPoints[0], color);
	}

	@Override
	public void reBuild() {
		super.modifiedPoints[0].set(a);
		super.modifiedPoints[1].set(b);
		super.modifiedPoints[2].set(c);
		super.reBuild();
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
		sb.append("[x1: ").append(super.modifiedPoints[0].x).append(" y1: ").append(super.modifiedPoints[0].y);
		sb.append(" x2: ").append(super.modifiedPoints[1].x).append(" y2: ").append(super.modifiedPoints[1].y);
		sb.append(" x3: ").append(super.modifiedPoints[2].x).append(" y3: ").append(super.modifiedPoints[2].y)
				.append("]");
		return sb.toString();
	}
}
