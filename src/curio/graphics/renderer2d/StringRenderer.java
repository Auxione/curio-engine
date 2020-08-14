package graphics.renderer2d;

import common.geom.Rectangle;
import common.utilities.TextInput;
import graphics.Color;

/**
 * String rendering for openGL. Uses bitmap fonts to display the string.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class StringRenderer {
	public FontData fontData;
	/**
	 * Maximum textbox width.
	 */
	public int maxTextWidth = 0;
	/**
	 * Maximum textbox height.
	 */
	public int maxTextHeight = 0;
	/**
	 * Default char offset in x axis when rendering.
	 */
	public int charVerticalOffset = 0;
	/**
	 * Default char offset in y axis when rendering.
	 */
	public int charHorizontalOffset = 0;
	/**
	 * Highlight the char in index.
	 */
	public int hightLightIndex = -1;
	/**
	 * Highlight color.
	 */
	public Color hightLightColor = Color.yellow;

	/**
	 * Create new String renderer and use the {@link FontData} as bitmap font.
	 * 
	 * @param fontData
	 */
	public StringRenderer(FontData fontData) {
		this.fontData = fontData;
	}

	/**
	 * Renders the string representation of the object with default
	 * {@link StringRenderer#maxTextWidth}, {@link StringRenderer#maxTextHeight},
	 * {@link StringRenderer#charVerticalOffset},
	 * {@link StringRenderer#charHorizontalOffset}
	 * 
	 * @param renderer2d : Renderer2D to use.
	 * @param object     : Object to get string representation.
	 * @param color      : Color of the rendered text.
	 * @param x          : x component of the position vector.
	 * @param y          : y component of the position vector.
	 */
	public void render(Renderer2D renderer2d, Object object, Color color, float x, float y) {
		render(renderer2d, object.toString(), color, x, y);
	}

	/**
	 * Renders the {@link TextInput} output as string with default
	 * {@link StringRenderer#maxTextWidth}, {@link StringRenderer#maxTextHeight},
	 * {@link StringRenderer#charVerticalOffset},
	 * {@link StringRenderer#charHorizontalOffset}
	 * 
	 * @param renderer2d : Renderer2D to use.
	 * @param textInput  : TextInput to get string.
	 * @param color      : Color of the rendered text.
	 * @param x          : x component of the position vector.
	 * @param y          : y component of the position vector.
	 */
	public void render(Renderer2D renderer2D, TextInput textInput, Color color, float x, float y) {
		render(renderer2D, textInput.getString(), color, x, y);
	}

	/**
	 * Renders the string in with default {@link StringRenderer#maxTextWidth},
	 * {@link StringRenderer#maxTextHeight},
	 * {@link StringRenderer#charVerticalOffset},
	 * {@link StringRenderer#charHorizontalOffset}
	 * 
	 * @param renderer2d : Renderer2D to use.
	 * @param string     : The string render.
	 * @param color      : Color of the rendered text.
	 * @param x          : x component of the position vector.
	 * @param y          : y component of the position vector.
	 */

	public void render(Renderer2D renderer2D, String string, Color color, float x, float y) {
		render(renderer2D, this.fontData, string, this.maxTextWidth, this.maxTextHeight, this.charVerticalOffset,
				this.charHorizontalOffset, this.hightLightIndex, this.hightLightColor, color, x, y);
	}

	/**
	 * Renders the given string. Char offsets are applied to every char when
	 * drawing. If the length of maximum text width specified, tries to clamp it to
	 * width via creating new line. If the length of maximum text height specified,
	 * it discards the remaining chars to fit into the given height.
	 * 
	 * @param renderer2D           : Renderer2D to use.
	 * @param fontdata             : The FontData to use.
	 * @param string               : The string render.
	 * @param maxTextWidth         : The maximum text width to limit.
	 * @param maxTextHeight        : The maximum text height to limit.
	 * @param charVerticalOffset   : Char offsets in x axis.
	 * @param charHorizontalOffset : Char offsets in y axis.
	 * @param hightLightIndex      : index of the char to hightLight.
	 * @param hightLightColor      : hightLighted char color.
	 * @param color                : Color of the rendered text.
	 * @param x                    : x component of the position vector.
	 * @param y                    : y component of the position vector.
	 * 
	 */
	public static void render(Renderer2D renderer2D, FontData fontdata, String string, int maxTextWidth,
			int maxTextHeight, int charVerticalOffset, int charHorizontalOffset, int hightLightIndex,
			Color hightLightColor, Color color, float x, float y) {
		int nextCharPos = 0;
		int nextLineHeight = 0;

		for (int i = 0; i < string.length(); i++) {
			int DrawWidth = fontdata.charWidths[string.charAt(i) - FontData.beginChar];
			int DrawHeight = fontdata.charHeights[string.charAt(i) - FontData.beginChar];

			if ((maxTextWidth > 0 && nextCharPos + DrawWidth >= maxTextWidth)) {
				nextLineHeight += fontdata.getLineHeight(string.substring(0, i)) + charHorizontalOffset;
				if (maxTextHeight > 0 && nextLineHeight + DrawHeight > maxTextHeight) {
					return;
				}
				nextCharPos = 0;
			}
			if (hightLightIndex > 0 && hightLightIndex == i && hightLightColor != null) {
				renderer2D.fillRect(x + nextCharPos, y + nextLineHeight, DrawWidth, DrawHeight, hightLightColor,
						fontdata.texture, fontdata.getCharUV(string.charAt(i)));
			} else {
				renderer2D.fillRect(x + nextCharPos, y + nextLineHeight, DrawWidth, DrawHeight, color, fontdata.texture,
						fontdata.getCharUV(string.charAt(i)));
			}
			nextCharPos += DrawWidth + charVerticalOffset;
		}
	}

	public static void render(Renderer2D renderer2D, FontData fontdata, String string, Color color, float x, float y) {
		render(renderer2D, fontdata, string, 0, 0, 0, 0, -1, null, color, x, y);
	}

	/**
	 * Returns the index of the char where the point is on the rendered text.
	 * Returns -1 if not detected.
	 * 
	 * @param string : The rendered string to get index from.
	 * @param textX  : X component of the rendered string.
	 * @param textY  : Y component of the rendered string.
	 * @param pointx : X component of the point.
	 * @param pointy : Y component of the point.
	 * @return the index number of the detected char in string or -1 if none
	 *         detected.
	 */
	public int getIndex(String string, float textX, float textY, float pointx, float pointy) {
		return getIndex(string, this.maxTextWidth, this.maxTextHeight, this.charVerticalOffset,
				this.charHorizontalOffset, textX, textY, pointx, pointy);
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
			int DrawWidth = this.fontData.charWidths[string.charAt(i) - FontData.beginChar];
			int DrawHeight = this.fontData.charHeights[string.charAt(i) - FontData.beginChar];

			if ((maxTextWidth > 0 && nextCharPos + DrawWidth >= maxTextWidth)) {
				nextLineHeight += FontData.getLineHeight(this.fontData, string.substring(0, i)) + charHorizontalOffset;
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
}
