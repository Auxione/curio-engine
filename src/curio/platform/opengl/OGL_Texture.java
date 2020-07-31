package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.Console;
import common.utilities.ImageBuffer;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import graphics.Texture;
import graphics.renderer2d.Renderer2D;

public class OGL_Texture implements Texture, NativeObject, OGL_Object {
	private static int nextTextureSlot = 0;
	private int ID;

	private ImageBuffer imageBuffer;
	private int textureSlot = nextTextureSlot;

	public OGL_Texture() {
		this.ID = glGenTextures();
		NativeObjectManager.register(this);
	}

	public OGL_Texture(ImageBuffer imageBuffer) {
		this.ID = glGenTextures();
		this.imageBuffer = imageBuffer;
		GPULoadMethod(0);
		NativeObjectManager.register(this);
	}

	@Override
	public void GPULoadMethod(int parameter) {
		if (OGL_Texture.nextTextureSlot > Renderer2D.TEXTURESAMPLERSIZE) {
			Console.severe(this, "Failed to create textures.");
			return;
		}

		bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, imageBuffer.getWidth(), imageBuffer.getHeight(), 0, GL_RGBA,
				GL_UNSIGNED_BYTE, imageBuffer.getData());

		OGL_Texture.nextTextureSlot += 1;// TODO: testing
		glBindTextureUnit(this.textureSlot, this.getID());
	}

	@Override
	public final void bind() {
		glActiveTexture(GL_TEXTURE0 + this.textureSlot);
		glBindTexture(GL_TEXTURE_2D, this.ID);
	}

	@Override
	public final void terminate() {
		glDeleteTextures(this.ID);
	}

	@Override
	public final int getID() {
		return this.ID;
	}

	@Override
	public final int getWidth() {
		return this.imageBuffer.getWidth();
	}

	@Override
	public final int getHeight() {
		return this.imageBuffer.getHeight();
	}

	@Override
	public final int getSlot() {
		return this.textureSlot;
	}

	@Override
	public ImageBuffer getBuffer() {
		return this.imageBuffer;
	}
}
