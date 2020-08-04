package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.buffers.IndexBuffer;
import graphics.Mesh.DrawType;

public class OGL_IndexBuffer extends OGL_Buffer {
	public OGL_IndexBuffer(DrawType method) {
		super(method);
	}

	public void uploadSubData(IndexBuffer indexBuffer, int index) {
		indexBuffer.flip();
		indexBuffer.resetIndex();
		bind(indexBuffer);
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, index * Integer.BYTES, indexBuffer.getData());
		indexBuffer.clear();
	}

	public void uploadData(IndexBuffer indexBuffer) {
		indexBuffer.flip();
		indexBuffer.resetIndex();
		bind(indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getData(), super.getDrawMethod());
		indexBuffer.clear();
	}

	public void GPUMemAlloc(IndexBuffer indexBuffer) {
		bind(indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity(), super.getDrawMethod());
	}

	public void GPUMemLoad(IndexBuffer indexBuffer) {
		bind(indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getData(), super.getDrawMethod());
	}

	public void bind(IndexBuffer indexBuffer) {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, super.getID());
	}
}
