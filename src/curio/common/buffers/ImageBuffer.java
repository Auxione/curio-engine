package common.buffers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;

import common.utilities.Resource;

/**
 * ImageBuffer for curio-engine.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class ImageBuffer implements Resource<ImageBuffer> {
	private int width = 0;
	private int height = 0;
	private ByteBuffer data;

	/**
	 * Create an empty ImageBuffer.
	 * 
	 */
	public ImageBuffer() {
	}

	/**
	 * Create an ImageBuffer and copy its content from src.
	 * 
	 * @param src the source of copy.
	 */
	public ImageBuffer(ImageBuffer src) {
		replace(src);
	}

	/**
	 * Create an ImageBuffer from {@link BufferedImage}.
	 * 
	 * @param bufferedImage
	 */
	public ImageBuffer(BufferedImage bufferedImage) {
		load(bufferedImage);
	}

	/**
	 * Replace the data of this buffer to given source data.
	 * 
	 * @param src the source.
	 */
	public void replace(ImageBuffer src) {
		this.width = src.width;
		this.height = src.height;
		this.data.put(src.data);
	}

	/**
	 * Load data from {@link BufferedImage}.
	 * 
	 */
	public void load(BufferedImage bufferedImage) {
		this.width = bufferedImage.getWidth();
		this.height = bufferedImage.getHeight();

		int[] pixels = new int[this.width * this.height];
		bufferedImage.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
		this.data = MemoryUtil.memAlloc(this.width * this.height * 4);

		for (int h = 0; h < this.height; h++) {
			for (int w = 0; w < this.width; w++) {
				int pixel = pixels[h * this.width + w];
				this.data.put((byte) ((pixel >> 16) & 0xFF));
				this.data.put((byte) ((pixel >> 8) & 0xFF));
				this.data.put((byte) (pixel & 0xFF));
				this.data.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		this.data.flip();
	}

	/**
	 * 
	 * @return the width of the Image.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @return the height of the Image.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 
	 * @return the data of the Image.
	 */
	public ByteBuffer getData() {
		return data;
	}

	@Override
	public ImageBuffer loadFile(File file) throws IOException {
		load(ImageIO.read(file));
		return this;
	}
}
