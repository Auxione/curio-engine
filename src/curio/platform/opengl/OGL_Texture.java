package platform.opengl;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL45.*;

import org.lwjgl.system.MemoryUtil;

import common.Console;
import common.buffers.TextureBuffer;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import graphics.Texture;

public class OGL_Texture implements Texture, NativeObject, OGL_Object {
	private static int nextTextureSlot = 0;
	private int ID;

	private TextureBuffer imageBuffer;
	private int textureSlot = nextTextureSlot;

	private int width, height;

	public OGL_Texture(int width, int height, boolean allocate) {
		this.ID = glGenTextures();
		this.width = width;
		this.height = height;
		if (allocate) {
			GPUMemAlloc();
		}
		NativeObjectManager.register(this);
	}

	public OGL_Texture(TextureBuffer imageBuffer) {
		this(imageBuffer.getWidth(), imageBuffer.getHeight(), false);
		this.imageBuffer = imageBuffer;
		GPUMemLoad(imageBuffer);
		NativeObjectManager.register(this);
	}

	public void GPUMemLoad(TextureBuffer imageBuffer) {
		if (OGL_Texture.nextTextureSlot > OGL_Renderer.TEXTURESAMPLERSIZE) {
			Console.severe(this, "Failed to create textures.");
			return;
		}
		bind();
		param();
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		imageBuffer.mirrorHorizontal();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, imageBuffer.getWidth(), imageBuffer.getHeight(), 0, GL_RGBA,
				GL_UNSIGNED_BYTE, imageBuffer.getData());

		OGL_Texture.nextTextureSlot += 1;
		glBindTextureUnit(this.textureSlot, this.getID());
	}

	public void GPUMemAlloc() {
		if (OGL_Texture.nextTextureSlot > OGL_Renderer.TEXTURESAMPLERSIZE) {
			Console.severe(this, "Failed to create textures.");
			return;
		}
		bind();
		param();
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, this.width, this.height, 0, GL_RGB, GL_UNSIGNED_BYTE, MemoryUtil.NULL);

		OGL_Texture.nextTextureSlot += 1;
		glBindTextureUnit(this.textureSlot, this.getID());
	}

	private void param() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
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
		return this.width;
	}

	@Override
	public final int getHeight() {
		return this.height;
	}

	@Override
	public final int getSlot() {
		return this.textureSlot;
	}

	@Override
	public TextureBuffer getBuffer() {
		return this.imageBuffer;
	}

}
