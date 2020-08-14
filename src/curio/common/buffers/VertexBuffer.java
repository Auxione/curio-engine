package common.buffers;

import java.nio.FloatBuffer;
import org.lwjgl.system.MemoryUtil;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import graphics.renderer2d.Vertex2;

public class VertexBuffer implements NativeObject {
	private FloatBuffer floatBuffer;
	private int bytesPerVertex;
	private int[] vertexDataSizes;

	public VertexBuffer(int capacity, int bytesPerVertex, int[] dataformat) {
		this.floatBuffer = MemoryUtil.memAllocFloat(capacity);
		this.bytesPerVertex = bytesPerVertex;
		this.vertexDataSizes = dataformat;
		NativeObjectManager.register(this);
	}

	public VertexBuffer(Vertex2[] vertices) {
		this(vertices.length * Vertex2.SIZE, Vertex2.BYTES, Vertex2.DATAFORMAT);
		put(vertices);
	}

	public void put(Vertex2 vertex) {
		this.floatBuffer.put(Vertex2.toArray(vertex));
	}

	public void put(int index, Vertex2 vertex) {
		this.floatBuffer.put(index * bytesPerVertex, Vertex2.toArray(vertex));
	}

	public void put(Vertex2[] vertices) {
		for (int i = 0; i < vertices.length; i++) {
			put(vertices[i]);
		}
	}

	public float get(int index) {
		return floatBuffer.get(index);
	}

	@Override
	public void terminate() {
		MemoryUtil.memFree(this.floatBuffer);
	}

	public int[] getDataSizes() {
		return this.vertexDataSizes;
	}

	public FloatBuffer getData() {
		return this.floatBuffer;
	}

	public void clear() {
		this.floatBuffer.clear();
	}

	public void flip() {
		this.floatBuffer.flip();
	}

	public int limit() {
		return this.floatBuffer.limit();
	}

	public int getBytesPerVertex() {
		return this.bytesPerVertex;
	}

	public int capacity() {
		return this.floatBuffer.capacity();
	}
}
