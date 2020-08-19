package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import graphics.Mesh.DrawType;

public abstract class OGL_Buffer implements OGL_Object, NativeObject {
	private int id;
	
	protected OGL_Buffer() {
		this.id = glGenBuffers();
		NativeObjectManager.register(this);
	}

	public static int getOGLType(DrawType drawType) {
		if (drawType.equals(DrawType.DYNAMIC_DRAW)) {
			return GL_DYNAMIC_DRAW;
		}

		else if (drawType.equals(DrawType.STREAM_DRAW)) {
			return GL_STREAM_DRAW;
		}

		else {
			return GL_STATIC_DRAW;
		}
	}

	@Override
	public void terminate() {
		glDeleteBuffers(getID());
	}

	@Override
	public int getID() {
		return this.id;
	}

}
