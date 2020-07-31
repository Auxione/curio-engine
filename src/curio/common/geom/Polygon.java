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
			super.modifiedPoints[i].set(this.defaultPoints[i]);
		}
		super.reBuild();
	}

	public void calculateParallels() {
		int axisIndexCount = 0;
		if (modifiedPoints.length <= 3) {
			return;
		}

		for (int i = 0; i < modifiedPoints.length; i += 2) {
			for (int other = 0; other < modifiedPoints.length; other += 2) {
				if (modifiedPoints[i].equals(modifiedPoints[other])) {
					continue;
				}
				if (findParallel(modifiedPoints[i], modifiedPoints[i + 1], modifiedPoints[other],
						modifiedPoints[other + 1])) {
					axisIndexCount += 2;
				}
			}
		}
	}

	private static float epsilon = 0.0001f;

	public static boolean findParallel(Vector2f p11, Vector2f p12, Vector2f p21, Vector2f p22) {
		Vector2f norm1 = new Vector2f();
		p11.sub(p12, norm1);
		norm1.normalize();

		Vector2f norm2 = new Vector2f();
		p21.sub(p22, norm2);
		norm2.normalize();

		float dot = norm1.dot(norm2);
		if (dot <= (1f + epsilon) && dot >= (1f - epsilon)) {
			return true;
		}

		if (dot >= -(1f + epsilon) && dot <= -(1f - epsilon)) {
			return true;
		}

		return false;
	}

	public void draw(Renderer2D renderer2d, float width, Color color) {
		for (int i = 0; i < super.modifiedPoints.length - 1; i++) {
			LineRenderer.render(renderer2d, width, super.modifiedPoints[i], super.modifiedPoints[i + 1], color);
		}
		LineRenderer.render(renderer2d, width, super.modifiedPoints[super.modifiedPoints.length - 1],
				super.modifiedPoints[0], color);
	}

	@Override
	public boolean contains(float x, float y) {
		for (int i = 0; i < super.getPoints().length - 1; i++) {
			if (Triangle.contains(super.center, super.getPoints()[i], super.getPoints()[i + 1], x, y)) {
				return true;
			}
		}
		if (Triangle.contains(super.center, super.getPoints()[super.getPoints().length - 1], super.getPoints()[0], x,
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
