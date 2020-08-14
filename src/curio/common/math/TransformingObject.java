package common.math;

import org.joml.Vector2f;

/**
 * Interface for applying transforms to implemented object.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface TransformingObject {
	public void reBuild();

	public Vector2f[] getDefaultPoints();

	public int[] getAxisIndices();
}
