package graphics;

import common.buffers.TextureBuffer;
import core.EngineSettings;
import platform.opengl.OGL_Texture;

/**
 * Texture interface for curio-engine
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface Texture {

	public static Texture createInstance(int width, int height, boolean allocate) {
		switch (EngineSettings.renderer) {

		default:
			return new OGL_Texture(width, height, allocate);
		}
	}

	public static Texture createInstance(TextureBuffer textureBuffer) {
		switch (EngineSettings.renderer) {

		default:
			return new OGL_Texture(textureBuffer);
		}
	}

	/**
	 * @return the slot is assigned to the texture slot in gpu.
	 */
	public int getSlot();

	/**
	 * @return the ID of the texture.
	 */
	public int getID();

	/**
	 * Bind the texture. Not used in modern GL implementations.
	 */
	public void bind();

	/**
	 * @return the width of the texture.
	 */
	public int getWidth();

	public void replace(TextureBuffer textureBuffer);

	/**
	 * @return the height of the texture.
	 */
	public int getHeight();

	/**
	 * @return the {@link TextureBuffer} of the texture.
	 */
	public TextureBuffer getBuffer();

}
