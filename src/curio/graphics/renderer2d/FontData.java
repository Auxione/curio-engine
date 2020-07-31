package graphics.renderer2d;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import common.utilities.ImageBuffer;
import graphics.Texture;
import graphics.TextureAtlas;
import graphics.TextureCoordinate;
import graphics.TextureFactory;

public class FontData {
	/**
	 * The {@link Texture} of the font.
	 */
	public Texture texture;
	/**
	 * The {@link TextureAtlas} of the texture.
	 */
	public TextureAtlas textureAtlas;
	/**
	 * The array of character widths.
	 */
	public int[] charWidths;
	/**
	 * The array of character heights.
	 */
	public int[] charHeights;
	/**
	 * The array texture maximum width.
	 */
	public int fontTextureWidth;

	/**
	 * The array texture maximum height.
	 */
	public int fontTextureHeight;

	public FontData(int fontTextureWidth, int fontTextureHeight) {
		this.fontTextureWidth = fontTextureWidth;
		this.fontTextureHeight = fontTextureHeight;
	}

	/**
	 * Get {@link TextureCoordinate} for given character.
	 * 
	 * @param Char : The character to get {@link TextureCoordinate}.
	 * @return the {@link TextureCoordinate} from given character.
	 */
	public TextureCoordinate getCharUV(char Char) {
		return this.textureAtlas.get(Char - beginChar);
	}

	/**
	 * Returns char width from given character.
	 * 
	 */
	public final int getCharWidth(char c) {
		return this.charWidths[c - FontData.beginChar];
	}

	/**
	 * Returns char height from given character.
	 * 
	 */
	public final int getCharHeight(char c) {
		return this.charHeights[c - FontData.beginChar];
	}

	public static final int beginChar = 0;
	public static final int endChar = 127;
	public static final int textureSizeMultipler = 16;
	private static FontData lastCreatedFontData;

	/**
	 * 
	 * @return the last created FontData.
	 */
	public static FontData getLast() {
		return lastCreatedFontData;
	}

	/**
	 * Creates FontData from {@link Font}
	 * 
	 * @return the created FontData.
	 */
	public static FontData createFontDataFromAWT(Font font) {
		FontData out = new FontData(font.getSize() * textureSizeMultipler, font.getSize() * textureSizeMultipler);
		BufferedImage imgTemp = new BufferedImage(out.fontTextureWidth, out.fontTextureHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) imgTemp.getGraphics();

		int rowHeight = 0;
		int positionX = 0;
		int positionY = 0;
		int fontTextureCharHeight = 0;

		out.textureAtlas = new TextureAtlas(out.texture, endChar - beginChar);
		out.charWidths = new int[endChar - beginChar];
		out.charHeights = new int[endChar - beginChar];

		for (int i = beginChar; i < endChar; i++) {
			char fontChar = (char) i;
			BufferedImage charImage = TextureFactory.createCharImage(font, fontChar);

			out.charWidths[i - beginChar] = charImage.getWidth();
			out.charHeights[i - beginChar] = charImage.getHeight();

			/*
			 * when current font position and width of the char exceeds the width of the
			 * textureSet set index to new line
			 */
			if (positionX + out.charWidths[i - beginChar] >= out.fontTextureWidth) {
				positionX = 0;
				positionY += rowHeight;
			}
			// mark the position of the char text to its data
			int charStoredX = positionX;
			int charStoredY = positionY;
			/*
			 * below these lines adjusts font height and row height to new height if its
			 * smaller than current char texture height
			 */
			if (out.charHeights[i - beginChar] > fontTextureCharHeight) {
				fontTextureCharHeight = out.charHeights[i - beginChar];
			}

			if (out.charHeights[i - beginChar] > rowHeight) {
				rowHeight = out.charHeights[i - beginChar];
			}
			// draw the font to position
			g.drawImage(charImage, positionX, positionY, null);
			// shift to new line
			positionX += out.charHeights[i - beginChar];

			out.textureAtlas.put(i - beginChar, new TextureCoordinate());
			out.textureAtlas.map(i - beginChar, out.fontTextureWidth, out.fontTextureHeight, charStoredX, charStoredY,
					out.charWidths[i - beginChar], out.charHeights[i - beginChar]);
		}
		out.texture = Texture.createInstance(new ImageBuffer(imgTemp));
		out.textureAtlas.setTexture(out.texture);
		lastCreatedFontData = out;
		return out;
	}
}
