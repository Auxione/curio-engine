package platform.opengl;

import java.io.File;
import java.io.IOException;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import common.utilities.Resource;

import static org.lwjgl.opengl.GL45.*;

public class OGL_Shader implements Resource<OGL_Shader>, NativeObject {
	private String string;
	private int type;
	private int programID;
	private int shaderID;
	private boolean loaded;

	public static final int VERTEXSHADER = GL_VERTEX_SHADER;
	public static final int FRAGMENTSHADER = GL_FRAGMENT_SHADER;
	public static final int COMPUTESHADER = GL_COMPUTE_SHADER;
	public static final int GEOMETRYSHADER = GL_GEOMETRY_SHADER;
	
	public OGL_Shader(int type) {
		this.type = type;
		NativeObjectManager.register(this);
	}

	@Override
	public OGL_Shader loadFile(File file) throws IOException {
		return null;
	}

	@Override
	public final String toString() {
		return "ProgramID: " + getProgramID() + " ShaderID: " + getShaderID() + " isLoaded?: " + this.loaded;
	}

	public final String getCode() {
		return string;
	}

	public final int getType() {
		return type;
	}

	public final int getProgramID() {
		this.loaded = true;
		return programID;
	}

	public final int getShaderID() {
		return shaderID;
	}

	public final void setProgramID(int programID) {
		this.programID = programID;
		this.loaded = true;
	}

	public final void setCode(String string) {
		if (this.loaded == true) {
			return;
		}
		this.string = string;
	}

	public final void setShaderID(int shaderID) {
		if (this.loaded == true) {
			return;
		}
		this.shaderID = shaderID;
	}

	@Override
	public final void terminate() {
		glDeleteShader(this.shaderID);
	}
}
