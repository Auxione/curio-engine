package common.tilemapsys;

import org.joml.Vector2f;

/**
 * Grid2 for {@link Tilemap}. Stores size of the single tile.
 * 
 * @author Mehmet Cem Zarifoglu
 */
public class Grid2 {
	/**
	 * single tile size in game world.
	 */
	public float x, y;
	public float offsetX, offsetY;

	/**
	 * Create grid2 object with given parameters as a single cell size.
	 * 
	 * @param x the width of the cell.
	 * @param y the height of the cell.
	 */
	public Grid2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Grid2(float x, float y, float offsetX, float offsetY) {
		this(x, y);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public Vector2f gridToWorld(TileCoordinate2 cellCoordinate, Vector2f target) {
		return gridToWorld(cellCoordinate.x, cellCoordinate.y, target);
	}

	public Vector2f gridToWorld(int x, int y, Vector2f target) {
		return target.set(x * (this.x + this.offsetX), y * (this.y + this.offsetY));
	}

	public TileCoordinate2 worldToGrid(Vector2f input, TileCoordinate2 target) {
		return worldToGrid(input.x, input.y, target);
	}

	public TileCoordinate2 worldToGrid(float x, float y, TileCoordinate2 target) {
		target.set(//
				(int) Math.floor(x / (this.x + this.offsetX)), //
				(int) Math.floor(y / (this.y + this.offsetY)));
		return target;
	}
}
