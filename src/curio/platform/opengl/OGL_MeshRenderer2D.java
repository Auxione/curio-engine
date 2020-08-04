package platform.opengl;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL45.*;

import graphics.Color;
import graphics.Mesh;
import graphics.renderer2d.Camera2;
import graphics.renderer2d.MeshRenderer2D;

public class OGL_MeshRenderer2D extends MeshRenderer2D implements OGL_Renderer {
	protected OGL_ShaderProgram shaderProgram;

	public OGL_MeshRenderer2D(Camera2 camera) {
		super(camera);
		GL.createCapabilities();
		this.shaderProgram = createShader();
	}

	@Override
	public final void bind() {
		this.shaderProgram.bind();
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
	public void update() {

		bind();
		OGL_ShaderUniforms.setMat4f(this.shaderProgram, "u_ViewMatrix", this.getCamera().getViewMatrix());
		OGL_ShaderUniforms.setMat4f(this.shaderProgram, "u_ProjectionMatrix", this.getCamera().getProjectionMatrix());

		for (Mesh m : MeshRenderer2D.objectList.keySet()) {
			OGL_ShaderUniforms.setMat3x2f(this.shaderProgram, "u_ModelMatrix",
					MeshRenderer2D.objectList.get(m).getMatrix());
			m.renderIndexed(m.indexBuffer.capacity());
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
		fact.setMainFunction(//
				"v_TextureSlot = TextureSlot;" //
						+ "v_TextureCoordinate = TextureCoordinate;" //
						+ "v_Color = color;" //
						+ "gl_Position =  u_ProjectionMatrix  * u_ViewMatrix * position;");

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
