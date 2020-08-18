package graphics;

import common.buffers.IndexBuffer;
import common.buffers.VertexBuffer;
import core.EngineSettings;
import platform.opengl.OGL_VertexArray;

public abstract class Mesh {
	public VertexBuffer vertexBuffer;
	public IndexBuffer indexBuffer;

	public enum DrawType {
		STATIC_DRAW, DYNAMIC_DRAW, STREAM_DRAW
	}

	public enum DrawMode {
		TRIANGLES, LINES, LINE_STRIP, LINE_LOOP, TRIANGLE_STRIP, TRIANGLE_FAN, POINTS
	}

	public static Mesh createInstance(DrawMode drawMode, DrawType drawType, VertexBuffer vertexBuffer,
			IndexBuffer indexBuffer) {
		switch (EngineSettings.renderer) {

		default:
			return new OGL_VertexArray(drawMode, drawType, vertexBuffer, indexBuffer);
		}
	}

	protected Mesh(int maxTriangles, int bytesPerData, int[] dataSizes) {
		this.vertexBuffer = new VertexBuffer(maxTriangles * 8, bytesPerData, dataSizes);
		this.indexBuffer = new IndexBuffer(maxTriangles * 12);
	}

	protected Mesh(VertexBuffer vertexBuffer, IndexBuffer indexBuffer) {
		this.vertexBuffer = vertexBuffer;
		this.indexBuffer = indexBuffer;
	}

	public abstract void uploadData();

	public abstract void uploadSubData(int VBindex, int IBindex);

	public abstract void bind();

	public abstract int getID();

	public abstract void renderIndexed(int count);

	public final void renderIndexed() {
		renderIndexed(indexBuffer.capacity());
	};

	public abstract void renderArrays(int first, int count);
}
