package platform.opengl;

import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL45.*;

import common.math.Transform2;
import org.joml.Matrix4f;

import graphics.Color;
import graphics.Mesh;
import graphics.renderer2d.MeshRenderer2D;

public class OGL_MeshRenderer2D extends MeshRenderer2D implements OGL_Renderer {
	protected static OGL_ShaderProgram shaderProgram;

	private Matrix4f calcMatrix = new Matrix4f();

	public OGL_MeshRenderer2D() {
		GL.createCapabilities();
		if (OGL_MeshRenderer2D.shaderProgram == null) {
			OGL_MeshRenderer2D.shaderProgram = createShader();
		}
	}

	@Override
	public final void bind() {
		if (OGL_MeshRenderer2D.shaderProgram != null) {
			OGL_MeshRenderer2D.shaderProgram.bind();
		}
	}

	@Override
	public void onSubmitMesh(Mesh mesh) {
		OGL_VertexArray m = (OGL_VertexArray) mesh;
		m.uploadData();
		m.GPUMemLoad();
	}

	@Override
	public void onRemoveMesh(Mesh mesh) {
		OGL_VertexArray m = (OGL_VertexArray) mesh;
		m.terminate();
	}

	@Override
	public void update(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
		bind();
		OGL_ShaderUniforms.setMat4f(OGL_MeshRenderer2D.shaderProgram, "u_ViewMatrix", viewMatrix);
		OGL_ShaderUniforms.setMat4f(OGL_MeshRenderer2D.shaderProgram, "u_ProjectionMatrix", projectionMatrix);

		for (Mesh m : OGL_MeshRenderer2D.objectList.keySet()) {
			OGL_ShaderUniforms.setMat4f(OGL_MeshRenderer2D.shaderProgram, "u_ModelMatrix",
					Transform2.convert(OGL_MeshRenderer2D.objectList.get(m).getMatrix(), calcMatrix));
			m.renderIndexed();
		}
	}

	public final OGL_ShaderProgram createShader() {
		OGL_ShaderFactory fact = OGL_ShaderFactory.getInstance();
		OGL_ShaderProgram tempShader = new OGL_ShaderProgram();
		fact.start(GL_VERTEX_SHADER);
		fact.addLayoutVar(0, "vec4", "position");
		fact.addLayoutVar(1, "vec4", "color");
		fact.addLayoutVar(2, "vec2", "TextureCoordinate");
		fact.addLayoutVar(3, "float", "TextureSlot");

		fact.addPassVarOut("vec4", "v_Color");
		fact.addPassVarOut("vec2", "v_TextureCoordinate");
		fact.addPassVarOut("float", "v_TextureSlot");

		fact.addUniformVar("mat4", "u_ProjectionMatrix");
		fact.addUniformVar("mat4", "u_ViewMatrix");
		fact.addUniformVar("mat4", "u_ModelMatrix");

		fact.setMainFunction(//
				"v_TextureSlot = TextureSlot;" //
						+ "v_TextureCoordinate = TextureCoordinate;" //
						+ "v_Color = color;" //
						+ "gl_Position =   u_ProjectionMatrix  * u_ViewMatrix * u_ModelMatrix * position;");

		tempShader.addShader(fact.end());

		fact.start(GL_FRAGMENT_SHADER);
		fact.addPassVarIn("vec4", "v_Color");
		fact.addPassVarIn("vec2", "v_TextureCoordinate");
		fact.addPassVarIn("float", "v_TextureSlot");

		fact.addUniformVar("sampler2D", "u_TextureSampler[" + TEXTURESAMPLERSIZE + "]");

		fact.setMainFunction(//
				"int index = int(v_TextureSlot);"//
						+ "gl_FragColor = texture2D(u_TextureSampler[index], v_TextureCoordinate) * v_Color;");//

		tempShader.addShader(fact.end());

		tempShader.GPULoad();

		int[] textureSamplerIDArray = new int[TEXTURESAMPLERSIZE];
		for (int i = 0; i < textureSamplerIDArray.length; i++) {
			textureSamplerIDArray[i] = i;
		}
		OGL_ShaderUniforms.setiv(tempShader, "u_TextureSampler", textureSamplerIDArray);
		return tempShader;
	}

	@Override
	public final void setBackground(Color color) {
		glClearColor(color.x, color.y, color.z, color.w);
	}

}
