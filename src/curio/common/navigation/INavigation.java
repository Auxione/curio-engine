package common.navigation;

import java.util.ArrayList;

import common.tilemapsys.TileCoordinate2;

/**
 * Interface for navigation and search algorithms.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface INavigation {
	public ArrayList<TileCoordinate2> getResult();

	public ArrayList<TileCoordinate2> getBannedCells();

	public ArrayList<TileCoordinate2> getSearchedCells();
}
