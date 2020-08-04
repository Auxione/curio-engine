package graphics.renderer2d;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import common.geom.Rectangle;
import common.math.Rotationf;

public class Camera2 {
	private Rectangle rectangle;

	private Matrix4f projection = new Matrix4f();
	private Matrix4f view = new Matrix4f();

	public Vector2f position = new Vector2f();
	public Rotationf rotation = new Rotationf();

	public Camera2(float width, float height) {
		this.rectangle = new Rectangle(width, height);
		setSize(width, height);
	}

	public void setSize(float width, float height) {
		this.rectangle.width = width;
		this.rectangle.height = height;
		this.rectangle.reBuild();
		this.projection.ortho2D(-this.rectangle.width / 2, this.rectangle.width / 2, this.rectangle.height / 2,
				-this.rectangle.height / 2);
	}

	public void update() {
		this.view.identity();
		this.view.rotateLocalZ(this.rotation.getRAD());
		this.view.setTranslation(this.position.x, this.position.y, 0);
	}

	public Matrix4f getViewMatrix() {
		return this.view;
	}

	public Matrix4f getProjectionMatrix() {
		return this.projection;
	}

	public float getWidth() {
		return this.rectangle.width;
	}

	public float getHeight() {
		return this.rectangle.height;
	}

}
