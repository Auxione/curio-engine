package common.math;

import org.joml.Vector2f;

/**
 * Common Math library for curio engine.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class MathUtils {
	/**
	 * Limit the value to given minimum and maximum. If the given value smaller than
	 * the minimum, method returns minimum value. If the given value higher than the
	 * maximum, method returns maximum value.
	 * 
	 * @param value the value to process.
	 * @param min   the wanted minimum value.
	 * @param max   the wanted maximum value.
	 * @return the processed value.
	 */
	public static float clamp(float value, float min, float max) {
		if (value >= max) {
			return max;
		}

		if (value <= min) {
			return min;
		}
		return value;
	}

	/**
	 * Integer version of the clamp method.
	 * 
	 * @see MathUtils#clamp
	 */
	public static int clamp(int value, int min, int max) {
		if (value >= max) {
			return max;
		}

		if (value <= min) {
			return min;
		}
		return value;
	}

	/**
	 * Generate random number based on java Math library.
	 * 
	 * @param start the minimum value of the generation.
	 * @param stop  the maximum value of the generation.
	 * @return generated number.
	 */
	public static int random(int start, int stop) {
		return (int) (start + Math.random() * (stop - start));
	}

	/**
	 * Generate random number based on java Math library.
	 * 
	 * @param start the minimum value of the generation.
	 * @param stop  the maximum value of the generation.
	 * @return generated number.
	 */
	public static float random(float start, float stop) {
		return (float) (start + Math.random() * (stop - start));
	}

	/**
	 * Generate random index number of the given array and returns that object in
	 * index.
	 * 
	 * @param o the array to choose object.
	 * @return randomly selected object from array.
	 */
	public static Object random(Object[] o) {
		return o[random(0, o.length)];
	}

	/**
	 * Maps the given value from one range to another.
	 * 
	 * @param value  the value to calculate
	 * @param start1 start of the first range.
	 * @param end1   end of the first range.
	 * @param start2 start of the second range.
	 * @param end2   end of the second range.
	 * @return the given value in second range.
	 */
	public static float map(float value, float start1, float end1, float start2, float end2) {
		return start2 + (end2 - start2) * ((value - start1) / (end1 - start1));
	}

	public static float lerp(float a, float b, float f) {
		return a * (1.0f - f) + (b * f);
	}

	/**
	 * Checks whether if the given point is in rectangular area
	 * 
	 * @param px         the x component of the point.
	 * @param py         the y component of the point.
	 * @param rectx      the x component of top left corner of the rectangle.
	 * @param recty      the y component of top left corner of the rectangle.
	 * @param rectWidth  width of the rectangle.
	 * @param rectHeight height of the rectangle.
	 * @return true if the point is in the rectangular area
	 */
	public final static boolean RectangleContains(float px, float py, float rectx, float recty, float rectWidth,
			float rectHeight) {
		return (px > rectx && px < rectx + rectWidth && py > recty && py < recty + rectHeight);
	}

	private static float epsilon = 0.0001f;

	public static boolean isParallel(Vector2f p11, Vector2f p12, Vector2f p21, Vector2f p22) {
		Vector2f norm1 = new Vector2f();
		p11.sub(p12, norm1);
		norm1.normalize();

		Vector2f norm2 = new Vector2f();
		p21.sub(p22, norm2);
		norm2.normalize();

		float dot = norm1.dot(norm2);
		if (dot <= (1f + epsilon) && dot >= (1f - epsilon)) {
			return true;
		}

		if (dot >= -(1f + epsilon) && dot <= -(1f - epsilon)) {
			return true;
		}

		return false;
	}

	/**
	 * Vector manipulation class for curio engine.
	 * 
	 * @author Cem05
	 *
	 */
	public static class Vector {

		public static void rotateAround(Vector2f point, Vector2f origin, float angle) {
			rotateAround(point, origin.x, origin.y, angle);
		};

		public static void rotateAround(Vector2f point, float angle) {
			rotateAround(point, 0, 0, angle);
		};

		/**
		 * Rotate the given point in origin by angle.
		 * 
		 * @param point   the point to rotate
		 * @param originX origin x component
		 * @param originY origin y component
		 * @param angle   angle in radians.
		 */
		public static void rotateAround(Vector2f point, float originX, float originY, float angle) {
			float dx = point.x - originX;
			float dy = point.y - originY;

			point.set(//
					Math.cos(angle) * dx - Math.sin(angle) * dy + originX, //
					Math.sin(angle) * dx + Math.cos(angle) * dy + originY);//

		};

		public Vector2f getVectorBetween(Vector2f point1, Vector2f point2, float t) {
			Vector2f deltaDistance = new Vector2f(point1.x - point2.x, point1.y - point2.y);
			return new Vector2f(point1.x + t * deltaDistance.x, point1.y + t * deltaDistance.y);
		}
	}

	/**
	 * Inertia calculation class.
	 * 
	 * @author Cem05
	 *
	 */
	public static class Inertia {
		/**
		 * calculation formula: 0.5 * PI * radius^4.
		 * 
		 * @param radius the radius of the circle.
		 * @return inertia value.
		 */
		public static float calculateCircle(float radius) {
			return (float) (0.5f * Math.PI * Math.pow(radius, 4));
		}

		public static float calculateRectangle(float width, float height) {
			return (1 / 12f) * width * height * (width * width + height * height);
		}
	}

}
