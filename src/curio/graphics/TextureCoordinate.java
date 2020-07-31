package graphics;

import core.debug.DebugObject;
import graphics.renderer2d.Renderer2D;

/**
 * This class contains UV coordinates of rectangular area in the texture.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class TextureCoordinate implements DebugObject {
	public float width = 1.0f, height = 1.0f, x = 0.0f, y = 0.0f;

	/**
	 * Create new TextureCoordinate
	 */
	public TextureCoordinate() {

	}

	public TextureCoordinate(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public TextureCoordinate(TextureCoordinate source) {
		set(source);
	}

	public void set(TextureCoordinate source) {
		this.x = source.x;
		this.y = source.y;
		this.width = source.width;
		this.height = source.height;
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {

	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[x= ").append(x).append(" y= ").append(y).append("][w=").append(width).append(" h=").append(height)
				.append("]");
		return sb.toString();
	}
}