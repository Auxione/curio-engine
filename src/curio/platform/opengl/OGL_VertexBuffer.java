package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.buffers.VertexBuffer;
import graphics.Mesh.DrawType;

public final class OGL_VertexBuffer extends OGL_Buffer {
	public static final int SHADERLOCATION = 0;
	private int usage;

	public OGL_VertexBuffer(DrawType drawType) {
		super();
		this.usage = OGL_Buffer.getOGLType(drawType);
	}

	public void uploadData(VertexBuffer vertexBuffer) {
		vertexBuffer.flip();
		bind(vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer.getData(), this.usage);
		vertexBuffer.clear();
	}

	public void uploadSubData(int index, VertexBuffer vertexBuffer) {
		vertexBuffer.flip();
		bind(vertexBuffer);
		glBufferSubData(GL_ARRAY_BUFFER, index * vertexBuffer.getBytesPerVertex(), vertexBuffer.getData());
		vertexBuffer.clear();
	}

	public void GPUMemAlloc(VertexBuffer vertexBuffer) {
		bind(vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer.capacity(), this.usage);
		initVertexAttribPointer(vertexBuffer);
	}

	public void GPUMemLoad(VertexBuffer vertexBuffer) {
		bind(vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer.getData(), this.usage);
		initVertexAttribPointer(vertexBuffer);
	}

	private void initVertexAttribPointer(VertexBuffer vertexBuffer) {
		int shaderIndex = 0;
		int currentPosition = 0;
		for (int i = 0; i < vertexBuffer.getDataSizes().length; i++) {
			glVertexAttribPointer(SHADERLOCATION + shaderIndex, vertexBuffer.getDataSizes()[i], GL_FLOAT, false,
					vertexBuffer.getBytesPerVertex(), currentPosition * Float.BYTES);
			currentPosition += vertexBuffer.getDataSizes()[i];
			shaderIndex++;
		}
	}

	public void bind(VertexBuffer vertexBuffer) {
		glBindBuffer(GL_ARRAY_BUFFER, super.getID());
		for (int i = 0; i < vertexBuffer.getDataSizes().length; i++) {
			glEnableVertexAttribArray(SHADERLOCATION + i);
		}
	}
}
