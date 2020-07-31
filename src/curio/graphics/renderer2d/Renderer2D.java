package graphics.renderer2d;

import org.joml.Vector2f;

import common.geom.Rectangle;
import common.geom.Triangle;
import common.math.MathUtils;
import core.debug.DebugObject;
import graphics.Color;
import graphics.Graphics;
import graphics.Texture;
import graphics.TextureCoordinate;
import platform.opengl.OGL_QuadRenderer2D;
import platform.opengl.OGL_TrisRenderer2D;

public abstract class Renderer2D implements Graphics, DebugObject {
	/**
	 * Maximum quads per batch.
	 */
	public static final int MAXQUADS = 20_000;
	/**
	 * Maximum vertices per quads in batch.
	 */
	public static final int MAXVERTICES = MAXQUADS * 4;
	/**
	 * Maximum indices for vertices.
	 */
	public static final int MAXINDICES = MAXQUADS * 6;
	/**
	 * The number of textures active on screen simultaneously.
	 */
	public static final int TEXTURESAMPLERSIZE = 32;
	/**
	 * The default {@link TextureCoordinate}.
	 */
	public static TextureCoordinate defaultTextureCoordinate;
	/**
	 * The default {@link Color}.
	 */
	public static Color defaultColor;
	/**
	 * The default {@link Texture}.
	 */
	public static Texture defaultWhiteTexture;

	/**
	 * Create new instance of renderer object.
	 * 
	 * @param i      : For future uses.
	 * @param width  : width of the frame buffer.
	 * @param height : height of the frame buffer.
	 */
	public static Renderer2D createInstance(int index, int width, int height) {
		switch (index) {
		case 1:
			return new OGL_TrisRenderer2D(width, height);
		default:
			return new OGL_QuadRenderer2D(width, height);
		}
	};

	protected Renderer2D() {
	}

	protected final void init(Color color, Texture texture, TextureCoordinate textureCoordinate) {
		Renderer2D.defaultTextureCoordinate = textureCoordinate;
		Renderer2D.defaultColor = color;
		Renderer2D.defaultWhiteTexture = texture;
	}

	/**
	 * Set background to given color.
	 * 
	 * @param color : The color to set background.
	 */
	public abstract void setBackground(Color color);

	/**
	 * Bind the renderer2D.
	 * 
	 */
	public abstract void bind();

	/**
	 * Begin creating the scene.
	 * 
	 */
	public abstract void beginScene();

	/**
	 * End creating the scene.
	 * 
	 */
	public abstract void endScene();

	/**
	 * Draw quad from given coordinates with default {@link Color}, {@link Texture},
	 * {@link TextureCoordinate}.
	 * 
	 * @param x1 : First point x component.
	 * @param y1 : First point y component.
	 * @param x2 : Second point x component.
	 * @param y2 : Second point y component.
	 * @param x3 : Third point x component.
	 * @param y3 : Third point y component.
	 * @param x4 : Fourth point x component.
	 * @param y4 : Fourth point y component.
	 */
	public final void fillQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		fillQuad(x1, y1, x2, y2, x3, y3, x4, y4, Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	};

	/**
	 * Draw quad from given coordinates with default {@link Texture},
	 * {@link TextureCoordinate}.
	 * 
	 * @param x1    : First point x component.
	 * @param y1    : First point y component.
	 * @param x2    : Second point x component.
	 * @param y2    : Second point y component.
	 * @param x3    : Third point x component.
	 * @param y3    : Third point y component.
	 * @param x4    : Fourth point x component.
	 * @param y4    : Fourth point y component.
	 * @param color : {@link Color} of the quad.
	 */
	public final void fillQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4,
			Color color) {
		fillQuad(x1, y1, x2, y2, x3, y3, x4, y4, color, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	};

	/**
	 * Draw quad from given coordinates with default {@link TextureCoordinate}.
	 * 
	 * @param x1      : First point x component.
	 * @param y1      : First point y component.
	 * @param x2      : Second point x component.
	 * @param y2      : Second point y component.
	 * @param x3      : Third point x component.
	 * @param y3      : Third point y component.
	 * @param x4      : Fourth point x component.
	 * @param y4      : Fourth point y component.
	 * @param color   : {@link Color} of the quad.
	 * @param texture : {@link Texture} of the quad.
	 */
	public final void fillQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4,
			Color color, Texture texture) {
		fillQuad(x1, y1, x2, y2, x3, y3, x4, y4, color, texture, Renderer2D.defaultTextureCoordinate);
	};

	/**
	 * Draw quad from given coordinates.
	 * 
	 * @param x1                : First point x component.
	 * @param y1                : First point y component.
	 * @param x2                : Second point x component.
	 * @param y2                : Second point y component.
	 * @param x3                : Third point x component.
	 * @param y3                : Third point y component.
	 * @param x4                : Fourth point x component.
	 * @param y4                : Fourth point y component.
	 * @param color             : {@link Color} of the quad.
	 * @param texture           : {@link Texture} of the quad.
	 * @param textureCoordinate : {@link TextureCoordinate} of the quad.
	 */
	public abstract void fillQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4,
			Color color, Texture texture, TextureCoordinate textureCoordinate);

	/**
	 * Draw quad from given coordinates with default {@link Color}, {@link Texture},
	 * {@link TextureCoordinate}.
	 * 
	 * @param p1 : First point.
	 * @param p2 : Second point.
	 * @param p3 : Third point.
	 * @param p4 : Fourth point.
	 */
	public final void fillQuad(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
		fillQuad(p1, p2, p3, p4, Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw quad from given coordinates with default {@link Texture},
	 * {@link TextureCoordinate}.
	 * 
	 * @param p1    : First point.
	 * @param p2    : Second point.
	 * @param p3    : Third point.
	 * @param p4    : Fourth point.
	 * @param color : {@link Color} of the quad.
	 */
	public final void fillQuad(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4, Color color) {
		fillQuad(p1, p2, p3, p4, color, Renderer2D.defaultWhiteTexture, Renderer2D.defaultTextureCoordinate);
	};

	/**
	 * Draw quad from given coordinates with default {@link TextureCoordinate}.
	 * 
	 * @param p1      : First point.
	 * @param p2      : Second point.
	 * @param p3      : Third point.
	 * @param p4      : Fourth point.
	 * @param color   : {@link Color} of the quad.
	 * @param texture : {@link Texture} of the quad.
	 */
	public final void fillQuad(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4, Color color, Texture texture) {
		fillQuad(p1, p2, p3, p4, color, texture, Renderer2D.defaultTextureCoordinate);
	};

	/**
	 * Draw quad from given coordinates.
	 * 
	 * @param p1                : First point.
	 * @param p2                : Second point.
	 * @param p3                : Third point.
	 * @param p4                : Fourth point.
	 * @param color             : {@link Color} of the quad.
	 * @param texture           : {@link Texture} of the quad.
	 * @param textureCoordinate : {@link TextureCoordinate} of the quad.
	 */
	public final void fillQuad(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4, Color color, Texture texture,
			TextureCoordinate textureCoordinate) {
		fillQuad(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, color, texture, textureCoordinate);
	};

	/**
	 * Draw rectangle centered on position with default {@link Color},
	 * {@link Texture}, {@link TextureCoordinate}.
	 * 
	 * @param x      : x component of the position vector.
	 * @param y      : y component of the position vector.
	 * @param width  : width of the rectangle.
	 * @param height : height of the rectangle.
	 */
	public final void fillRectCentered(float x, float y, float width, float height) {
		fillRect(x - width / 2, y - height / 2, width, height, Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle centered on given position with default {@link Texture},
	 * {@link TextureCoordinate}.
	 * 
	 * @param x      : x component of the position vector.
	 * @param y      : y component of the position vector.
	 * @param width  : width of the rectangle.
	 * @param height : height of the rectangle.
	 * @param color  : {@link Color} of the rectangle.
	 * 
	 */
	public final void fillRectCentered(float x, float y, float width, float height, Color color) {
		fillRect(x - width / 2, y - height / 2, width, height, color, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle centered on given parameters with default
	 * {@link TextureCoordinate}.
	 * 
	 * @param x       : x component of the position vector.
	 * @param y       : y component of the position vector.
	 * @param width   : width of the rectangle.
	 * @param height  : height of the rectangle.
	 * @param color   : {@link Color} of the rectangle.
	 * @param texture : {@link Texture} of the rectangle.
	 * 
	 */
	public final void fillRectCentered(float x, float y, float width, float height, Color color, Texture texture) {
		fillRect(x - width / 2, y - height / 2, width, height, color, texture, Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle centered on given parameters.
	 * 
	 * @param x                 : x component of the position vector.
	 * @param y                 : y component of the position vector.
	 * @param width             : width of the rectangle.
	 * @param height            : height of the rectangle.
	 * @param color             : {@link Color} of the rectangle.
	 * @param texture           : {@link Texture} of the rectangle.
	 * @param textureCoordinate : {@link TextureCoordinate} of the rectangle.
	 */

	public final void fillRectCentered(float x, float y, float width, float height, Color color, Texture texture,
			TextureCoordinate textureCoordinate) {
		fillRect(x - width / 2, y - height / 2, width, height, color, texture, textureCoordinate);
	}

	/**
	 * Draw rectangle centered and rotated on given parameters with default
	 * {@link Color}, {@link Texture}, {@link TextureCoordinate}.
	 * 
	 * @param x      : x component of the position vector.
	 * @param y      : y component of the position vector.
	 * @param width  : width of the rectangle.
	 * @param height : height of the rectangle.
	 * @param angle  : angle in radian.
	 */

	public void fillRectRotated(float x, float y, int width, int height, float angle) {
		fillRectRotated(x, y, width, height, angle, Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle centered and rotated on given parameters with default
	 * {@link Texture}, {@link TextureCoordinate}.
	 * 
	 * @param x      : x component of the position vector.
	 * @param y      : y component of the position vector.
	 * @param width  : width of the rectangle.
	 * @param height : height of the rectangle.
	 * @param angle  : angle in radian.
	 * @param color  : {@link Color} of the rectangle.
	 * 
	 */
	public void fillRectRotated(float x, float y, int width, int height, float angle, Color color) {
		fillRectRotated(x, y, width, height, angle, color, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle centered and rotated on given parameters with default
	 * {@link TextureCoordinate}.
	 * 
	 * @param x       : x component of the position vector.
	 * @param y       : y component of the position vector.
	 * @param width   : width of the rectangle.
	 * @param height  : height of the rectangle.
	 * @param angle   : angle in radian.
	 * @param color   : {@link Color} of the rectangle.
	 * @param texture : {@link Texture} of the rectangle.
	 * 
	 */
	public void fillRectRotated(float x, float y, int width, int height, float angle, Color color, Texture texture) {
		fillRectRotated(x, y, width, height, angle, color, texture, Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle centered and rotated on given parameters.
	 * 
	 * @param x                 : x component of the position vector.
	 * @param y                 : y component of the position vector.
	 * @param width             : width of the rectangle.
	 * @param height            : height of the rectangle.
	 * @param angle             : angle in radian.
	 * @param color             : {@link Color} of the rectangle.
	 * @param texture           : {@link Texture} of the rectangle.
	 * @param textureCoordinate : {@link TextureCoordinate} of the rectangle.
	 */

	public void fillRectRotated(float x, float y, int width, int height, float angle, Color color, Texture texture,
			TextureCoordinate textureCoordinate) {
		Vector2f p1 = new Vector2f(x - width / 2, y - height / 2);
		Vector2f p2 = new Vector2f(x + width / 2, y - height / 2);
		Vector2f p3 = new Vector2f(x + width / 2, y + height / 2);
		Vector2f p4 = new Vector2f(x - width / 2, y + height / 2);

		MathUtils.Vector.rotateAround(p1, x, y, angle);
		MathUtils.Vector.rotateAround(p2, x, y, angle);
		MathUtils.Vector.rotateAround(p3, x, y, angle);
		MathUtils.Vector.rotateAround(p4, x, y, angle);

		fillQuad(p1, p2, p3, p4, color, texture, textureCoordinate);
	}

	/**
	 * Draw rectangle with given parameters with default {@link Color},
	 * {@link Texture}, {@link TextureCoordinate}.
	 * 
	 * @param x      : x component of the top left corner.
	 * @param y      : y component of the top left corner.
	 * @param width  : width of the rectangle.
	 * @param height : height of the rectangle.
	 */

	public final void fillRect(float x1, float y1, float width, float height) {
		fillRect(x1, y1, width, height, Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle with given parameters with default {@link Texture},
	 * {@link TextureCoordinate}.
	 * 
	 * @param x      : x component of the top left corner.
	 * @param y      : y component of the top left corner.
	 * @param width  : width of the rectangle.
	 * @param height : height of the rectangle.
	 * @param color  : {@link Color} of the rectangle.
	 * 
	 */
	public final void fillRect(float x1, float y1, float width, float height, Color color) {
		fillRect(x1, y1, width, height, color, Renderer2D.defaultWhiteTexture, Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle with given parameters with default {@link TextureCoordinate}.
	 * 
	 * @param x       : x component of the top left corner.
	 * @param y       : y component of the top left corner.
	 * @param width   : width of the rectangle.
	 * @param height  : height of the rectangle.
	 * @param color   : {@link Color} of the rectangle.
	 * @param texture : {@link Texture} of the rectangle.
	 */
	public final void fillRect(float x1, float y1, float width, float height, Color color, Texture texture) {
		fillRect(x1, y1, width, height, color, texture, Renderer2D.defaultTextureCoordinate);
	}

	/**
	 * Draw rectangle with given parameters.
	 * 
	 * @param x                 : x component of the top left corner.
	 * @param y                 : y component of the top left corner.
	 * @param width             : width of the rectangle.
	 * @param height            : height of the rectangle.
	 * @param color             : {@link Color} of the rectangle.
	 * @param texture           : {@link Texture} of the rectangle.
	 * @param textureCoordinate : {@link TextureCoordinate} of the rectangle.
	 */
	public final void fillRect(float x1, float y1, float width, float height, Color color, Texture texture,
			TextureCoordinate textureCoordinate) {
		fillQuad(x1, y1, x1 + width, y1, x1 + width, y1 + height, x1, y1 + height, color, texture, textureCoordinate);
	}

	public abstract void fillTris(float x1, float y1, float x2, float y2, float x3, float y3, Color color,
			Texture texture, TextureCoordinate textureCoordinate);

	public void fillTris(float x1, float y1, float x2, float y2, float x3, float y3, Color color, Texture texture) {
		fillTris(x1, y1, x2, y2, x3, y3, color, texture, Renderer2D.defaultTextureCoordinate);
	};

	public void fillTris(float x1, float y1, float x2, float y2, float x3, float y3, Color color) {
		fillTris(x1, y1, x2, y2, x3, y3, color, Renderer2D.defaultWhiteTexture, Renderer2D.defaultTextureCoordinate);
	};

	public void fillTris(float x1, float y1, float x2, float y2, float x3, float y3) {
		fillTris(x1, y1, x2, y2, x3, y3, Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	};

	private void fillTris(Vector2f vector2f, Vector2f vector2f2, Vector2f vector2f3, Color defaultColor2,
			Texture defaultWhiteTexture2, TextureCoordinate defaultTextureCoordinate2) {
		fillTris(vector2f.x, vector2f.y, vector2f2.x, vector2f2.y, vector2f3.x, vector2f3.y, Renderer2D.defaultColor,
				Renderer2D.defaultWhiteTexture, Renderer2D.defaultTextureCoordinate);
	}

	public final void fill(Rectangle rectangle) {
		fillQuad(rectangle.modifiedPoints[0], rectangle.modifiedPoints[1], rectangle.modifiedPoints[2],
				rectangle.modifiedPoints[3], Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	public final void fill(Rectangle rectangle, Color color) {
		fillQuad(rectangle.modifiedPoints[0], rectangle.modifiedPoints[1], rectangle.modifiedPoints[2],
				rectangle.modifiedPoints[3], color, Renderer2D.defaultWhiteTexture,
				Renderer2D.defaultTextureCoordinate);
	}

	public final void fill(Rectangle rectangle, Color color, Texture texture) {
		fillQuad(rectangle.modifiedPoints[0], rectangle.modifiedPoints[1], rectangle.modifiedPoints[2],
				rectangle.modifiedPoints[3], color, texture, Renderer2D.defaultTextureCoordinate);
	}

	public final void fill(Rectangle rectangle, Color color, Texture texture, TextureCoordinate textureCoordinate) {
		fillQuad(rectangle.modifiedPoints[0], rectangle.modifiedPoints[1], rectangle.modifiedPoints[2],
				rectangle.modifiedPoints[3], color, texture, textureCoordinate);
	}

	public final void fill(Triangle triangle) {
		fillTris(triangle.modifiedPoints[0], triangle.modifiedPoints[1], triangle.modifiedPoints[2],
				Renderer2D.defaultColor, Renderer2D.defaultWhiteTexture, Renderer2D.defaultTextureCoordinate);
	}

	public final void fill(Triangle triangle, Color color) {
		fillTris(triangle.modifiedPoints[0], triangle.modifiedPoints[1], triangle.modifiedPoints[2], color,
				Renderer2D.defaultWhiteTexture, Renderer2D.defaultTextureCoordinate);
	}

	public final void fill(Triangle triangle, Color color, Texture texture) {
		fillTris(triangle.modifiedPoints[0], triangle.modifiedPoints[1], triangle.modifiedPoints[2], color, texture,
				Renderer2D.defaultTextureCoordinate);
	}

	public final void fill(Triangle triangle, Color color, Texture texture, TextureCoordinate textureCoordinate) {
		fillTris(triangle.modifiedPoints[0], triangle.modifiedPoints[1], triangle.modifiedPoints[2], color, texture,
				textureCoordinate);
	}

	public abstract void setRenderSize(float width, float height);
}
