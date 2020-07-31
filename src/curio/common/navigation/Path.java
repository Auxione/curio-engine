package common.navigation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.joml.Vector2f;

/**
 * Queue of positions for generated {@link AStarPathfinder} result.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class Path {
	private Queue<Vector2f> nodes;

	public Path() {
		this.nodes = new LinkedList<Vector2f>();
	}

	public Path add(Vector2f vector) {
		nodes.add(vector);
		return this;
	}

	public Vector2f peek() {
		return this.nodes.peek();
	}

	public Vector2f poll() {
		return this.nodes.poll();
	}

	public Vector2f getLast() {
		ArrayList<Vector2f> b = new ArrayList<Vector2f>(nodes);
		return b.get(b.size() - 1);
	}

	public Queue<Vector2f> getNodes() {
		return new LinkedList<Vector2f>(nodes);
	}

	public ArrayList<Vector2f> getArrayList() {
		return new ArrayList<Vector2f>(nodes);
	}

	public Path clear() {
		this.nodes.clear();
		return this;
	}
}
