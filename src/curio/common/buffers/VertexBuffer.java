package common.buffers;

import java.nio.FloatBuffer;
import org.lwjgl.system.MemoryUtil;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import graphics.renderer2d.Vertex2;

public class VertexBuffer implements NativeObject {
	private FloatBuffer floatBuffer;
	private int bytesPerData;
	private int[] vertexDataSizes;

	public VertexBuffer(int capacity, int bytesPerData, int[] dataSizes) {
		this.floatBuffer = MemoryUtil.memAllocFloat(capacity);
		this.bytesPerData = bytesPerData;
		this.vertexDataSizes = dataSizes;
		NativeObjectManager.register(this);
	}

	public VertexBuffer(Vertex2[] vertices) {
		this(vertices.length * Vertex2.SIZE, Vertex2.BYTES, Vertex2.DATASIZES);
		put(vertices);
	}

	public void put(Vertex2 vertex) {
		this.floatBuffer.put(Vertex2.toArray(vertex));
	}

	public void put(Vertex2[] vertices) {
		for (int i = 0; i < vertices.length; i++) {
			put(vertices[i]);
		}
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

	public int getBytesPerData() {
		return this.bytesPerData;
	}

	public int capacity() {
		return this.floatBuffer.capacity();
	}
}
