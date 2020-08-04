package platform.opengl;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL45.*;

import common.Console;
import common.utilities.NativeObject;

public class OGL_ShaderProgram implements OGL_Object, NativeObject {
	private int programID;
	private ArrayList<OGL_Shader> shaders = new ArrayList<OGL_Shader>();

	public OGL_ShaderProgram() {
	}

	public void GPULoad() {
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
		Console.fine(this, this + " Compiled " + type + " successfully.");
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

	public void bind() {
		glUseProgram(this.programID);
	}
}