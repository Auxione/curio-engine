package graphics;

import org.joml.Matrix4f;

import common.math.Transform2;
import graphics.renderer2d.MeshRenderer2D;

public class Camera {
	private CameraSettings cameraSettings;
	private Matrix4f projection = new Matrix4f();
	private Matrix4f view = new Matrix4f();
	public Transform2 transform = new Transform2();

	public Camera(CameraSettings cameraSettings) {
		applySettings(cameraSettings);
	}

	public void applySettings(CameraSettings cameraSettings) {
		this.cameraSettings = cameraSettings;
		this.projection.setOrtho2D(-this.cameraSettings.width / 2, this.cameraSettings.width / 2,
				this.cameraSettings.height / 2, -this.cameraSettings.height / 2);
	}

	public void update() {
		this.transform.update();
		Transform2.convert(this.transform.getMatrix(), this.view);
	}

	public void render(MeshRenderer2D meshRenderer2D) {
		if (cameraSettings.renderTarget != null) {
			this.cameraSettings.renderTarget.bind();
			this.cameraSettings.renderTarget.clear(cameraSettings.backgroundColor);
			meshRenderer2D.update(this.view, this.projection);
			this.cameraSettings.renderTarget.unBind();
		} else {
			meshRenderer2D.update(this.view, this.projection);
		}
	}

	public Matrix4f getViewMatrix() {
		return this.view;
	}

	public Matrix4f getProjectionMatrix() {
		return this.projection;
	}

	public CameraSettings getCameraSettings() {
		return this.cameraSettings;
	}
}
