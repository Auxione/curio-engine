package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.buffers.VertexBuffer;
import graphics.Mesh.DrawType;

public final class OGL_VertexBuffer extends OGL_Buffer {
	public static final int SHADERLOCATION = 0;

	public OGL_VertexBuffer(DrawType method) {
		super(method);
	}

	public void uploadData(VertexBuffer vertexBuffer) {
		vertexBuffer.flip();
		bind(vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer.getData(), super.getDrawMethod());
		vertexBuffer.clear();
	}

	public void uploadSubData(VertexBuffer vertexBuffer, int index) {
		vertexBuffer.flip();
		bind(vertexBuffer);
		glInvalidateBufferData(this.getID());
		glBufferSubData(GL_ARRAY_BUFFER, index * vertexBuffer.getBytesPerData(), vertexBuffer.getData());
		vertexBuffer.clear();
	}

	public void GPUMemAlloc(VertexBuffer vertexBuffer) {
		bind(vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer.capacity(), super.getDrawMethod());
		initVertexAttribPointer(vertexBuffer);
	}

	public void GPUMemLoad(VertexBuffer vertexBuffer) {
		bind(vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer.getData(), super.getDrawMethod());
		initVertexAttribPointer(vertexBuffer);
	}

	private void initVertexAttribPointer(VertexBuffer vertexBuffer) {
		int shaderIndex = 0;
		int currentPosition = 0;
		for (int i = 0; i < vertexBuffer.getDataSizes().length; i++) {
			glVertexAttribPointer(SHADERLOCATION + shaderIndex, vertexBuffer.getDataSizes()[i], GL_FLOAT, false,
					vertexBuffer.getBytesPerData(), currentPosition * Float.BYTES);
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
