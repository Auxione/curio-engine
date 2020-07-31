package physics.collision;

import org.joml.Vector2f;

public class SAT2 {
	private static boolean separatingAxis(SATObject obj1, SATObject obj2, float dx, float dy) {
		Vector2f axisNorm = new Vector2f(dy, -dx);

		float minA = Float.POSITIVE_INFINITY, maxA = Float.NEGATIVE_INFINITY;
		float minB = Float.POSITIVE_INFINITY, maxB = Float.NEGATIVE_INFINITY;

		/* Project first polygon on axis */
		for (int k = 0; k < obj1.getPoints().length; k++) {
			float d = obj1.getPoints()[k].dot(axisNorm);

			minA = (float) Math.min(minA, d);
			maxA = (float) Math.max(maxA, d);
		}
		/* Project second polygon on axis */
		for (int k = 0; k < obj2.getPoints().length; k++) {
			float d = obj2.getPoints()[k].dot(axisNorm);

			minB = (float) Math.min(minB, d);
			maxB = (float) Math.max(maxB, d);
		}
		/* Check if intervals overlap */
		return minA > maxB || minB > maxA;
	}

	public static boolean check(SATObject obj1, SATObject obj2) {
		int[] indx1 = obj1.getAxisIndices(), indx2 = obj1.getAxisIndices();
		/* Try to find a separating axis using the first polygon's edges */

		for (int i = 0; i < indx1.length; i += 2) {
			if (separatingAxis(obj1, obj2, //
					obj1.getPoints()[indx1[i]].x - obj1.getPoints()[indx1[i + 1]].x, //
					obj1.getPoints()[indx1[i]].y - obj1.getPoints()[indx1[i + 1]].y))
				return false;
		}

		/* Try to find a separating axis using the second polygon's edges */
		for (int i = 0; i < indx2.length; i += 2) {
			if (separatingAxis(obj1, obj2, //
					obj2.getPoints()[indx2[i]].x - obj2.getPoints()[indx2[i + 1]].x, //
					obj2.getPoints()[indx2[i]].y - obj2.getPoints()[indx2[i + 1]].y))
				return false;
		}
		return true;
	}

}
