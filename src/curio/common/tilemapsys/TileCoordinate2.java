package common.tilemapsys;

import org.joml.Vector2i;

/**
 * This class represents a 2D tilemap coordinates.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class TileCoordinate2 extends Vector2i {
	/**
	 * Create an object with coordinates of (0,0).
	 */
	public TileCoordinate2() {
		super();
	}

	/**
	 * Create an object and set its coordinates to given parameters.
	 * 
	 * @param x x value of the cell coordinate.
	 * @param y y value of the cell coordinate.
	 */
	public TileCoordinate2(int x, int y) {
		super(x, y);
	}

	/**
	 * Returns new {@link TileCoordinate2} from current tile neighbors.
	 * 
	 * @param cellularMap To check if the neighbor is in the map.
	 * @param offsetx     x offset of the cell.
	 * @param offsety     y offset of the cell.
	 * @return cellCoordinate CellCoordinate of the neighbor cell.
	 */

	public TileCoordinate2 getNeighborTile(Tilemap tileMap, int offsetx, int offsety) {
		int px = x + offsetx;
		int py = y + offsety;
		if (tileMap.isInBorders(px, py) == true) {
			return new TileCoordinate2(px, py);
		}
		return null;
	}

	/**
	 * Returns all the neighbors of the current cell.
	 * 
	 * @param cellularMap To check if the neighbor is in the map.
	 * @return cellCoordinate CellCoordinate of the neighbor cell.
	 */
	public TileCoordinate2[] getNeighborTiles(Tilemap tileMap) {
		TileCoordinate2[] cells = new TileCoordinate2[4];
		cells[0] = this.getNeighborTile(tileMap, -1, 0);
		cells[1] = this.getNeighborTile(tileMap, 0, -1);
		cells[2] = this.getNeighborTile(tileMap, +1, 0);
		cells[3] = this.getNeighborTile(tileMap, 0, +1);
		return cells;
	}

}