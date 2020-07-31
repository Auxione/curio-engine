package physics;

import java.util.ArrayList;

import org.joml.Vector2f;

import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.LineRenderer;
import graphics.renderer2d.Renderer2D;

public class Ray2 implements DebugObject {
	private static ArrayList<RaycastObject> rayCastList = new ArrayList<RaycastObject>();
	public static float maxRange = 1000;

	public static void register(RaycastObject raycastObject) {
		Ray2.rayCastList.add(raycastObject);
	}

	public static void clear() {
		Ray2.rayCastList.clear();
	}

	public float castResolution = 1;
	public Vector2f position = new Vector2f();
	public Vector2f direction = new Vector2f();
	private Vector2f castPos = new Vector2f();

	public Ray2() {

	}

	public Ray2(Vector2f position, Vector2f direction) {
		this.position.set(position);
		this.direction.set(direction);
	}

	public Ray2(float x, float y, float dirx, float diry) {
		this.position.set(x, y);
		this.direction.set(dirx, diry);
	}

	public RaycastObject cast() {
		return cast(Ray2.maxRange);
	}

	public RaycastObject cast(float maxRange) {
		castPos.zero();
		float range = 0;
		while (range <= maxRange) {
			direction.mul(range, castPos);
			castPos.add(position);

			for (RaycastObject raycastObject : rayCastList) {
				if (raycastObject.contains(castPos)) {
					return raycastObject;
				}
			}
			range += castResolution;
		}
		return null;
	}

	public RaycastObject cast(RaycastObject raycastObject) {
		return cast(raycastObject, Ray2.maxRange);
	}

	public RaycastObject cast(RaycastObject raycastObject, float maxRange) {
		castPos.zero();
		float range = 0;
		while (range <= maxRange) {
			direction.mul(range, castPos);
			castPos.add(position);

			if (raycastObject.contains(castPos)) {
				return raycastObject;
			}
			range++;
		}
		return null;
	}

	public void draw(Renderer2D renderer2d, Color color) {
		LineRenderer.render(renderer2d, 1, position.x, position.y, castPos.x, castPos.y, color);
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, this.position.x, this.position.y);
		renderer2d.fillRectCentered(this.position.x, this.position.y, 2, 2, color);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[x: ").append(this.position.x).append(" y: ").append(this.position.y);
		sb.append(" Dir: x:").append(this.direction.x).append(" y: ").append(this.direction.y).append(" y: ")
				.append(" resolution: ").append(castResolution).append(" totalObjs: ").append(rayCastList.size())
				.append("]");
		return sb.toString();
	}
}
