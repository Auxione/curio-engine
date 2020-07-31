package common.geom.triangulators;

/**
 * Simple triangulation algorithm for convex polygons. Algorithm starts in 0
 * then creates a triangle from next 2 points then marks the position and
 * creates another triangle from marked position in same order. The result is in
 * this format { 0, 1, 2, 0, 2, 3, ....}. Created indices represents triangles
 * in polygon. This method cannot be used for concave polygons.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class BasicTriangulator implements Triangulator {
	/**
	 * Calculates and returns index data from vertex.
	 * 
	 * @param size the number of vertices in the polygon.
	 * @return the index data.
	 */
	@Override
	public int[] getIndices(int size) {
		int[] temp = new int[(size - 2) * 3];
		int index = 0;
		for (int i = 0; i < temp.length; i += 3) {
			temp[i] = 0;
			temp[i + 1] = index + 1;
			temp[i + 2] = index + 2;
			index++;
		}
		return temp;
	}
}
