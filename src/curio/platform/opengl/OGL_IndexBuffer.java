package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import common.buffers.IndexBuffer;
import graphics.Mesh.DrawType;

public class OGL_IndexBuffer extends OGL_Buffer {
	private int usage;

	public OGL_IndexBuffer(DrawType drawType) {
		this.usage = OGL_Buffer.getOGLType(drawType);
	}

	public void uploadSubData(int index, IndexBuffer indexBuffer) {
		indexBuffer.flip();
		bind();
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, index * Integer.BYTES, indexBuffer.getData());
		indexBuffer.clear();
	}

	public void uploadData(IndexBuffer indexBuffer) {
		indexBuffer.flip();
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getData(), this.usage);
		indexBuffer.clear();
	}

	public void GPUMemAlloc(IndexBuffer indexBuffer) {
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity(), this.usage);
	}

	public void GPUMemLoad(IndexBuffer indexBuffer) {
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getData(), this.usage);
	}

	public void bind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, super.getID());
	}
}
