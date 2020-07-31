package common.math;

import org.joml.Vector2f;

public class Rotationf {
	private float value = 0;

	public static Rotationf fromDegrees(float angle) {
		return new Rotationf((float) Math.toRadians(angle));
	}

	public static Rotationf fromRadians(float angle) {
		return new Rotationf(angle);
	}

	public static Rotationf fromHeading(Vector2f vector2f) {
		return new Rotationf((float) Math.atan2(vector2f.y, vector2f.x));
	}

	public Vector2f getHeading(Vector2f target) {
		target.set((float) Math.cos(value), (float) Math.sin(value));
		return target;
	}

	public Vector2f getHeading() {
		return new Vector2f((float) Math.cos(value), (float) Math.sin(value));
	}

	public Rotationf() {
	}

	private Rotationf(float value) {
		setRAD(value);
	}

	public void zero() {
		this.value = 0;
	}

	public void setDEG(float value) {
		setRAD((float) Math.toRadians(value));
	}

	public void addDEG(float value) {
		addRAD((float) Math.toRadians(value));
	}

	public void subDEG(float value) {
		subRAD((float) Math.toRadians(value));
	}

	public void set(Rotationf rotation) {
		setRAD(rotation.value);
	}

	public void add(Rotationf rotation) {
		addRAD(rotation.value);
	}

	public void sub(Rotationf rotation) {
		subRAD(rotation.value);
	}

	public void add(Rotationf rotation, Rotationf target) {
		target.addRAD(rotation.value + this.value);
	}

	public void sub(Rotationf rotation, Rotationf target) {
		target.addRAD(rotation.value - this.value);
	}

	public void setRAD(float value) {
		this.value = value;
		this.value = (float) ((this.value + 2 * Math.PI) % (2 * Math.PI));
	}

	public void addRAD(float value) {
		this.value += value;
		this.value = (float) ((this.value + 2 * Math.PI) % (2 * Math.PI));
	}

	public void subRAD(float value) {
		this.value -= value;
		this.value = (float) ((this.value + 2 * Math.PI) % (2 * Math.PI));
	}

	public float getDEG() {
		return (float) Math.toDegrees(value);
	}

	public float getRAD() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
