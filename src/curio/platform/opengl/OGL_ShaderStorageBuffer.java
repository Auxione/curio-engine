package platform.opengl;

import static org.lwjgl.opengl.GL45.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;


public class OGL_ShaderStorageBuffer extends OGL_Buffer {
	private int usage;

	protected OGL_ShaderStorageBuffer() {
		super();
		this.usage = GL_DYNAMIC_COPY;
	}

	public void GPUMemAlloc(ByteBuffer byteBuffer) {
		bind();
		glBufferData(GL_SHADER_STORAGE_BUFFER, byteBuffer.capacity(), this.usage);
	}

	public void GPUMemLoad(ByteBuffer byteBuffer) {
		bind();
		glBufferData(GL_SHADER_STORAGE_BUFFER, byteBuffer, this.usage);
	}

	public void uploadData(ByteBuffer byteBuffer) {
		byteBuffer.flip();
		bind();
		glBufferData(GL_SHADER_STORAGE_BUFFER, byteBuffer, this.usage);
		byteBuffer.clear();
	}

	public void uploadSubData(int index, ByteBuffer byteBuffer) {
		byteBuffer.flip();
		bind();
		glBufferSubData(GL_SHADER_STORAGE_BUFFER, index, byteBuffer);
		byteBuffer.clear();
	}

	public void getData(ByteBuffer target) {
		bind();
		ByteBuffer p = glMapBuffer(GL_SHADER_STORAGE_BUFFER, GL_WRITE_ONLY);
		MemoryUtil.memCopy(p, target);
		glUnmapBuffer(GL_SHADER_STORAGE_BUFFER);
	}

	public void bind() {
		glBindBuffer(GL_SHADER_STORAGE_BUFFER, super.getID());
	}
}
