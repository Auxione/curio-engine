package graphics.renderer2d;

import org.joml.Vector2f;

import common.buffers.TextureBuffer;
import common.geom.Rectangle;
import common.math.TransformingObject;
import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.Texture;
import graphics.TextureCoordinate;

public class Image implements TransformingObject, DebugObject {
	public Rectangle shape;
	public Texture texture;
	public TextureCoordinate textureCoordinate;
	public Color tint = new Color(Color.white);

	public Image(int width, int height) {
		this.textureCoordinate = new TextureCoordinate();
		this.shape = new Rectangle(width, height);
	}

	public Image(TextureBuffer imageBuffer) {
		this(imageBuffer.getWidth(), imageBuffer.getHeight());
		this.texture = Texture.createInstance(imageBuffer);
	}

	public void render(Renderer2D renderer2d) {
		renderer2d.fillQuad(shape.getDefaultPoints()[0], shape.getDefaultPoints()[1], shape.getDefaultPoints()[2],
				shape.getDefaultPoints()[3], this.tint, this.texture, this.textureCoordinate);
	}

	@Override
	public void reBuild() {
		this.shape.reBuild();
	}

	@Override
	public Vector2f[] getDefaultPoints() {
		return this.shape.getDefaultPoints();
	}

	@Override
	public int[] getAxisIndices() {
		return this.shape.getAxisIndices();
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, this.shape.getDefaultPoints()[0].x,
				this.shape.getDefaultPoints()[0].y);

		LineRenderer.render(renderer2d, 1, this.shape.getDefaultPoints()[0], this.shape.getDefaultPoints()[1], color);
		LineRenderer.render(renderer2d, 1, this.shape.getDefaultPoints()[1], this.shape.getDefaultPoints()[2], color);
		LineRenderer.render(renderer2d, 1, this.shape.getDefaultPoints()[2], this.shape.getDefaultPoints()[3], color);
		LineRenderer.render(renderer2d, 1, this.shape.getDefaultPoints()[3], this.shape.getDefaultPoints()[0], color);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Shape: ").append(shape.debugPrint()).append(" Tint: ").append(tint).append(" Texture: ");
		sb.append(texture).append(" TextureCoordinate: ").append(textureCoordinate.debugPrint()).append("]");
		return sb.toString();
	}
}
