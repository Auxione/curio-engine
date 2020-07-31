package platform.opengl;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL45.*;
import graphics.Color;
import graphics.Texture;
import graphics.TextureCoordinate;
import graphics.TextureFactory;
import graphics.renderer2d.Renderer2D;
import graphics.renderer2d.Vertex2;
import platform.opengl.OGL_Buffer.Method;
import platform.opengl.OGL_VertexArray.Mode;

public class OGL_QuadRenderer2D extends Renderer2D {
	protected int[] textureSamplerIDArray;

	private int quadIndexCount = 0;

	protected OGL_ShaderProgram shaderProgram;
	protected OGL_VertexArray vertexArrayObject;
	protected OGL_VertexBuffer vertexBufferObject;
	protected OGL_IndexBuffer indexBufferObject;

	protected int[] quadIndices = new int[] { 0, 1, 2, 2, 3, 0 };
	protected Vertex2[] quadVertices = new Vertex2[] { new Vertex2(), new Vertex2(), new Vertex2(), new Vertex2() };

	private Matrix4f orthographicViewingMatrix = new Matrix4f();

	private int debug_QuadCount = 0;
	private int debug_RenderCallCount = 0;

	public OGL_QuadRenderer2D(int width, int height) {
		init();
		setRenderSize(width, height);
	}

	private void init() {
		GL.createCapabilities();
		super.init(Color.white, TextureFactory.fillRectangle(2, 2), new TextureCoordinate());

		this.vertexArrayObject = new OGL_VertexArray(Mode.TRIANGLES);
		this.vertexBufferObject = new OGL_VertexBuffer(Method.STREAM_DRAW, MAXVERTICES * Vertex2.SIZE, Vertex2.BYTES,
				Vertex2.DATASIZES);

		this.indexBufferObject = new OGL_IndexBuffer(Method.STREAM_DRAW, MAXINDICES);

		this.vertexArrayObject.add(this.vertexBufferObject);
		this.vertexArrayObject.add(this.indexBufferObject);
		this.vertexArrayObject.GPULoadMethod(0);

		for (int i = 0; i < MAXINDICES; i += 6) {
			this.indexBufferObject.put(this.quadIndices);
		}
		this.indexBufferObject.uploadSubData(0);
		this.shaderProgram = createShader();

		this.shaderProgram.uniforms.setMat4f("u_OrthographicViewingMatrix", this.orthographicViewingMatrix);
		OGL_RenderUtils.Blending.set(true);
	}

	public void setRenderSize(float width, float height) {
		this.orthographicViewingMatrix.ortho2D(0, width, height, 0);
		setOrthoViewMatrix(this.orthographicViewingMatrix);
	}

	@Override
	public void fillQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color color,
			Texture texture, TextureCoordinate textureCoordinate) {

		if (this.quadIndexCount >= MAXINDICES) {
			flushAndReset();
		}

		this.quadVertices[0].position.set(x1, y1);
		this.quadVertices[1].position.set(x2, y2);
		this.quadVertices[2].position.set(x3, y3);
		this.quadVertices[3].position.set(x4, y4);

		Vertex2.setColor(this.quadVertices, color);
		Vertex2.setTextureSlot(this.quadVertices, texture);
		Vertex2.setTexturePositionRect(this.quadVertices, textureCoordinate);

		this.vertexBufferObject.put(this.quadVertices);
		this.quadIndexCount += 6;
		this.debug_QuadCount++;
	}

	@Override
	public void fillTris(float x1, float y1, float x2, float y2, float x3, float y3, Color color, Texture texture,
			TextureCoordinate textureCoordinate) {
		fillQuad(x1, y1, x2, y2, x3, y3, x3, y3, color, texture, textureCoordinate);
	}

	@Override
	public final void bind() {
		this.shaderProgram.bind();
	}

	@Override
	public final void beginScene() {
		this.quadIndexCount = 0;
		this.debug_RenderCallCount = 0;
		this.debug_QuadCount = 0;
	}

	public final void flush() {
		if (this.quadIndexCount == 0) {
			return;
		}
		this.vertexArrayObject.drawIndexed(this.quadIndexCount);
		this.debug_RenderCallCount++;
	}

	public final void flushAndReset() {
		endScene();
		this.quadIndexCount = 0;
	}

	@Override
	public final void endScene() {
		this.vertexArrayObject.bind();
		this.vertexBufferObject.uploadSubData(0);
		flush();
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

		fact.addUniformVar("mat4", "u_OrthographicViewingMatrix");

		fact.setMainFunction(//
				"v_TextureSlot = TextureSlot;" //
						+ "v_TextureCoordinate = TextureCoordinate;" //
						+ "v_Color = color;" //
						+ "gl_Position =  u_OrthographicViewingMatrix  * position;");
		tempShader.addShader(fact.end());

		fact.start(GL_FRAGMENT_SHADER);
		fact.addPassVarIn("vec4", "v_Color");
		fact.addPassVarIn("vec2", "v_TextureCoordinate");
		fact.addPassVarIn("float", "v_TextureSlot");

		fact.addUniformVar("sampler2D", "u_TextureSampler[" + TEXTURESAMPLERSIZE + "]");

		fact.setMainFunction(//
				"int index = int(v_TextureSlot);"//
						+ "gl_FragColor = texture2D(u_TextureSampler[index], v_TextureCoordinate) * v_Color;\n");//

		tempShader.addShader(fact.end());

		tempShader.GPULoadMethod(0);

		this.textureSamplerIDArray = new int[TEXTURESAMPLERSIZE];
		for (int i = 0; i < this.textureSamplerIDArray.length; i++) {
			this.textureSamplerIDArray[i] = i;
		}
		tempShader.uniforms.setiv("u_TextureSampler", this.textureSamplerIDArray);

		return tempShader;
	}

	public final void setOrthoViewMatrix(Matrix4f orthographicViewingMatrix) {
		this.shaderProgram.uniforms.setMat4f("u_OrthographicViewingMatrix", orthographicViewingMatrix);
	}

	@Override
	public final void setBackground(Color color) {
		glClearColor(color.x, color.y, color.z, color.w);
	}

	@Override
	public final String getVendorName() {
		return glGetString(GL_VENDOR);
	}

	@Override
	public final String getHardwareName() {
		return glGetString(GL_RENDERER);
	}

	@Override
	public final int getMaxTextureSlots() {
		return glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS);
	}

	@Override
	public final int getTextureSlotSize() {
		return glGetInteger(GL_MAX_TEXTURE_SIZE);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();

		sb.append(debug_QuadCount).append(" Quads rendered with ").append(debug_RenderCallCount)
				.append(" render calls.");
		return sb.toString();
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
	 
	}

}
