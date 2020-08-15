package common.buffers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import common.utilities.Resource;

/**
 * ImageBuffer for curio-engine.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class TextureBuffer implements Resource<TextureBuffer>, NativeObject {
	private int width = 0;
	private int height = 0;
	private ByteBuffer data;

	/**
	 * Create an empty ImageBuffer.
	 * 
	 */
	public TextureBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		this.data = MemoryUtil.memAlloc(this.width * this.height * 4);
		NativeObjectManager.register(this);

		System.out.println(this.width + " aa " + this.height);
	}

	/**
	 * Create an ImageBuffer and copy its content from src.
	 * 
	 * @param src the source of copy.
	 */
	public TextureBuffer(TextureBuffer src) {
		this(src.width, src.height);
		replace(src);
	}

	/**
	 * Create an ImageBuffer from {@link BufferedImage}.
	 * 
	 * @param bufferedImage
	 */
	public TextureBuffer(BufferedImage bufferedImage) {
		this(bufferedImage.getWidth(), bufferedImage.getHeight());
		load(bufferedImage);
	}

	/**
	 * Replace the data of this buffer to given source data.
	 * 
	 * @param src the source.
	 */
	public void replace(TextureBuffer src) {
		this.width = src.width;
		this.height = src.height;
		this.data.put(src.data);
	}

	private void load(BufferedImage bufferedImage) {
		for (int h = 0; h < bufferedImage.getHeight(); h++) {
			for (int w = 0; w < bufferedImage.getWidth(); w++) {
				int pixel = bufferedImage.getRGB(w, h);

				this.data.put((byte) ((pixel >> 16) & 0xFF));
				this.data.put((byte) ((pixel >> 8) & 0xFF));
				this.data.put((byte) (pixel & 0xFF));
				this.data.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		this.data.flip();
	}

	public void setRGBA(int x, int y, int pixel) {
		int ofs = ((x + (y * this.width)) * 4);
		this.data.put(ofs, (byte) ((pixel >> 16) & 0xFF));
		this.data.put(ofs + 1, (byte) ((pixel >> 8) & 0xFF));
		this.data.put(ofs + 2, (byte) (pixel & 0xFF));
		this.data.put(ofs + 3, (byte) ((pixel >> 24) & 0xFF));
	}

	public void mirrorHorizontal() {
		byte[] tempArray = new byte[this.height * this.width * 4];
		int size = this.width * 4;

		for (int h = 0; h < this.height; h++) {
			this.data.get(h * size, tempArray, (this.height - h - 1) * size, size);
		}
		this.data.put(0, tempArray);
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
	public TextureBuffer loadFile(File file) throws IOException {
		load(ImageIO.read(file));
		return this;
	}

	@Override
	public void terminate() {
		MemoryUtil.memFree(data);
	}
}
