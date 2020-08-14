package graphics.renderer2d;

import java.util.HashMap;

import org.joml.Matrix4f;

import common.math.Transform2;
import core.EngineSettings;
import graphics.Color;
import graphics.Mesh;
import platform.opengl.OGL_MeshRenderer2D;

public abstract class MeshRenderer2D {
	protected static HashMap<Mesh, Transform2> objectList = new HashMap<Mesh, Transform2>();

	public static MeshRenderer2D createInstance() {
		switch (EngineSettings.renderer) {

		default:
			return new OGL_MeshRenderer2D();
		}
	}

	protected MeshRenderer2D() {
	}

	public final void submit(Mesh mesh, Transform2 transform) {
		MeshRenderer2D.objectList.put(mesh, transform);
		onSubmitMesh(mesh);
	}

	public final void remove(Mesh mesh) {
		MeshRenderer2D.objectList.remove(mesh);
		onRemoveMesh(mesh);
	}

	public abstract void update(Matrix4f viewMatrix, Matrix4f projectionMatrix);

	protected abstract void onSubmitMesh(Mesh mesh);

	protected abstract void onRemoveMesh(Mesh mesh);

	public abstract void setBackground(Color color);

	public abstract void bind();
}
