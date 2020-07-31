package common.geom;

import org.joml.Vector2f;

import common.math.TransformingObject;
import physics.RaycastObject;
import physics.collision.SATObject;

public abstract class Shape2 implements TransformingObject, RaycastObject, SATObject {
	public Vector2f[] modifiedPoints;
	public Vector2f center = new Vector2f();

	public Shape2(int points) {
		this.modifiedPoints = new Vector2f[points];
		for (int i = 0; i < this.modifiedPoints.length; i++) {
			this.modifiedPoints[i] = new Vector2f();
		}
	}

	public Shape2(Shape2 source) {
		this.modifiedPoints = new Vector2f[source.modifiedPoints.length];
		for (int i = 0; i < this.modifiedPoints.length; i++) {
			this.modifiedPoints[i] = new Vector2f(source.modifiedPoints[i].x, source.modifiedPoints[i].y);
		}
	}

	public final void findCenter() {
		this.center.zero();
		for (int i = 0; i < this.modifiedPoints.length; i++) {
			center.add(modifiedPoints[i]);
		}
		this.center.div(this.modifiedPoints.length);
	}

	@Override
	public void reBuild() {
		findCenter();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.toString()).append("\n");

		for (int i = 0; i < modifiedPoints.length; i++) {
			stringBuilder.append("  ").append(i).append(": ").append(modifiedPoints[i].toString()).append("\n");
		}
		stringBuilder.append(super.toString());
		return stringBuilder.toString();
	}

	@Override
	public boolean contains(Vector2f vector) {
		return contains(vector.x, vector.y);
	}

	@Override
	public Vector2f[] getPoints() {
		return modifiedPoints;
	}

	public abstract boolean contains(float x, float y);

	public abstract int[] getAxisIndices();

}
