package graphics.renderer2d;

import java.util.HashMap;

import common.math.Transform2;
import core.EngineSettings;
import graphics.Color;
import graphics.Mesh;
import platform.opengl.OGL_MeshRenderer2D;

//TODO:
public abstract class MeshRenderer2D {
	protected static HashMap<Mesh, Transform2> objectList = new HashMap<Mesh, Transform2>();
	private Camera2 camera;

	public static MeshRenderer2D createInstance(Camera2 camera) {
		switch (EngineSettings.renderer) {

		default:
			return new OGL_MeshRenderer2D(camera);
		}
	}

	protected MeshRenderer2D(Camera2 camera) {
		setCamera(camera);
	}

	public final void setCamera(Camera2 camera) {
		this.camera = camera;
	}

	public final Camera2 getCamera() {
		return this.camera;
	}

	public final void submit(Mesh mesh, Transform2 transform) {
		MeshRenderer2D.objectList.put(mesh, transform);
		onSubmitMesh(mesh);
	}

	public final void remove(Mesh mesh) {
		MeshRenderer2D.objectList.remove(mesh);
		onRemoveMesh(mesh);
	}

	public abstract void update();

	protected abstract void onSubmitMesh(Mesh mesh);

	protected abstract void onRemoveMesh(Mesh mesh);

	public abstract void setBackground(Color color);

	public abstract void bind();
}
