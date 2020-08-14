package physics.collision;

import org.joml.Vector2f;

public interface SATObject {

	public Vector2f[] getDefaultPoints();

	public int[] getAxisIndices();
}
