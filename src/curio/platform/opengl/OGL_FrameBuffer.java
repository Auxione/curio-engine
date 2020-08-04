package platform.opengl;

import common.utilities.NativeObject;
import static org.lwjgl.opengl.GL45.*;

public class OGL_FrameBuffer implements OGL_Object, NativeObject {
	private int id;
	private OGL_Texture oglTexture;

	public OGL_FrameBuffer(int width, int height) {
		this.id = glGenFramebuffers();
		bind();
		this.oglTexture = new OGL_Texture(width, height, true);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.oglTexture.getID(), 0);
	}

	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		this.oglTexture.bind();
	}

	public void unBind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	@Override
	public void terminate() {
		glDeleteFramebuffers(id);
	}

	public OGL_Texture getTexture() {
		return this.oglTexture;
	}

	@Override
	public int getID() {
		return id;
	}

}
