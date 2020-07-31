package platform.opengl;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL45.*;
import org.lwjgl.system.MemoryUtil;

import common.Console;
import common.utilities.NativeObject;

//TODO: Code cleaning.
//TODO: more uniforms for common scenarios.(color,matrix4f etc..)
public class OGL_ShaderProgram implements OGL_Object, NativeObject {
	private int programID;
	private ArrayList<OGL_Shader> shaders = new ArrayList<OGL_Shader>();

	public Uniforms uniforms = new Uniforms();

	public OGL_ShaderProgram() {
	}

	@Override
	public void GPULoadMethod(int parameter) {
		this.programID = glCreateProgram();
		for (OGL_Shader oGL_Shader : this.shaders) {
			int shaderID = compile(oGL_Shader.getCode(), oGL_Shader.getType());
			oGL_Shader.setShaderID(shaderID);
			oGL_Shader.setProgramID(this.getID());
			glAttachShader(this.programID, oGL_Shader.getShaderID());
		}

		glLinkProgram(this.programID);
		if (glGetProgrami(this.programID, GL_LINK_STATUS) == GL_FALSE) {
			Console.severe(this, "Error on program Linking: \n" + glGetProgramInfoLog(this.programID));
			return;
		}
		glValidateProgram(this.programID);
		if (glGetProgrami(this.programID, GL_VALIDATE_STATUS) == GL_FALSE) {
			Console.severe(this, "Error on program Validation: \n" + glGetProgramInfoLog(this.programID));
			return;
		}
		Console.fine(this, "Initialized with ID: " + this.programID);
	}

	private int compile(String stringToLoad, int type) {
		int tempID = glCreateShader(type);
		glShaderSource(tempID, stringToLoad);
		glCompileShader(tempID);
		if (glGetShaderi(tempID, GL_COMPILE_STATUS) == GL_FALSE) {
			Console.severe(this, "Could not compile " + type + " shader: \n" + glGetShaderInfoLog(tempID, 500));
		}
		Console.fine(this, this + " Compiled " + type + " succesifully.");
		return tempID;
	}

	@Override
	public void terminate() {
		glDeleteProgram(this.programID);
	}

	public void addShader(OGL_Shader oGL_Shader) {
		this.shaders.add(oGL_Shader);
	}

	@Override
	public int getID() {
		return programID;
	}

	@Override
	public void bind() {
		glUseProgram(this.programID);
	}

	public class Uniforms {
		private FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);

		public void setBool(String name, boolean value) {
			bind();
			if (value == true) {
				glUniform1i(getLocation(name), GL_TRUE);
			} else {
				glUniform1i(getLocation(name), GL_FALSE);
			}
		}

		public void setMat4f(String name, Matrix4f matrix) {
			bind();
			glUniformMatrix4fv(getLocation(name), true, matrix.getTransposed(matrixBuffer));
		}

		public void setMat3f(String name, Matrix3f matrix) {
			bind();
			glUniformMatrix3fv(getLocation(name), true, matrix.getTransposed(matrixBuffer));
		}

		public void setMat2f(String name, boolean transpose, Matrix2f matrix) {
			bind();
			glUniformMatrix2fv(getLocation(name), true, matrix.getTransposed(matrixBuffer));
		}

		public void setf(String name, float x, float y, float z, float w) {
			bind();
			glUniform4f(getLocation(name), x, y, z, w);
		}

		public void setf(String name, float x, float y, float z) {
			bind();
			glUniform3f(getLocation(name), x, y, z);
		}

		public void setf(String name, float x, float y) {
			bind();
			glUniform2f(getLocation(name), x, y);
		}

		public void setf(String name, float x) {
			bind();
			glUniform1f(getLocation(name), x);
		}

		public float getf(String name) {
			bind();
			return glGetUniformf(getID(), getLocation(name));
		}

		public void seti(String name, int x, int y, int z, int w) {
			bind();
			glUniform4i(getLocation(name), x, y, z, w);
		}

		public void seti(String name, int x, int y, int z) {
			bind();
			glUniform3i(getLocation(name), x, y, z);
		}

		public void seti(String name, int x, int y) {
			bind();
			glUniform2i(getLocation(name), x, y);
		}

		public void setiv(String name, int[] buffer) {
			bind();
			glUniform1iv(getLocation(name), buffer);
		}

		public void seti(String name, int x) {
			bind();
			glUniform1i(getLocation(name), x);
		}

		public int geti(String name) {
			bind();
			return glGetUniformi(getID(), getLocation(name));
		}

		private int getLocation(String name) {
			return glGetUniformLocation(getID(), name);
		}
	}
}