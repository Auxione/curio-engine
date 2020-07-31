package graphics.renderer3d;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.joml.Vector3f;
import org.joml.Vector2f;

public class Camera {
	public Vector2f position = new Vector2f();
	public float rotation = 0;

	private Matrix4f transform = new Matrix4f();
	private Matrix4f orthographicViewingMatrix = new Matrix4f();

	public Camera(int width, int height) {
		this.orthographicViewingMatrix.ortho2D(0, width, height, 0);
	}

	public void applySettings() {
		this.transform.setTranslation(new Vector3f(position, 0));
		this.transform.setRotationZYX(rotation, 0, 0);
	}

	public Matrix4f getTransform() {
		applySettings();
		return transform;
	}

	public Matrix4f getViewMatrix() {
		return this.orthographicViewingMatrix;
	}

//TODO: Tests
	public static Vector2f toWorld(Camera camera2D, float x, float y) {
		Vector4f out = new Vector4f(x, y, 0, 0);
		camera2D.transform.transformAffine(out);
		return new Vector2f(out.x, out.y);
	}
}
