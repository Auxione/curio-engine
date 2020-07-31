package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;

public abstract class OGL_Buffer implements OGL_Object, NativeObject {
	private int id;
	private int DRAW_METHOD;

	public enum Method {
		STATIC_DRAW, DYNAMIC_DRAW, STREAM_DRAW
	}

	protected OGL_Buffer(Method method) {
		if (method.equals(Method.DYNAMIC_DRAW)) {
			this.DRAW_METHOD = GL_DYNAMIC_DRAW;
		}

		else if (method.equals(Method.STREAM_DRAW)) {
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
