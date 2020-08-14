package common.navigation;

import java.util.ArrayList;

import common.tilemapsys.TileCoordinate2;
import common.tilemapsys.Tilemap;

/**
 * An {@link TileCoordinate2} for {@link AStarPathfinder}
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class Node2 extends TileCoordinate2 {
	private int addedCost;
	public int gCost = Integer.MAX_VALUE;
	public int hCost;

	public Node2 parentNode;

	public Node2(TileCoordinate2 cellCoordinate) {
		super.set(cellCoordinate.x(), cellCoordinate.y());
	}

	public Node2(int x, int y) {
		super.set(x, y);
	}

	public Node2 calculateHCost(Node2 node) {
		int dx = Math.abs(super.x() - node.y());
		int dy = Math.abs(super.x() - node.y());
		this.hCost = (int) Math.sqrt(dx * dx + dy * dy);
		return this;
	}

	public int calculateGCost(Node2 parentNode) {
		this.gCost = parentNode.gCost + 1;
		return this.gCost;
	}

	public Node2 addCost(int cost) {
		this.addedCost += cost;
		return this;
	}

	public int getFCost() {
		return hCost + gCost + addedCost;
	}

	public ArrayList<Node2> getNeighborNodes(Tilemap iCellMap) {
		ArrayList<Node2> cells = new ArrayList<Node2>();
		Node2 n1 = this.getNeighborNode(iCellMap, +1, 0);
		Node2 n2 = this.getNeighborNode(iCellMap, -1, 0);
		Node2 n3 = this.getNeighborNode(iCellMap, 0, +1);
		Node2 n4 = this.getNeighborNode(iCellMap, 0, -1);

		if (n1 != null) {
			cells.add(n1);
		}
		if (n2 != null) {
			cells.add(n2);
		}
		if (n3 != null) {
			cells.add(n3);
		}
		if (n4 != null) {
			cells.add(n4);
		}

		return cells;
	}

	public Node2 getNeighborNode(Tilemap iCellMap, int offsetx, int offsety) {
		int px = super.x() + offsetx;
		int py = super.y() + offsety;
		if (iCellMap.isInBorders(px, py) == true) {
			return new Node2(px, py);
		}
		return null;
	}
}