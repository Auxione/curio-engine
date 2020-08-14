package platform.opengl;

import common.buffers.IndexBuffer;
import common.buffers.VertexBuffer;
import common.utilities.NativeObject;
import graphics.Mesh;

import static org.lwjgl.opengl.GL45.*;

public class OGL_VertexArray extends Mesh implements NativeObject, OGL_Object {
	private int id;
	private int DRAW_MODE;

	private OGL_VertexBuffer vertexBuffer;
	private OGL_IndexBuffer indexBuffer;

	public OGL_VertexArray(DrawMode mode, DrawType bufferType, VertexBuffer vertexBuffer, IndexBuffer indexBuffer) {
		super(vertexBuffer, indexBuffer);
		this.id = glGenVertexArrays();

		this.vertexBuffer = new OGL_VertexBuffer(bufferType);
		this.indexBuffer = new OGL_IndexBuffer(bufferType);
		setDrawMode(mode);
	}

	public void setDrawMode(DrawMode mode) {
		switch (mode) {
		case TRIANGLE_STRIP:
			this.DRAW_MODE = GL_TRIANGLE_STRIP;
			break;
		case TRIANGLE_FAN:
			this.DRAW_MODE = GL_TRIANGLE_FAN;
			break;
		case LINES:
			this.DRAW_MODE = GL_LINES;
			break;
		case LINE_LOOP:
			this.DRAW_MODE = GL_LINE_LOOP;
			break;
		case LINE_STRIP:
			this.DRAW_MODE = GL_LINE_STRIP;
			break;
		case POINTS:
			this.DRAW_MODE = GL_POINTS;
			break;
		default:
			this.DRAW_MODE = GL_TRIANGLES;
			break;
		}
	}

	@Override
	public void uploadSubData(int VBindex, int IBindex) {
		bind();
		if (VBindex != -1) {
			this.vertexBuffer.uploadSubData(VBindex, super.vertexBuffer);
		}
		if (IBindex != -1) {
			this.indexBuffer.uploadSubData(IBindex, super.indexBuffer);
		}
	}

	@Override
	public void uploadData() {
		bind();
		this.vertexBuffer.uploadData(super.vertexBuffer);
		this.indexBuffer.uploadData(super.indexBuffer);
	}

	@Override
	public void renderIndexed(int count) {
		bind();
		glDrawElements(this.DRAW_MODE, count, GL_UNSIGNED_INT, 0);
	}

	@Override
	public void renderArrays(int first, int count) {
		bind();
		glDrawArrays(this.DRAW_MODE, first, count);
	}

	public void GPUMemAlloc() {
		bind();
		this.vertexBuffer.GPUMemAlloc(super.vertexBuffer);
		this.indexBuffer.GPUMemAlloc(super.indexBuffer);
	}

	public void GPUMemLoad() {
		bind();
		this.vertexBuffer.GPUMemLoad(super.vertexBuffer);
		this.indexBuffer.GPUMemLoad(super.indexBuffer);
	}

	@Override
	public void bind() {
		glBindVertexArray(this.id);
		this.vertexBuffer.bind(super.vertexBuffer);
		this.indexBuffer.bind();
	}

	@Override
	public void terminate() {
		glDeleteVertexArrays(this.id);
	}

	@Override
	public int getID() {
		return this.id;
	}
}
