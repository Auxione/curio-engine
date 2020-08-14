package common.geom;

import org.joml.Vector2f;

import graphics.Color;
import graphics.renderer2d.LineRenderer;
import graphics.renderer2d.Renderer2D;

public class Polygon extends Shape2 {
	private int currentVertexIndex = 0;
	private boolean shaping = false;

	private Vector2f[] defaultPoints;

	public Polygon(int points) {
		super(points);

		this.defaultPoints = new Vector2f[points];
		for (int i = 0; i < this.defaultPoints.length; i++) {
			this.defaultPoints[i] = new Vector2f();
		}
	}

	public Polygon(Polygon source) {
		super(source);
		this.defaultPoints = new Vector2f[source.defaultPoints.length];
		for (int i = 0; i < this.defaultPoints.length; i++) {
			this.defaultPoints[i] = new Vector2f(source.defaultPoints[i].x, source.defaultPoints[i].y);
		}
	}

	public Polygon(Vector2f[] points) {
		super(points.length);

		this.defaultPoints = new Vector2f[points.length];
		for (int i = 0; i < this.defaultPoints.length; i++) {
			this.defaultPoints[i] = new Vector2f();
		}
		beginShape();
		add(points);
		endShape();
	}

	public final void beginShape() {
		this.shaping = true;
		this.currentVertexIndex = 0;
	}

	public final void add(Vector2f vector2f) {
		if (!shaping) {
			return;
		}
		this.defaultPoints[currentVertexIndex].set(vector2f);
		this.currentVertexIndex += 1;
	}

	public final void add(Vector2f[] vector2f) {
		if (!shaping) {
			return;
		}
		for (int i = 0; i < vector2f.length; i++) {
			add(vector2f[i]);
		}
	}

	public final void endShape() {
		this.shaping = false;
		reBuild();
	}

	@Override
	public void reBuild() {
		for (int i = 0; i < this.defaultPoints.length; i++) {
			super.defaultPoints[i].set(this.defaultPoints[i]);
		}
		super.reBuild();
	}

	public void draw(Renderer2D renderer2d, float width, Color color) {
		for (int i = 0; i < super.defaultPoints.length - 1; i++) {
			LineRenderer.render(renderer2d, width, super.defaultPoints[i], super.defaultPoints[i + 1], color);
		}
		LineRenderer.render(renderer2d, width, super.defaultPoints[super.defaultPoints.length - 1],
				super.defaultPoints[0], color);
	}

	@Override
	public boolean contains(float x, float y) {
		for (int i = 0; i < super.getDefaultPoints().length - 1; i++) {
			if (Triangle.contains(super.center, super.getDefaultPoints()[i], super.getDefaultPoints()[i + 1], x, y)) {
				return true;
			}
		}
		if (Triangle.contains(super.center, super.getDefaultPoints()[super.getDefaultPoints().length - 1], super.getDefaultPoints()[0], x,
				y)) {
			return true;
		}
		return false;
	}

	@Override
	public int[] getAxisIndices() {
		return null;
	}
}
