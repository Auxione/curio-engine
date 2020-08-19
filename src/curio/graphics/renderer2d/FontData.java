package graphics.renderer2d;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import common.buffers.TextureBuffer;
import common.geom.Rectangle;
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

	public FontData() {
		this.textureAtlas = new TextureAtlas(endChar - beginChar);
		this.charWidths = new int[endChar - beginChar];
		this.charHeights = new int[endChar - beginChar];
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
	public static final int maxRows = 127;
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
	public static FontData createFromAWT(Font font) {
		FontData out = new FontData();

		BufferedImage toMetrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D toMetricsGraphics = (Graphics2D) toMetrics.getGraphics();
		toMetricsGraphics.setFont(font);
		toMetricsGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics awtfontMetrics = toMetricsGraphics.getFontMetrics();

		int charSize = 0;

		for (int i = beginChar; i < endChar; i++) {
			int index = i - beginChar;
			out.charWidths[index] = awtfontMetrics.charWidth((char) index);
			out.charHeights[index] = awtfontMetrics.getHeight();

			if (charSize < out.charHeights[index]) {
				charSize = out.charHeights[index];
			}

			else if (charSize < out.charWidths[index]) {
				charSize = out.charWidths[index];
			}
		}

		int maxTextureWidth = charSize * (maxRows + 1);
		int maxTextureHeight = charSize * (endChar - beginChar + 1) / maxRows;

		BufferedImage imgTemp = new BufferedImage(maxTextureWidth, maxTextureHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) imgTemp.getGraphics();

		int currentColomn = 0;
		int currentRow = 0;
		for (int i = beginChar; i < endChar; i++) {
			int index = i - beginChar;
			BufferedImage charImage = TextureFactory.createCharImage(font, (char) index);

			int posX = charSize * currentRow;
			int posY = charSize * currentColomn;
			g.drawImage(charImage, posX, posY, null);

			TextureCoordinate textureCoordinate = new TextureCoordinate();
			textureCoordinate.x = (float) (posX) / maxTextureWidth;
			textureCoordinate.y = (float) (posY) / maxTextureHeight;
			textureCoordinate.width = (float) (out.charWidths[index]) / maxTextureWidth;
			textureCoordinate.height = (float) (out.charHeights[index]) / maxTextureHeight;
			out.textureAtlas.put(i, textureCoordinate);

			currentRow++;
			if (currentRow > maxRows) {
				currentColomn++;
				currentRow = 0;
			}
		}
		out.texture = Texture.createInstance(new TextureBuffer(imgTemp));
		out.textureAtlas.setTexture(out.texture);
		lastCreatedFontData = out;
		return out;
	}

	/**
	 * Returns the index of the char where the point is on the rendered text.
	 * Returns -1 if not detected.
	 * 
	 * @param string               : The rendered string to get index from.
	 * @param maxTextWidth         : The maximum text width to limit.
	 * @param maxTextHeight        : The maximum text height to limit.
	 * @param charVerticalOffset   : Char offsets in x axis.
	 * @param charHorizontalOffset : Char offsets in y axis.
	 * @param textX                : X component of the rendered string.
	 * @param textY                : Y component of the rendered string.
	 * @param pointx               : X component of the point.
	 * @param pointy               : Y component of the point.
	 * @return the index number of the detected char in string or -1 if none
	 *         detected.
	 */
	public int getIndex(String string, int maxTextWidth, int maxTextHeight, int charVerticalOffset,
			int charHorizontalOffset, float textX, float textY, float pointx, float pointy) {
		int nextCharPos = 0;
		int nextLineHeight = 0;

		for (int i = 0; i < string.length(); i++) {
			int DrawWidth = this.charWidths[string.charAt(i) - FontData.beginChar];
			int DrawHeight = this.charHeights[string.charAt(i) - FontData.beginChar];

			if ((maxTextWidth > 0 && nextCharPos + DrawWidth >= maxTextWidth)) {
				nextLineHeight += getLineHeight(this, string.substring(0, i)) + charHorizontalOffset;
				if (maxTextHeight > 0 && nextLineHeight + DrawHeight > maxTextHeight) {
					return i;
				}
				nextCharPos = 0;
				nextCharPos = 0;
			}
			if (Rectangle.contains(textX + nextCharPos, textY + nextLineHeight, DrawWidth, DrawHeight, pointx,
					pointy)) {
				return i;
			}

			nextCharPos += DrawWidth + charVerticalOffset;
		}
		return -1;
	}

	/**
	 * Returns the line width.
	 * 
	 * @param string : The string to calculate line width.
	 * @return the width of the given string.
	 */
	public final int getLineWidth(String string) {
		return getLineWidth(this, string);
	}

	/**
	 * Returns the line height.
	 * 
	 * @param string : The string to calculate line height.
	 * @return the height of the given string.
	 */
	public final int getLineHeight(String string) {
		return getLineHeight(this, string);
	}

	/**
	 * Returns the line width.
	 * 
	 * @param string : The string to calculate line width.
	 * @return the width of the given string.
	 */
	public static final int getLineWidth(FontData fontdata, String string) {
		int out = 0, maxTextWidth = 0;
		for (int i = 0; i < string.length(); i++) {
			out += fontdata.charWidths[string.charAt(i)];
		}
		if (out > maxTextWidth && maxTextWidth != 0) {
			out = maxTextWidth;
		}
		return out;
	}

	/**
	 * Returns the line height.
	 * 
	 * @param string : The string to calculate line height.
	 * @return the height of the given string.
	 */
	public static final int getLineHeight(FontData fontdata, String string) {
		int out = 0;
		for (int i = 0; i < string.length(); i++) {
			if (out < fontdata.charHeights[string.charAt(i)]) {
				out = fontdata.charHeights[string.charAt(i)];
			}
		}
		return out;
	}
}
