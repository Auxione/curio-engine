package common.geom;

import org.joml.Vector2f;

import graphics.Color;
import graphics.renderer2d.LineRenderer;
import graphics.renderer2d.Renderer2D;

public class LineSegment2 {
	public Vector2f a = new Vector2f();
	public Vector2f b = new Vector2f();

	/**
	 * Create a new {@link LineSegment2} as a copy of the given <code>source</code>.
	 * 
	 * @param source the {@link LineSegment2} to copy from
	 */
	public LineSegment2(LineSegment2 source) {
		set(source.a.x, source.a.y, source.b.x, source.b.y);
	}

	/**
	 * Create a new {@link LineSegment2} between the given two points.
	 * 
	 * @param a the first point
	 * @param b the second point
	 */
	public LineSegment2(Vector2f a, Vector2f b) {
		set(a.x, a.y, b.x, b.y);
	}

	/**
	 * Create a new {@link LineSegment2} between the two points.
	 * 
	 * @param aX the x coordinate of the first point
	 * @param aY the y coordinate of the first point
	 * @param bX the x coordinate of the second point
	 * @param bY the y coordinate of the second point
	 */
	public LineSegment2(float aX, float aY, float bX, float bY) {
		set(aX, aY, bX, bY);
	}

	private void set(float aX, float aY, float bX, float bY) {
		this.a.set(aX, aY);
		this.b.set(bX, bY);
	}

	public Vector2f getNormal() {
		float dx = this.b.x - this.a.x;
		float dy = this.b.y - this.a.y;
		return new Vector2f(-dy, dx).normalize();
	}

	/**
	 * Calculates the distance from a point to {@link LineSegment2}.
	 * 
	 * @param point the point to calculate distance.
	 * @return calculated distance.
	 */
	public float distance(float x, float y) {
		float s1 = -this.b.y + this.a.y;
		float s2 = this.b.x - this.a.x;
		return (float) (Math.abs((x - this.a.x) * s1 + (y - this.a.y) * s2) / Math.sqrt(s1 * s1 + s2 * s2));
	}

	/**
	 * Calculates the distance from a point to {@link LineSegment2}.
	 * 
	 * @param point the point to calculate distance.
	 * @return calculated distance in float.
	 */
	public float distance(Vector2f point) {
		return distance(point.x, point.y);
	}

	/**
	 * Check if this {@link LineSegment2} intersects with other. Store in
	 * <code>intersectPosition</code>.
	 * 
	 * @param other the LineSegment2 to check intersection.
	 * @return true if intersection happens.
	 */
	public boolean intersects(LineSegment2 other) {
		return intersects(this.a, this.b, other.a, other.b, null);
	}

	/**
	 * Check if this {@link LineSegment2} intersects with other. Store in
	 * <code>intersectPosition</code>.
	 * 
	 * @param other             the LineSegment2 to check intersection.
	 * @param intersectPosition store the position of intersection.
	 * @return true if intersection happens.
	 */
	public static boolean intersects(Vector2f a, Vector2f b, Vector2f c, Vector2f d) {
		return intersects(a, b, c, d, null);
	}

	/**
	 * Check if this {@link LineSegment2} intersects with other. Store in
	 * <code>intersectPosition</code>.
	 * 
	 * @param other             the LineSegment2 to check intersection.
	 * @param intersectPosition store the position of intersection.
	 * @return true if intersection happens.
	 */
	public static boolean intersects(Vector2f a, Vector2f b, Vector2f c, Vector2f d, Vector2f intersectPosition) {
		float uA = ((d.x - c.x) * (a.y - c.y) - (d.y - c.y) * (a.x - c.x))
				/ ((d.y - c.y) * (b.x - a.x) - (d.x - c.x) * (b.y - a.y));
		float uB = ((b.x - a.x) * (a.y - c.y) - (b.y - a.y) * (a.x - c.x))
				/ ((d.y - c.y) * (b.x - a.x) - (d.x - c.x) * (b.y - a.y));

		if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
			if (intersectPosition != null) {
				intersectPosition.set(a.x + (uA * (b.x - a.x)), a.y + (uA * (b.y - a.y)));
			}
			return true;
		}
		return false;
	}

	public void draw(Renderer2D renderer2d, float width, Color color) {
		LineRenderer.render(renderer2d, width, this.a, this.b, color);
	}

}
