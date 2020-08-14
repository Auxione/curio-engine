package common.tilemapsys;

/**
 * Interface for tilemap system.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public abstract class Tilemap {
	/**
	 * Returns the width of the map.
	 * 
	 * @return the width of the map.
	 */
	public abstract int getWidth();

	/**
	 * Returns the height of the map.
	 * 
	 * @return the height of the map.
	 */
	public abstract int getHeight();

	/**
	 * Check if the given coordinates is in the tilemap space.
	 * 
	 * @param x x coordinate of the given coordinate.
	 * @param y y coordinate of the given coordinate.
	 * @return true if its in tilemap space.
	 */
	public abstract boolean isInBorders(int x, int y);

	public final boolean isInBorders(TileCoordinate2 tileCoordinate2) {
		return isInBorders(tileCoordinate2.x, tileCoordinate2.y);
	}

	public abstract void applyRule(TilemapRule tileMapRule);

	/**
	 * Returns the ID of the cell in given coordinates. See {@link Tile}
	 * 
	 * @param x x coordinate of the given coordinate.
	 * @param y y coordinate of the given coordinate.
	 * @return ID of the cell. Returns 0 if its not in tilemap space.
	 */
	public abstract int getCellID(int x, int y);

	/**
	 * Returns the ID of the cell in given coordinates. See {@link Tile}
	 * 
	 * @param x x coordinate of the given coordinate.
	 * @param y y coordinate of the given coordinate.
	 * @return ID of the cell. Returns 0 if its not in tilemap space.
	 */
	public int getCellID(TileCoordinate2 tileCoordinate2) {
		return getCellID(tileCoordinate2.x, tileCoordinate2.y);
	}
}
