package graphics;

import common.Console;
import common.geom.Rectangle;
import graphics.renderer2d.Image;

/**
 * Container for {@link TextureCoordinate}s. This class contains array of
 * {@link TextureCoordinate}s to store UV coordinates of the {@link Texture}.
 * 
 * @see TextureCoordinate
 * @see Texture
 * @author Mehmet Cem Zarifoglu
 *
 */
public class TextureAtlas {
	private TextureCoordinate[] textureCoordinates;
	private Texture texture;

	public TextureAtlas(int size) {
		this.textureCoordinates = new TextureCoordinate[size];
	}

	public TextureAtlas(Texture texture, int size) {
		this.textureCoordinates = new TextureCoordinate[size];
		this.texture = texture;
	}

	public final TextureAtlas subAtlas(int startIndex, int endIndex, TextureAtlas target) {
		if (endIndex < startIndex) {
			Console.severe(this, "End index cannot be less than start index.");
			return null;
		}
		if (endIndex - startIndex > this.size()) {
			Console.severe(this, "Given range is bigger than size.");
			return null;
		}

		target.textureCoordinates = new TextureCoordinate[endIndex - startIndex];
		target.texture = getTexture();
		for (int index = 0; index < target.textureCoordinates.length; index++) {
			target.textureCoordinates[index] = new TextureCoordinate(this.get(startIndex + index));
		}
		return target;
	}

	public final TextureAtlas subAtlas(int startIndex, int endIndex) {
		TextureAtlas out = new TextureAtlas(this.texture, endIndex - startIndex);
		return subAtlas(startIndex, endIndex, out);
	}

	public final void map(int index, float x, float y, float width, float height) {
		map(index, texture.getWidth(), texture.getHeight(), x, y, width, height);
	}

	public final void map(int index, float textureWidth, float textureHeight, float x, float y, float width,
			float height) {
		TextureCoordinate tc = get(index);

		tc.x = x / textureWidth;
		tc.y = y / textureHeight;
		tc.width = width / textureWidth;
		tc.height = height / textureHeight;
	}

	public final void set(Image image, int index) {
		image.textureCoordinate.set(get(index));
		image.texture = getTexture();
	}

	public boolean check() {
		return (this.texture != null) && (this.textureCoordinates != null);
	}

	public final TextureCoordinate get(int index) {
		return this.textureCoordinates[index];
	}

	public final void put(int index, TextureCoordinate textureCoordinate) {
		this.textureCoordinates[index] = textureCoordinate;
	}

	public final Texture getTexture() {
		return this.texture;
	}

	public final void setTexture(Texture texture) {
		this.texture = texture;
	}

	public final int size() {
		return this.textureCoordinates.length;
	}

	public static TextureAtlas createCellular(Texture texture, Rectangle rectangle) {
		int xCount = (int) (texture.getWidth() / rectangle.width);
		int yCount = (int) (texture.getHeight() / rectangle.height);
		float normalizedWidth = rectangle.width / texture.getWidth();
		float normalizedHeight = rectangle.height / texture.getHeight();

		TextureAtlas out = new TextureAtlas(texture, xCount * yCount);

		int index = 0;
		for (int y = 0; y < yCount; y++) {
			for (int x = 0; x < xCount; x++) {
				out.textureCoordinates[index] = new TextureCoordinate(x * normalizedWidth, y * normalizedHeight,
						normalizedWidth, normalizedHeight);
				index++;
			}
		}
		return out;
	}

}
