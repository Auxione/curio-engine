package graphics.renderer2d;

import org.joml.Vector2f;

import common.buffers.ImageBuffer;
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

	public Image(ImageBuffer imageBuffer) {
		this(imageBuffer.getWidth(), imageBuffer.getHeight());
		this.texture = Texture.createInstance(imageBuffer);
	}

	public void render(Renderer2D renderer2d) {
		renderer2d.fillQuad(shape.modifiedPoints[0], shape.modifiedPoints[1], shape.modifiedPoints[2],
				shape.modifiedPoints[3], this.tint, this.texture, this.textureCoordinate);
	}

	@Override
	public void reBuild() {
		this.shape.reBuild();
	}

	@Override
	public Vector2f[] getPoints() {
		return this.shape.getPoints();
	}

	@Override
	public int[] getAxisIndices() {
		return this.shape.getAxisIndices();
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, this.shape.getPoints()[0].x,
				this.shape.getPoints()[0].y);

		LineRenderer.render(renderer2d, 1, this.shape.getPoints()[0], this.shape.getPoints()[1], color);
		LineRenderer.render(renderer2d, 1, this.shape.getPoints()[1], this.shape.getPoints()[2], color);
		LineRenderer.render(renderer2d, 1, this.shape.getPoints()[2], this.shape.getPoints()[3], color);
		LineRenderer.render(renderer2d, 1, this.shape.getPoints()[3], this.shape.getPoints()[0], color);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Shape: ").append(shape.debugPrint()).append(" Tint: ").append(tint).append(" Texture: ");
		sb.append(texture).append(" TextureCoordinate: ").append(textureCoordinate.debugPrint()).append("]");
		return sb.toString();
	}
}
