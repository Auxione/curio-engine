package graphics;

import common.utilities.ImageBuffer;
import platform.opengl.OGL_Texture;

/**
 * Texture interface for curio-engine
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface Texture {
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

	/**
	 * @return the height of the texture.
	 */
	public int getHeight();

	/**
	 * @return the {@link ImageBuffer} of the texture.
	 */
	public ImageBuffer getBuffer();

	public static Texture createInstance() {
		return new OGL_Texture();
	}

	public static Texture createInstance(ImageBuffer imageBuffer) {
		return new OGL_Texture(imageBuffer);
	}

}
