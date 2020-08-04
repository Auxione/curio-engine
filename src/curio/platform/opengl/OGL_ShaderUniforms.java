package platform.opengl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glGetUniformf;
import static org.lwjgl.opengl.GL20.glGetUniformi;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1iv;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix2fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL21.glUniformMatrix3x2fv;

import java.nio.FloatBuffer;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

public class OGL_ShaderUniforms {
	private static FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);

	public static void setBool(OGL_ShaderProgram program, String name, boolean value) {
		program.bind();
		if (value == true) {
			glUniform1i(getLocation(program, name), GL_TRUE);
		} else {
			glUniform1i(getLocation(program, name), GL_FALSE);
		}
	}

	public static void setMat4f(OGL_ShaderProgram program, String name, Matrix4f matrix) {
		program.bind();//
		glUniformMatrix4fv(getLocation(program, name), false, matrix.get(matrixBuffer));
	}

	public static void setMat3x2f(OGL_ShaderProgram program, String name, Matrix3x2f matrix) {
		program.bind();
		glUniformMatrix3x2fv(getLocation(program, name), false, matrix.get(matrixBuffer));
	}

	public static void setMat3f(OGL_ShaderProgram program, String name, Matrix3f matrix) {
		program.bind();
		glUniformMatrix3fv(getLocation(program, name), false, matrix.get(matrixBuffer));
	}

	public static void setMat2f(OGL_ShaderProgram program, String name, boolean transpose, Matrix2f matrix) {
		program.bind();
		glUniformMatrix2fv(getLocation(program, name), false, matrix.get(matrixBuffer));
	}

	public static void setf(OGL_ShaderProgram program, String name, float x, float y, float z, float w) {
		program.bind();
		glUniform4f(getLocation(program, name), x, y, z, w);
	}

	public static void setf(OGL_ShaderProgram program, String name, float x, float y, float z) {
		program.bind();
		glUniform3f(getLocation(program, name), x, y, z);
	}

	public static void setf(OGL_ShaderProgram program, String name, float x, float y) {
		program.bind();
		glUniform2f(getLocation(program, name), x, y);
	}

	public static void setf(OGL_ShaderProgram program, String name, float x) {
		program.bind();
		glUniform1f(getLocation(program, name), x);
	}

	public static float getf(OGL_ShaderProgram program, String name) {
		program.bind();
		return glGetUniformf(program.getID(), getLocation(program, name));
	}

	public static void seti(OGL_ShaderProgram program, String name, int x, int y, int z, int w) {
		program.bind();
		glUniform4i(getLocation(program, name), x, y, z, w);
	}

	public static void seti(OGL_ShaderProgram program, String name, int x, int y, int z) {
		program.bind();
		glUniform3i(getLocation(program, name), x, y, z);
	}

	public static void seti(OGL_ShaderProgram program, String name, int x, int y) {
		program.bind();
		glUniform2i(getLocation(program, name), x, y);
	}

	public static void setiv(OGL_ShaderProgram program, String name, int[] buffer) {
		program.bind();
		glUniform1iv(getLocation(program, name), buffer);
	}

	public static void seti(OGL_ShaderProgram program, String name, int x) {
		program.bind();
		glUniform1i(getLocation(program, name), x);
	}

	public static float geti(OGL_ShaderProgram program, String name) {
		program.bind();
		return glGetUniformi(program.getID(), getLocation(program, name));
	}

	private static int getLocation(OGL_ShaderProgram program, String name) {
		return glGetUniformLocation(program.getID(), name);
	}
}
