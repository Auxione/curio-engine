package common.navigation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import common.tilemapsys.TileCoordinate2;
import common.tilemapsys.Tilemap;
import common.tilemapsys.Grid2;

/**
 * AStar Pathfinding algorithm for {@link Tilemap}.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class AStarPathfinder {
	private HashMap<Integer, Integer> manipulatedIDs;
	private ArrayList<Integer> bannedIDs;

	private Tilemap tilemap;

	private Node2 startNode;
	private Node2 targetNode;

	private PriorityQueue<Node2> openList;
	private PriorityQueue<Node2> closedList;

	private ArrayList<TileCoordinate2> bannedCells;

	public AStarPathfinder() {
		this.openList = new PriorityQueue<Node2>(fCostComparator);
		this.closedList = new PriorityQueue<Node2>(fCostComparator);

		this.manipulatedIDs = new HashMap<Integer, Integer>();
		this.bannedIDs = new ArrayList<Integer>();
		this.bannedCells = new ArrayList<TileCoordinate2>();

	}

	/**
	 * Sets current {@link Tilemap} and {@link Grid2} to search path.
	 * 
	 * @param cellularMap CellularMap to search path.
	 * @param grid        Grid to create path.
	 */
	public AStarPathfinder setParameters(Tilemap tilemap) {
		this.clear();
		this.tilemap = tilemap;
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

		return this;
	}

	/**
	 * Begin PathFinding from start cell to target cell. Method checks both start
	 * and target cell in BannedIDList then executes search loop.
	 * 
	 * @param startCellCoordinate  The cell to start search.
	 * @param targetCellCoordinate The cell to reach target.
	 */
	public ArrayList<TileCoordinate2> search(TileCoordinate2 startCellCoordinate, TileCoordinate2 targetCellCoordinate) {
		this.targetNode = new Node2(targetCellCoordinate);

		if (tilemap.isInBorders(startCellCoordinate.x(), startCellCoordinate.y()) == false) {
			return null;
		}

		else if (tilemap.isInBorders(targetCellCoordinate.x(), targetCellCoordinate.y()) == false) {
			return null;
		}

		if (checkBannedIDList(this.targetNode) == true) {
			return null;
		}

		this.startNode = new Node2(startCellCoordinate);
		this.startNode.gCost = 0;

		this.openList.add(this.startNode);

		search_Loop: while (!this.openList.isEmpty()) {
			Node2 currentNode = this.openList.poll();

			if (checkBannedIDList(currentNode) == true) {
				this.bannedCells.add(currentNode);
				continue search_Loop;
			}

			if (currentNode.equals(this.targetNode)) {
				this.targetNode.parentNode = currentNode.parentNode;
				break search_Loop;
			}
			this.closedList.add(currentNode);

			for (Node2 neighborNode : currentNode.getNeighborNodes(tilemap)) {
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
		ArrayList<TileCoordinate2> out = new ArrayList<TileCoordinate2>();
		while (this.targetNode != null) {
			out.add(this.targetNode);
			this.targetNode = this.targetNode.parentNode;
		}
		return out;
	}

	private boolean checkClosedList(Node2 currentNode) {
		for (Node2 closedListNode : this.closedList) {
			if (closedListNode.equals(currentNode) == true) {
				return true;
			}
		}
		return false;
	}

	private boolean checkBannedIDList(Node2 currentNode) {
		for (int bannedIDs : this.bannedIDs) {
			if (this.tilemap.getCellID(currentNode.x, currentNode.y) == bannedIDs) {
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
	public final AStarPathfinder manipulateIDCost(int id, int addedCost) {
		this.manipulatedIDs.put(id, addedCost);
		return this;
	}

	/**
	 * Ban an ID to prevent search to that {@link Tile}s that have this ID.
	 * 
	 * @param id The ID to ban.
	 */
	public final AStarPathfinder addBannedID(int id) {
		this.bannedIDs.add(id);
		return this;
	}

	private int addCost(TileCoordinate2 cellCoordinate) {
		int cellID = this.tilemap.getCellID(cellCoordinate.x, cellCoordinate.y);
		for (int id : this.manipulatedIDs.keySet()) {
			if (cellID == id) {
				return this.manipulatedIDs.get(id);
			}
		}
		return 0;
	}

	private Comparator<Node2> fCostComparator = new Comparator<Node2>() {
		@Override
		public int compare(Node2 node1, Node2 node2) {
			return Integer.compare(node1.getFCost(), node2.getFCost());
		};
	};

	public final Node2[] getSearchedNodes() {
		Node2[] temp = new Node2[this.closedList.size()];
		return this.closedList.toArray(temp);
	}

	/**
	 * Returns the start {@link Tile}. Null if start {@link Tile} is banned.
	 * 
	 * @return The {@link TileCoordinate2} of start.
	 */
	public final TileCoordinate2 getStartNode() {
		return this.startNode;
	}

	/**
	 * Returns the target {@link Tile}. Null if target {@link Tile} is banned.
	 * 
	 * @return The {@link TileCoordinate2} of target.
	 */
	public final TileCoordinate2 getTargetNode() {
		return this.targetNode;
	}

}