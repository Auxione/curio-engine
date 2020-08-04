package common.geom.triangulators;

/**
 * 1-Dimensional quad Triangulator for surfaces with no thickness.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class QuadTriangulator implements Triangulator {
	private int[] indices;

	public void calculate(int xSize, int ySize) {
		indices = new int[xSize * ySize * 6];
		int vertices = 0;
		int indices = 0;

		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				this.indices[indices + 0] = vertices + 0;
				this.indices[indices + 1] = vertices + xSize + 1;
				this.indices[indices + 2] = vertices + 1;

				this.indices[indices + 3] = vertices + 1;
				this.indices[indices + 4] = vertices + xSize + 1;
				this.indices[indices + 5] = vertices + xSize + 2;

				vertices++;
				indices += 6;
			}
			vertices++;
		}

	}

	@Override
	public int[] getIndices() {
		return this.indices;
	}
}
