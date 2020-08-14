package graphics;

import core.EngineSettings;
import platform.opengl.OGL_FrameBuffer;

public abstract class FrameBuffer {
	public static FrameBuffer createInstance(int width, int height) {
		switch (EngineSettings.renderer) {
		default:
			return new OGL_FrameBuffer(width, height);
		}
	}

	protected FrameBuffer() {
	}

	public abstract void bind();

	public abstract void unBind();

	public abstract void terminate();

	public abstract Texture getTexture();

	public abstract int getID();

	public abstract int getWidth();

	public abstract void clear(Color color);

	public abstract int getHeight();

}
