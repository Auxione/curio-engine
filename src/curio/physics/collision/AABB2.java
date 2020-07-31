package physics.collision;

import org.joml.Vector2f;

import common.math.TransformingObject;
import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.LineRenderer;
import graphics.renderer2d.Renderer2D;

public class AABB2 implements DebugObject {
	private float minX = Float.POSITIVE_INFINITY;
	private float minY = Float.POSITIVE_INFINITY;
	private float maxX = Float.NEGATIVE_INFINITY;
	private float maxY = Float.NEGATIVE_INFINITY;
	private TransformingObject transformingObject;

	public AABB2() {

	}

	public AABB2(TransformingObject transformingObject) {
		this.transformingObject = transformingObject;
	}

	public void update() {
		calculate(this.transformingObject);
		correctBounds();
	}

	public void calculate(TransformingObject transformingObject) {
		for (int i = 0; i < transformingObject.getPoints().length; i++) {
			Vector2f vec = transformingObject.getPoints()[i];
			this.minX = this.minX > vec.x ? this.minX : vec.x;
			this.minY = this.minY > vec.y ? this.minY : vec.y;
			this.maxX = this.maxX < vec.x ? this.maxX : vec.x;
			this.maxY = this.maxY < vec.y ? this.maxY : vec.y;
		}
		correctBounds();
	}

	public void correctBounds() {
		float tmp;
		if (this.minX > this.maxX) {
			tmp = this.minX;
			this.minX = this.maxX;
			this.maxX = tmp;
		}
		if (this.minY > this.maxY) {
			tmp = this.minY;
			this.minY = this.maxY;
			this.maxY = tmp;
		}
	}

	public boolean checkAgainst(AABB2 other) {
		return (this.minX <= other.maxX && this.maxX >= other.minX && this.minY <= other.maxY
				&& this.maxY >= other.minY);
	}

	public float getMaxX() {
		return maxX;
	}

	public float getMinX() {
		return minX;
	}

	public float getMaxY() {
		return maxY;
	}

	public float getMinY() {
		return minY;
	}
 

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d,  debugPrint(), color, minX, minY);

		LineRenderer.render(renderer2d, 1, minX, minY, maxX, minY, color);
		LineRenderer.render(renderer2d, 1, maxX, minY, maxX, maxY, color);
		LineRenderer.render(renderer2d, 1, maxX, maxY, minX, maxY, color);
		LineRenderer.render(renderer2d, 1, minX, maxY, minX, minY, color);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(minX).append(" ").append(minY).append(") < (").append(maxX).append(" ").append(maxY)
				.append(")");
		return sb.toString();
	}
}
