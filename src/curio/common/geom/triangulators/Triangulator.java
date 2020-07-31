package common.geom.triangulators;

/**
 * Triangulation algorithm interface.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface Triangulator {
	/**
	 * Returns index data of the given the number of vertices.
	 * 
	 * @param size the number of vertices in the polygon.
	 * @return the index data.
	 */
	public int[] getIndices(int size);
}
