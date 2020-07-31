package platform.opengl;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL45.*;
import org.lwjgl.system.MemoryUtil;

import graphics.renderer2d.Vertex2;

public class OGL_VertexBuffer extends OGL_Buffer {
	public static final int SHADERLOCATION = 0;
	private FloatBuffer floatBuffer;
	private int bytesPerData;
	private int[] vertexDataSizes;

	public OGL_VertexBuffer(Method method, int capacity, int bytesPerData, int[] dataSizes) {
		super(method);
		this.floatBuffer = MemoryUtil.memAllocFloat(capacity);
		this.bytesPerData = bytesPerData;
		this.vertexDataSizes = dataSizes;
	}

	public void put(Vertex2 vertex) {
		this.floatBuffer.put(Vertex2.toArray(vertex));
	}

	public void put(Vertex2[] vertices) {
		for (int i = 0; i < vertices.length; i++) {
			put(vertices[i]);
		}
	}

	public void uploadSubData(int index) {
		this.floatBuffer.flip();
		bind();
		glInvalidateBufferData(this.getID());
		glBufferSubData(GL_ARRAY_BUFFER, index * this.bytesPerData, this.floatBuffer);
		this.floatBuffer.clear();
	}

	public void uploadData() {
		this.floatBuffer.flip();
		bind();
		glBufferData(GL_ARRAY_BUFFER, this.floatBuffer, super.getDrawMethod());
		this.floatBuffer.clear();
	}

	@Override
	public void GPULoadMethod(int parameter) {
		bind();
		if (parameter == 1) {
			glBufferData(GL_ARRAY_BUFFER, this.floatBuffer.capacity(), super.getDrawMethod());
		} else {
			glBufferData(GL_ARRAY_BUFFER, this.floatBuffer, super.getDrawMethod());
		}

		int shaderIndex = 0;
		int currentPosition = 0;

		for (int i = 0; i < this.vertexDataSizes.length; i++) {
			glVertexAttribPointer(SHADERLOCATION + shaderIndex, this.vertexDataSizes[i], GL_FLOAT, false,
					this.bytesPerData, currentPosition * Float.BYTES);
			currentPosition += this.vertexDataSizes[i];
			shaderIndex++;
		}
	}

	@Override
	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, super.getID());
		for (int i = 0; i < this.vertexDataSizes.length; i++) {
			glEnableVertexAttribArray(SHADERLOCATION + i);
		}
	}
}
