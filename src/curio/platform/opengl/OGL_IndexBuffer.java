package platform.opengl;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;
import org.lwjgl.system.MemoryUtil;

import graphics.renderer3d.Indexable;

public class OGL_IndexBuffer extends OGL_Buffer {
	private IntBuffer intBuffer;
	private int maxIndexNumber = 0;
	private int currentIndexOffset = 0;

	public OGL_IndexBuffer(Method method, int capacity) {
		super(method);
		this.intBuffer = MemoryUtil.memAllocInt(capacity);
	}

	public void put(int[] indices) {
		this.maxIndexNumber = 0;
		for (int i = 0; i < indices.length; i++) {
			if (this.maxIndexNumber < indices[i]) {
				this.maxIndexNumber = indices[i];
			}

			this.intBuffer.put(indices[i] + this.currentIndexOffset);
		}
		this.currentIndexOffset += maxIndexNumber + 1;
	}

	public void put(int index) {
		index += this.currentIndexOffset;
		this.intBuffer.put(index);
		this.currentIndexOffset += 1;
	}

	public void put(Indexable indexable) {
		put(indexable.getIndexArray());
	}

	public void uploadSubData(int index) {
		this.intBuffer.flip();
		this.currentIndexOffset = 0;
		bind();
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, index * Integer.BYTES, this.intBuffer);
		this.intBuffer.clear();
	}

	public void uploadData() {
		this.intBuffer.flip();
		this.currentIndexOffset = 0;
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.intBuffer, super.getDrawMethod());
		this.intBuffer.clear();
	}

	@Override
	public void GPULoadMethod(int parameter) {
		bind();
		if (parameter == 1) {
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.intBuffer.capacity(), super.getDrawMethod());
		} else {
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.intBuffer, super.getDrawMethod());
		}
	}

	@Override
	public void bind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, super.getID());
	}

	public int getLimit() {
		return this.intBuffer.limit();
	}
}
