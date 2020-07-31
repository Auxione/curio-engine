package graphics;

import org.joml.Vector4f;

import common.math.MathUtils;

public class Color extends Vector4f {
	public static final Color transparent = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Color white = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color yellow = new Color(1.0f, 1.0f, 0, 1.0f);
	public static final Color lightRed = new Color(1.0f, 0.5f, 0.5f, 1.0f);
	public static final Color red = new Color(1.0f, 0, 0, 1.0f);
	public static final Color darkRed = new Color(0.5f, 0, 0, 1.0f);
	public static final Color blue = new Color(0, 0, 1.0f, 1.0f);
	public static final Color green = new Color(0, 1.0f, 0, 1.0f);
	public static final Color black = new Color(0, 0, 0, 1.0f);
	public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color cyan = new Color(0, 1.0f, 1.0f, 1.0f);
	public static final Color darkGray = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	public static final Color lightGray = new Color(0.7f, 0.7f, 0.7f, 1.0f);

	public static final Color pink = new Color(255, 175, 175, 255);
	public static final Color orange = new Color(255, 200, 0, 255);
	public static final Color magenta = new Color(255, 0, 255, 255);
	public static final Color purple = new Color(187, 51, 255, 255);
	public static final Color limeGreen = new Color(50, 205, 50, 255);

	public Color(Color color) {
		set(color);
	}

	/**
	 * @param r The red component of the colour (0.0 -> 1.0)
	 * @param g The green component of the colour (0.0 -> 1.0)
	 * @param b The blue component of the colour (0.0 -> 1.0)
	 */
	public Color(float r, float g, float b) {
		set(MathUtils.clamp(r, 0, 1), MathUtils.clamp(g, 0, 1), MathUtils.clamp(b, 0, 1), 1);
	}

	/**
	 * @param r The red component of the colour (0.0 -> 1.0)
	 * @param g The green component of the colour (0.0 -> 1.0)
	 * @param b The blue component of the colour (0.0 -> 1.0)
	 * @param a The alpha component of the colour (0.0 -> 1.0)
	 */
	public Color(float r, float g, float b, float a) {
		set(MathUtils.clamp(r, 0, 1), MathUtils.clamp(g, 0, 1), MathUtils.clamp(b, 0, 1), MathUtils.clamp(a, 0, 1));
	}

	/**
	 * @param r The red component of the colour (0 -> 255)
	 * @param g The green component of the colour (0 -> 255)
	 * @param b The blue component of the colour (0 -> 255)
	 */
	public Color(int r, int g, int b) {
		set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
	}

	/**
	 * @param r The red component of the colour (0 -> 255)
	 * @param g The green component of the colour (0 -> 255)
	 * @param b The blue component of the colour (0 -> 255)
	 * @param a The alpha component of the colour (0 -> 255)
	 */
	public Color(int r, int g, int b, int a) {
		set(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}

	/**
	 * Create a colour from an evil integer packed 0xAARRGGBB. If AA is specified as
	 * zero then it will be interpreted as unspecified and hence a value of 255 will
	 * be recorded.
	 * 
	 * @param value The value to interpret for the colour
	 */
	public Color(int value) {
		int r = (value & 0x00FF0000) >> 16;
		int g = (value & 0x0000FF00) >> 8;
		int b = (value & 0x000000FF);
		int a = (value & 0xFF000000) >> 24;

		if (a < 0) {
			a += 256;
		}
		if (a == 0) {
			a = 255;
		}
		set(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}

	@Override
	public int hashCode() {
		return ((int) (this.x + this.y + this.z + this.w) * 255);
	}

	@Override
	public String toString() {
		return "Color (" + this.x + "," + this.y + "," + this.z + "," + this.w + ")";
	}

	public Vector4f set(int r, int g, int b) {
		return set(r / 255.0f, g / 255.0f, b / 255.0f);
	}

	public Vector4f set(int r, int g, int b,int a) {
		return set(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}

	/**
	 * @return The red component (range 0-255)
	 */
	public int getRed() {
		return (int) (this.x * 255);
	}

	/**
	 * @return The green component (range 0-255)
	 */
	public int getGreen() {
		return (int) (this.y * 255);
	}

	/**
	 * @return The blue component (range 0-255)
	 */
	public int getBlue() {
		return (int) (this.z * 255);
	}

	/**
	 * @return The alpha component (range 0-255)
	 */
	public int getAlpha() {
		return (int) (this.w * 255);
	}

	/**
	 * @param scale The scale up of RGB (i.e. if you supply 0.03 the colour will be
	 *              brightened by 3%)
	 * @return The brighter version of this colour
	 */
	public Color brighter(float scale) {
		scale += 1;
		return new Color(this.x * scale, this.y * scale, this.z * scale, this.w);
	}

	/**
	 * @return The brighter version of this colour
	 */
	public Color brighter() {
		return brighter(0.2f);
	}

	/**
	 * @param scale The scale down of RGB (i.e. if you supply 0.03 the colour will
	 *              be darkened by 3%)
	 * @return The darker version of this colour
	 */
	public Color darker(float scale) {
		scale = 1 - scale;
		return new Color(this.x * scale, this.y * scale, this.z * scale, this.w);
	}

	/**
	 * @return The darker version of this colour
	 */
	public Color darker() {
		return darker(0.5f);
	}
}