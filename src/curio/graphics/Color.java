package graphics;

import common.math.MathUtils;

public class Color {
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

	/**
	 * The red component (range 0-1)
	 */
	public float r;
	/**
	 * The green component (range 0-1)
	 */
	public float g;
	/**
	 * The blue component (range 0-1)
	 */
	public float b;
	/**
	 * The alpha component (range 0-1)
	 */
	public float a;

	public Color(Color color) {
		set(color);
	}

	/**
	 * @param r The red component of the color (0.0 -> 1.0)
	 * @param g The green component of the color (0.0 -> 1.0)
	 * @param b The blue component of the color (0.0 -> 1.0)
	 */
	public Color(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}

	/**
	 * @param r The red component of the color (0.0 -> 1.0)
	 * @param g The green component of the color (0.0 -> 1.0)
	 * @param b The blue component of the color (0.0 -> 1.0)
	 * @param a The alpha component of the color (0.0 -> 1.0)
	 */
	public Color(float r, float g, float b, float a) {
		set(r, g, b, a);
	}

	/**
	 * @param r The red component of the color (0 -> 255)
	 * @param g The green component of the color (0 -> 255)
	 * @param b The blue component of the color (0 -> 255)
	 */
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}

	/**
	 * @param r The red component of the color (0 -> 255)
	 * @param g The green component of the color (0 -> 255)
	 * @param b The blue component of the color (0 -> 255)
	 * @param a The alpha component of the color (0 -> 255)
	 */
	public Color(int r, int g, int b, int a) {
		this(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}

	/**
	 * Create a color from an evil integer packed 0xAARRGGBB. If AA is specified as
	 * zero then it will be interpreted as unspecified and hence a value of 255 will
	 * be recorded.
	 * 
	 * @param value The value to interpret for the color
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
		set(r, g, b, a);
	}

	@Override
	public int hashCode() {
		return ((int) (this.r + this.g + this.b + this.a) * 255);
	}

	public void set(int r, int g, int b) {
		set(r / 255.0f, g / 255.0f, b / 255.0f);
	}

	public void set(int r, int g, int b, int a) {
		set(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}

	public void set(Color source) {
		set(source.r, source.g, source.b, source.a);
	}

	public void set(float r, float g, float b) {
		nset(MathUtils.clamp(r, 0f, 1f), MathUtils.clamp(g, 0f, 1f), MathUtils.clamp(b, 0f, 1f));
	}

	public void set(float r, float g, float b, float a) {
		nset(MathUtils.clamp(r, 0f, 1f), MathUtils.clamp(g, 0f, 1f), MathUtils.clamp(b, 0f, 1f),
				MathUtils.clamp(a, 0f, 1f));
	}

	public void nset(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void nset(float r, float g, float b, float a) {
		nset(r, g, b);
		this.a = a;
	}

	/**
	 * @param scale The scale up of RGB (i.e. if you supply 0.03 the color will be
	 *              brightened by 3%)
	 * @return The brighter version of this color
	 */
	public void brighter(float scale) {
		scale += 1;
		set(this.r * scale, this.g * scale, this.b * scale, this.a);
	}

	/**
	 * @return The brighter version of this color
	 */
	public void brighter() {
		brighter(0.2f);
	}

	/**
	 * @param scale The scale down of RGB (i.e. if you supply 0.03 the color will be
	 *              darkened by 3%)
	 * @return The darker version of this color
	 */
	public void darker(float scale) {
		scale = 1 - scale;
		set(this.r * scale, this.g * scale, this.b * scale, this.a);
	}

	/**
	 * @return The darker version of this color
	 */
	public void darker() {
		darker(0.5f);
	}

	/**
	 * @return The red component (range 0-255)
	 */
	public int getRed() {
		return (int) (this.r * 255);
	}

	/**
	 * @return The green component (range 0-255)
	 */
	public int getGreen() {
		return (int) (this.g * 255);
	}

	/**
	 * @return The blue component (range 0-255)
	 */
	public int getBlue() {
		return (int) (this.b * 255);
	}

	/**
	 * @return The alpha component (range 0-255)
	 */
	public int getAlpha() {
		return (int) (this.a * 255);
	}

	public void zero() {
		this.r = 0f;
		this.g = 0f;
		this.b = 0f;
		this.a = 0f;
	}
}