package common.geom;

import org.joml.Vector2f;

public class ShapeFactory {
	public static final int CIRCLEPOINTS = 360;
	private static ShapeFactory instance;
	private Vector2f[] points;

	private ShapeFactory() {

	}

	public static ShapeFactory getInstance() {
		if (ShapeFactory.instance == null) {
			ShapeFactory.instance = new ShapeFactory();
		}
		return instance;
	}

	public void add(float value) {
		for (int i = 0; i < this.points.length; i++) {
			this.points[i].add(value, value);
		}
	}

	public void add(float x, float y) {
		for (int i = 0; i < this.points.length; i++) {
			this.points[i].add(x, y);
		}
	}

	public void sub(float value) {
		for (int i = 0; i < this.points.length; i++) {
			this.points[i].sub(value, value);
		}
	}

	public void mult(float value) {
		for (int i = 0; i < this.points.length; i++) {
			this.points[i].mul(value);
		}
	}

	public void mult(float x, float y) {
		for (int i = 0; i < this.points.length; i++) {
			this.points[i].mul(x, y);
		}
	}

	public void div(float value) {
		for (int i = 0; i < this.points.length; i++) {
			this.points[i].div(value);
		}
	}

	public Vector2f[] getResult() {
		return this.points;
	}

	public void clean() {
		this.points = null;
	}

	public void createCircle() {
		createCircularOrbit(CIRCLEPOINTS);
	}

	public Polygon getResultAsPolygon() {
		return new Polygon(getResult());
	}

	public void createCircularOrbit(int points) {
		this.points = new Vector2f[points];
		float angles = 360 / points;
		for (int i = 0; i < points; i++) {
			this.points[i] = new Vector2f((float) (Math.cos(i * Math.toRadians(angles))),
					(float) (Math.sin(i * Math.toRadians(angles))));
		}
	}

	public void createTriangle() {
		createCircularOrbit(3);
	}
}
