package platform.opengl;

import java.util.ArrayList;

import common.utilities.NativeObject;
import graphics.renderer3d.Mesh;

import static org.lwjgl.opengl.GL45.*;

public class OGL_VertexArray implements NativeObject, OGL_Object, Mesh {
	public ArrayList<OGL_Buffer> buffers = new ArrayList<OGL_Buffer>();

	private int id;
	private int DRAW_MODE;

	private OGL_IndexBuffer indexBuffer;
	private OGL_VertexBuffer vertexBuffer;

	public enum Mode {
		TRIANGLES, LINES, LINE_STRIP, LINE_LOOP, TRIANGLE_STRIP, TRIANGLE_FAN, POINTS
	}

	public OGL_VertexArray(Mode mode) {
		this.id = glGenVertexArrays();
		setDrawMode(mode);
	}

	public void setDrawMode(Mode mode) {
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

	public void add(OGL_Buffer iBuffer) {
		this.buffers.add(iBuffer);
	}

	@Override
	public void terminate() {
		glDeleteVertexArrays(this.id);
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public void uploadSubData(int VBindex, int IBindex) {
		bind();
		this.vertexBuffer.uploadSubData(VBindex);
		this.indexBuffer.uploadSubData(IBindex);
	}

	@Override
	public void uploadData() {
		bind();
		this.vertexBuffer.uploadData();
		this.indexBuffer.uploadData();
	}

	@Override
	public void drawIndexed(int count) {
		bind();
		glDrawElements(this.DRAW_MODE, count, GL_UNSIGNED_INT, 0);
	}

	public void drawArrays(int first, int count) {
		bind();
		glDrawArrays(this.DRAW_MODE, first, count);
	}

	@Override
	public void GPULoadMethod(int parameter) {
		bind();
		for (OGL_Buffer iBuffer : this.buffers) {
			iBuffer.GPULoadMethod(parameter);
		}
	}

	@Override
	public void bind() {
		glBindVertexArray(this.id);
		for (OGL_Buffer iBuffer : this.buffers) {
			iBuffer.bind();
		}
	}

}
