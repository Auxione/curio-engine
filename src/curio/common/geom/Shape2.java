package common.geom;

import org.joml.Vector2f;

import common.math.TransformingObject;
import physics.RaycastObject;
import physics.collision.SATObject;

public abstract class Shape2 implements TransformingObject, RaycastObject, SATObject {
	protected Vector2f[] defaultPoints;
	public Vector2f center = new Vector2f();

	public Shape2(int points) {
		this.defaultPoints = new Vector2f[points];
		for (int i = 0; i < this.defaultPoints.length; i++) {
			this.defaultPoints[i] = new Vector2f();
		}
	}

	public Shape2(Shape2 source) {
		this.defaultPoints = new Vector2f[source.defaultPoints.length];
		for (int i = 0; i < this.defaultPoints.length; i++) {
			this.defaultPoints[i] = new Vector2f(source.defaultPoints[i].x, source.defaultPoints[i].y);
		}
	}

	public final void findCenter() {
		this.center.zero();
		for (int i = 0; i < this.defaultPoints.length; i++) {
			center.add(defaultPoints[i]);
		}
		this.center.div(this.defaultPoints.length);
	}

	@Override
	public void reBuild() {
		findCenter();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.toString()).append("\n");

		for (int i = 0; i < defaultPoints.length; i++) {
			stringBuilder.append("  ").append(i).append(": ").append(defaultPoints[i].toString()).append("\n");
		}
		stringBuilder.append(super.toString());
		return stringBuilder.toString();
	}

	@Override
	public boolean contains(Vector2f vector) {
		return contains(vector.x, vector.y);
	}

	@Override
	public Vector2f[] getDefaultPoints() {
		return defaultPoints;
	}

	public abstract boolean contains(float x, float y);

	public abstract int[] getAxisIndices();

}
