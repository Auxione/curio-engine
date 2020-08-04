package graphics.renderer3d;

/**
 * Interface for polygons that needs to rendered with indices.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface Indexable {
	/**
	 * @see Indexable
	 * @return the indices.
	 */
	public int[] getIndexArray();
}
