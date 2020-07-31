package common.tilemapsys;

import org.joml.Vector2f;

/**
 * Grid2 for {@link TileMap}. Stores size of the single tile.
 * 
 * @author Mehmet Cem Zarifoglu
 */
public class Grid2 {
	/**
	 * single tile size in game world.
	 */
	protected Vector2f size;

	/**
	 * Create grid2 object with given parameters as a single cell size.
	 * 
	 * @param x the width of the cell.
	 * @param y the height of the cell.
	 */
	public Grid2(int x, int y) {
		this.size = new Vector2f(x, y);
	}

	/**
	 * Set current grid size.
	 * 
	 * @param x Size of the x axis.
	 * @param y Size of the y axis.
	 */
	public void setSizes(float x, float y) {
		this.size.set(x, y);
	}

	/**
	 * Returns world position of the tile.
	 * 
	 * @param cellCoordinate CellCoordinate of the cell.
	 * @return Cells position in world.
	 */
	public Vector2f gridToWorld(TileCoordinate2 cellCoordinate) {
		return new Vector2f(cellCoordinate.x * this.size.x, cellCoordinate.y * this.size.y);
	}

	/**
	 * Returns {@link TileCoordinate2} from world position of a point.
	 * 
	 * @param x X position of the point.
	 * @param y Y position of the point.
	 * 
	 * @return {@link TileCoordinate2} of the point.
	 */

	public TileCoordinate2 worldToGrid(float x, float y) {
		int cx = (int) Math.floor(x / this.size.x);
		int cy = (int) Math.floor(y / this.size.y);
		return new TileCoordinate2(cx, cy);
	}

	/**
	 * The world width of the single tile.
	 * 
	 * @return width of the tile.
	 */
	public float getX() {
		return this.size.x;
	}

	/**
	 * The world height of the single tile.
	 * 
	 * @return height of the tile.
	 */
	public float getY() {
		return this.size.y;
	}
}
