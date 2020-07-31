package physics.collision;

import java.util.ArrayList;

import common.tilemapsys.Grid2;
import common.tilemapsys.TileCoordinate2;
import common.tilemapsys.TileMap;

public class TileMapCollider {
	private ArrayList<Integer> bannedIDs = new ArrayList<Integer>();

	private TileMap cellMap;

	public TileMapCollider(TileMap cellMap) {
		this.cellMap = cellMap;
	}

	public boolean checkTileBan(Grid2 grid, float x, float y) {
		return checkTileBan(grid.worldToGrid(x, y));
	}

	public boolean checkTileBan(TileCoordinate2 tileCoordinate2) {
		for (int ID : this.bannedIDs) {
			if (ID == this.cellMap.getCellID(tileCoordinate2)) {
				return false;
			}
		}
		return true;
	}

	public void banID(int tileID) {
		this.bannedIDs.add(tileID);
	}

	public void removeBannedID(int tileID) {
		this.bannedIDs.remove(tileID);
	}
}
