package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import graphics.Mesh.DrawType;

public abstract class OGL_Buffer implements OGL_Object, NativeObject {
	private int id;
	private int DRAW_METHOD;

	protected OGL_Buffer(DrawType drawType) {
		if (drawType.equals(DrawType.DYNAMIC_DRAW)) {
			this.DRAW_METHOD = GL_DYNAMIC_DRAW;
		}

		else if (drawType.equals(DrawType.STREAM_DRAW)) {
			this.DRAW_METHOD = GL_STREAM_DRAW;
		}

		else {
			this.DRAW_METHOD = GL_STATIC_DRAW;
		}
		this.id = glGenBuffers();
		NativeObjectManager.register(this);
	}

	@Override
	public void terminate() {
		glDeleteBuffers(getID());
	}

	public int getDrawMethod() {
		return this.DRAW_METHOD;
	}

	@Override
	public int getID() {
		return this.id;
	}

}
