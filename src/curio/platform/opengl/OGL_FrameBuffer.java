package platform.opengl;

import common.Console;
import common.utilities.NativeObject;
import graphics.Color;
import graphics.FrameBuffer;
import graphics.Texture;

import static org.lwjgl.opengl.GL45.*;

public class OGL_FrameBuffer extends FrameBuffer implements OGL_Object, NativeObject {
	private int id;
	private OGL_Texture oglTexture;

	public OGL_FrameBuffer(int width, int height) {
		this.id = glGenFramebuffers();
		this.oglTexture = new OGL_Texture(width, height, true);

		bind();
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.oglTexture.getID(), 0);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			Console.severe(this, "Framebuffer is not completed.");
		}
	}

	public void clear(Color color) {
		if (color != null) {
			glClearColor(color.x, color.y, color.z, color.w);
			glClear(GL_COLOR_BUFFER_BIT);
		}
	}

	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, getID());
		glViewport(0, 0, getWidth(), getHeight());
		this.oglTexture.bind();
	}

	public void unBind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	@Override
	public void terminate() {
		glDeleteFramebuffers(getID());
	}

	public Texture getTexture() {
		return this.oglTexture;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public int getWidth() {
		return this.oglTexture.getWidth();
	}

	@Override
	public int getHeight() {
		return this.oglTexture.getHeight();
	}

}
