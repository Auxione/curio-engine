package common.navigation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import common.tilemapsys.TileCoordinate2;
import common.tilemapsys.Tilemap;

/**
 * BreadthFirstSearch is used to detect differences in {@link Tilemap}. Search
 * checks if the current {@link Tile} is in wanted list or banned list then
 * spread to its neighbors.
 * 
 * @author Mehmet Cem Zarifoglu
 */

public class BreadthFirstSearch {
	private boolean[][] visited;
	private ArrayList<Integer> bannedIDs;
	private ArrayList<Integer> wantedIDs;
	private Tilemap tilemap;

	private TileCoordinate2 startCellCoordinate;

	private ArrayList<TileCoordinate2> bannedCells;
	private ArrayList<TileCoordinate2> searchedCells;
	private ArrayList<TileCoordinate2> resultCellCoordinates;

	public BreadthFirstSearch() {
		this.searchedCells = new ArrayList<TileCoordinate2>();
		this.bannedCells = new ArrayList<TileCoordinate2>();
		this.resultCellCoordinates = new ArrayList<TileCoordinate2>();

		this.bannedIDs = new ArrayList<Integer>();
		this.wantedIDs = new ArrayList<Integer>();
	}

	/**
	 * Sets {@link Tilemap}, {@link Grid2} and layer index of the search.
	 * 
	 * @param cellularMap The CellularMap to search path.
	 * @param searchLayer The layer to search.
	 */
	public BreadthFirstSearch setParameters(Tilemap tilemap, int searchLayer) {
		reset();
		this.tilemap = tilemap;
		this.visited = new boolean[this.tilemap.getWidth()][this.tilemap.getHeight()];
		return this;
	}

	/**
	 * Clears all data.
	 */
	public void reset() {
		this.startCellCoordinate = null;
		this.searchedCells.clear();
		this.bannedCells.clear();
		this.resultCellCoordinates.clear();
		this.wantedIDs.clear();
		this.bannedIDs.clear();
	}

	/**
	 * Begin search from start {@link TileCoordinate2}.
	 * 
	 * @param startCellCoordinate The {@link TileCoordinate2} to start search.
	 */

	public BreadthFirstSearch search(TileCoordinate2 startCellCoordinate) {
		if (this.wantedIDs.isEmpty() == false) {
			Queue<TileCoordinate2> queue = new LinkedList<>();
			queue.add(startCellCoordinate);
			this.startCellCoordinate = startCellCoordinate;
			search_Loop: while (queue.isEmpty() == false) {
				TileCoordinate2 currentCellCoordinate = queue.poll();
				int x = currentCellCoordinate.x();
				int y = currentCellCoordinate.y();

				this.searchedCells.add(new TileCoordinate2(x, y));

				for (int bi : bannedIDs) {
					if (tilemap.getCellID(x, y) == bi) {
						this.bannedCells.add(new TileCoordinate2(x, y));
						continue search_Loop;
					}
				}
				if (visited[x][y] == true) {
					continue search_Loop;
				}

				for (int wi : wantedIDs) {
					if (tilemap.getCellID(x, y) == wi) {
						this.resultCellCoordinates.add(new TileCoordinate2(x, y));
						break;
					}
				}
				visited[x][y] = true;

				if (y - 1 != -1 && visited[x][y - 1] == false) {
					queue.add(new TileCoordinate2(x, y - 1)); // go north
				}
				if (y + 1 != visited[x].length && visited[x][y + 1] == false) {
					queue.add(new TileCoordinate2(x, y + 1)); // go south
				}

				if (x - 1 != -1 && visited[x - 1][y] == false) {
					queue.add(new TileCoordinate2(x - 1, y)); // go west
				}
				if (x + 1 != visited.length && visited[x + 1][y] == false) {
					queue.add(new TileCoordinate2(x + 1, y)); // go east
				}
			}
		}
		return this;
	}

	/**
	 * Ban an ID to prevent search to that {@link Tile}s that have this ID.
	 * 
	 * @param id The ID to ban.
	 */
	public BreadthFirstSearch addBannedID(int i) {
		this.bannedIDs.add(i);
		return this;
	}

	/**
	 * adds ID to pass searched {@link Tile}s to get result.
	 * 
	 * @param i ID of the {@link Tile}.
	 */
	public BreadthFirstSearch addWantedID(int i) {
		this.wantedIDs.add(i);
		return this;
	}

	/**
	 * Returns the start {@link TileCoordinate2}. Null if start
	 * {@link TileCoordinate2} is banned.
	 * 
	 * @return The {@link TileCoordinate2}.
	 */
	public TileCoordinate2 getStartNode() {
		return startCellCoordinate;
	}

	/**
	 * Returns the result.
	 * 
	 * @return The ArrayList of {@link TileCoordinate2}s.
	 */

	public ArrayList<TileCoordinate2> getResult() {
		return resultCellCoordinates;
	}

	/**
	 * Returns the ArrayList of banned {@link Tile}s.
	 * 
	 * @return The ArrayList of banned {@link Tile}s.
	 */

	public ArrayList<TileCoordinate2> getBannedCells() {
		return bannedCells;
	}

	/**
	 * Returns the ArrayList of searched {@link Tile}s.
	 * 
	 * @return The ArrayList of searched {@link Tile}s.
	 */

	public ArrayList<TileCoordinate2> getSearchedCells() {
		return searchedCells;
	}

}