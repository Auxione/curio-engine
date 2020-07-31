package platform.opengl;

import common.Console;
import core.WindowSettings;

public class OGL_ShaderFactory {
	private StringBuilder shaderString = new StringBuilder();
	private String OGLCoreString = "#version " + WindowSettings.OGLVersion + "0 core";

	private static OGL_ShaderFactory instance;

	private OGL_Shader outShader;
	private boolean ended = false;
	private String shaderMainFunction;

	private OGL_ShaderFactory() {

	}

	public final static OGL_ShaderFactory getInstance() {
		if (OGL_ShaderFactory.instance == null) {
			OGL_ShaderFactory.instance = new OGL_ShaderFactory();
		}
		return instance;
	}

	public final void start(int type) {
		this.outShader = new OGL_Shader(type);
		this.shaderString.delete(0, this.shaderString.capacity());
		
		this.shaderString.append("\n");
		this.shaderString.append(OGLCoreString);
		this.shaderString.append("\n");
		this.shaderString.append("\n");
		this.ended = false;
		Console.fine(this, "Shader building started.");
	}

	public final void addLayoutVar(int index, String var, String name) {
		if (this.ended == false) {
			this.shaderString.append("layout (location = ").append(index).append(") in ").append(var).append(" ")
					.append(name).append(";\n");
		}
	}

	public final void addPassVarOut(String var, String name) {
		if (this.ended == false) {
			this.shaderString.append("out ").append(var).append(" ").append(name).append(";\n");
		}
	}

	public final void addPassVarIn(String var, String name) {
		if (this.ended == false) {
			this.shaderString.append("in ").append(var).append(" ").append(name).append(";\n");
		}
	}

	public final void addUniformVar(String var, String name) {
		if (this.ended == false) {
			this.shaderString.append("uniform ").append(var).append(" ").append(name).append(";\n");
		}
	}

	public final void addVar(String var, String name) {
		if (this.ended == false) {
			this.shaderString.append(var).append(" ").append(name).append(";\n");
		}
	}

	public final void setMainFunction(String vertexMainFunction) {
		if (this.ended == false) {
			this.shaderMainFunction = vertexMainFunction;
		}
	}

	public final OGL_Shader end() {
		this.shaderString.append("\nvoid main() {\n").append(this.shaderMainFunction).append("\n}\n\n");
		this.ended = true;
		this.outShader.setCode(shaderString.toString());

		Console.fine(this, "Shader created.");
		return this.outShader;
	}
}
