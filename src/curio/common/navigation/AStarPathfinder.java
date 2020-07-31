package common.navigation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import common.tilemapsys.TileCoordinate2;
import common.tilemapsys.TileMap;
import common.tilemapsys.Grid2;

/**
 * AStar Pathfinding algorithm for {@link TileMap}.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class AStarPathfinder implements INavigation {
	private HashMap<Integer, Integer> manipulatedIDs;
	private ArrayList<Integer> bannedIDs;

	private TileMap iCellMap;

	private Node2D startNode;
	private Node2D targetNode;

	private PriorityQueue<Node2D> openList;
	private PriorityQueue<Node2D> closedList;

	private Grid2 grid;
	private Path path;
	private ArrayList<TileCoordinate2> bannedCells;

	public AStarPathfinder() {
		this.openList = new PriorityQueue<Node2D>(fCostComparator);
		this.closedList = new PriorityQueue<Node2D>(fCostComparator);

		this.manipulatedIDs = new HashMap<Integer, Integer>();
		this.bannedIDs = new ArrayList<Integer>();
		this.bannedCells = new ArrayList<TileCoordinate2>();

		this.path = new Path();
	}

	/**
	 * Sets current {@link TileMap} and {@link Grid2} to search path.
	 * 
	 * @param cellularMap CellularMap to search path.
	 * @param grid        Grid to create path.
	 */
	public AStarPathfinder setParameters(TileMap iCellMap, Grid2 grid) {
		this.clear();
		this.iCellMap = iCellMap;
		this.grid = grid;
		return this;
	}

	/**
	 * Clears all data.
	 */

	public AStarPathfinder clear() {
		this.openList.clear();
		this.closedList.clear();

		this.manipulatedIDs.clear();
		this.bannedIDs.clear();
		this.bannedCells.clear();

		this.path.clear();
		return this;
	}

	/**
	 * Begin PathFinding from start cell to target cell. Method checks both start
	 * and target cell in BannedIDList then executes search loop.
	 * 
	 * @param startCellCoordinate  The cell to start search.
	 * @param targetCellCoordinate The cell to reach target.
	 */
	public AStarPathfinder search(TileCoordinate2 startCellCoordinate, TileCoordinate2 targetCellCoordinate) {
		this.targetNode = new Node2D(targetCellCoordinate);

		if (iCellMap.isInBorders(startCellCoordinate.x(), startCellCoordinate.y()) == false) {
			return this;
		}

		else if (iCellMap.isInBorders(targetCellCoordinate.x(), targetCellCoordinate.y()) == false) {
			return this;
		}

		if (checkBannedIDList(this.targetNode) == true) {
			return this;
		}

		this.startNode = new Node2D(startCellCoordinate);
		this.startNode.gCost = 0;

		this.openList.add(this.startNode);

		search_Loop: while (!this.openList.isEmpty()) {
			Node2D currentNode = this.openList.poll();

			if (checkBannedIDList(currentNode) == true) {
				this.bannedCells.add(currentNode);
				continue search_Loop;
			}

			if (currentNode.equals(this.targetNode)) {
				this.targetNode.parentNode = currentNode.parentNode;
				break search_Loop;
			}
			this.closedList.add(currentNode);

			for (Node2D neighborNode : currentNode.getNeighborNodes(iCellMap)) {
				boolean ifItsInClosedList = checkClosedList(neighborNode);
				int tentative_gCost = neighborNode.calculateGCost(currentNode);

				if (tentative_gCost <= neighborNode.gCost) {
					neighborNode.gCost = tentative_gCost;
					neighborNode.calculateHCost(this.targetNode);
					neighborNode.addCost(addCost(neighborNode));
					neighborNode.parentNode = currentNode;
					if (ifItsInClosedList == false) {
						this.openList.add(neighborNode);
					}
				}
			}
		}
		Node2D currentNode = this.targetNode;

		while (currentNode != null) {
			this.path.add(this.grid.gridToWorld(currentNode));
			currentNode = currentNode.parentNode;
		}
		return this;
	}

	private boolean checkClosedList(Node2D currentNode) {
		for (Node2D closedListNode : this.closedList) {
			if (closedListNode.equals(currentNode) == true) {
				return true;
			}
		}
		return false;
	}

	private boolean checkBannedIDList(Node2D currentNode) {
		for (int bannedIDs : this.bannedIDs) {
			if (this.iCellMap.getCellID(currentNode.x, currentNode.y) == bannedIDs) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add or remove cost from given ID to prevent or prioritize in search.
	 * 
	 * @param id        The ID to manipulate cost.
	 * @param addedCost The cost of the ID.
	 */
	public AStarPathfinder addManipulatedID(int id, int addedCost) {
		this.manipulatedIDs.put(id, addedCost);
		return this;
	}

	/**
	 * Ban an ID to prevent search to that {@link Tile}s that have this ID.
	 * 
	 * @param id The ID to ban.
	 */
	public AStarPathfinder addBannedID(int id) {
		this.bannedIDs.add(id);
		return this;
	}

	private int addCost(TileCoordinate2 cellCoordinate) {
		int cellID = this.iCellMap.getCellID(cellCoordinate.x, cellCoordinate.y);
		for (int id : this.manipulatedIDs.keySet()) {
			if (cellID == id) {
				return this.manipulatedIDs.get(id);
			}
		}
		return 0;
	}

	private Comparator<Node2D> fCostComparator = new Comparator<Node2D>() {
		@Override
		public int compare(Node2D node1, Node2D node2) {
			return Integer.compare(node1.getFCost(), node2.getFCost());
		};
	};

	/**
	 * Returns all the searched {@link Node}s.
	 * 
	 * @return The list of {@link Node}.
	 */
	public ArrayList<Node2D> getSearchedNodes() {
		return new ArrayList<Node2D>(this.closedList);
	}

	/**
	 * Returns the start {@link Tile}. Null if start {@link Tile} is banned.
	 * 
	 * @return The {@link TileCoordinate2} of start.
	 */
	public TileCoordinate2 getStartCellCoordinate() {
		return this.startNode;
	}

	/**
	 * Returns the target {@link Tile}. Null if target {@link Tile} is banned.
	 * 
	 * @return The {@link TileCoordinate2} of target.
	 */
	public TileCoordinate2 getTargetCellCoordinate() {
		// TODO Auto-generated method stub
		return this.targetNode;
	}

	/**
	 * Returns the created {@link Path}.
	 * 
	 * @return The {@link Path} to the target from start.
	 */
	public Path getPath() {
		return this.path;
	}

	/**
	 * This method not used by AStarPthfinder
	 * 
	 * @return Null.
	 */
	@Override
	public ArrayList<TileCoordinate2> getResult() {
		return null;
	}

	/**
	 * Returns the list of banned {@link Tile}s.
	 * 
	 * @return The list of banned {@link Tile}s.
	 */

	@Override
	public ArrayList<TileCoordinate2> getBannedCells() {
		// TODO Auto-generated method stub
		return this.bannedCells;
	}

	/**
	 * Returns the list of searched {@link Tile}s.
	 * 
	 * @return The list of searched {@link Tile}s.
	 */
	@Override
	public ArrayList<TileCoordinate2> getSearchedCells() {
		// TODO Auto-generated method stub
		return new ArrayList<TileCoordinate2>(this.closedList);
	}

}