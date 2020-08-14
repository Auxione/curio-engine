package common.utilities;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import common.Console;
import common.buffers.AudioBuffer;
import common.buffers.TextureBuffer;
import graphics.Texture;

/**
 * ResourceManager for curio-engine
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class ResourceManager {
	private static ResourceManager instance;
	private static File lastFile;

	private ResourceManager() {
	}

	/**
	 * @return the instance of the ResourceManager.
	 */
	public static ResourceManager getInstance() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}

	/**
	 * Returns the {@link File} extension from filepath.
	 * 
	 * @return the extension of the {@link File}.
	 */
	public String getExtension(String filepath) {
		return filepath.substring(filepath.lastIndexOf(".") + 1).toLowerCase();
	}

	/**
	 * Loads the file from filepath.
	 * 
	 * @param filepath the filepath to load {@link File} from.
	 * @return ResourceManager Instance.
	 */
	public ResourceManager load(String filepath) {
		ResourceManager.lastFile = new File(filepath);
		if (!ResourceManager.lastFile.exists()) {
			Console.warning(this, filepath + " does not exists!");
		}
		return this;
	}

	/**
	 * Create audioBuffer from last loaded file. Currently only wave files
	 * supported.
	 * 
	 * @return {@link AudioBuffer}
	 */
	public AudioBuffer asAudioBuffer() {
		String ext = getExtension(lastFile.getPath());
		if (ext.contentEquals("wav")) {
			try {
				return new AudioBuffer().loadFile(lastFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Console.warning(this, "Unsupported audio format.");
		}
		return null;
	}

	/**
	 * Create ImageBuffer from last loaded file. "png", "jpeg", "bmp", "wbmp" files
	 * are supported.
	 * 
	 * @return {@link TextureBuffer}
	 */
	public TextureBuffer asTextureBuffer() {
		String ext = getExtension(lastFile.getPath());
		if (ext.contentEquals("png") || ext.contentEquals("jpeg") || ext.contentEquals("bmp")
				|| ext.contentEquals("wbmp")) {
			try {
				return new TextureBuffer(ImageIO.read(lastFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Console.warning(this, "Unsupported image format.");
		}
		return null;
	}

	/**
	 * Create Texture from last loaded file. "png", "jpeg", "bmp", "wbmp" files are
	 * supported.
	 * 
	 * @return {@link Texture}
	 */
	public Texture asTexture() {
		return Texture.createInstance(asTextureBuffer());
	}

	public File getFile() {
		return lastFile;
	}

	public String asString() {
		InputStream in;
		try {
			in = new FileInputStream(lastFile);
			String out = new String(in.readAllBytes());
			in.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
