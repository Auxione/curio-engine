package tests;

import common.buffers.IndexBuffer;
import common.buffers.VertexBuffer;
import common.geom.triangulators.QuadTriangulator;
import common.math.MathUtils;
import common.math.Transform2;
import core.BasicGame;
import core.EngineSettings;
import graphics.Blending;
import graphics.Mesh;
import graphics.TextureFactory;
import graphics.VertexFactory;
import graphics.Mesh.DrawMode;
import graphics.Mesh.DrawType;
import graphics.Texture;
import graphics.renderer2d.Camera2;
import graphics.renderer2d.MeshRenderer2D;
import graphics.renderer2d.Vertex2;

public class AppSandbox extends BasicGame {
	public static void main(String[] args) {
		AppSandbox appSandbox = new AppSandbox();
		appSandbox.debugManagerSettings.debugPrint = true;
		appSandbox.debugManagerSettings.debugDraw = true;
		appSandbox.debugManagerSettings.log = false;

		EngineSettings.renderer = 0;

		appSandbox.windowSettings.title = "Sandbox";
		appSandbox.windowSettings.width = 1280;
		appSandbox.windowSettings.height = 720;
		appSandbox.windowSettings.monitorPositionX = 10;
		appSandbox.windowSettings.monitorPositionY = 50;
		appSandbox.windowSettings.samples = 0;
		appSandbox.windowSettings.refreshRate = 60;
		appSandbox.windowSettings.vsync = false;
		appSandbox.windowSettings.fullscreen = false;
		appSandbox.windowSettings.resizeable = false;
		appSandbox.windowSettings.noAPI = false;

		appSandbox.run();
	}

	MeshRenderer2D meshRenderer2D;
	Transform2 transform;

	public Mesh createMesh(int xs, int ys, int cell) {
		Texture tex = TextureFactory.fillRectangle(2, 2);
		Vertex2[] quadVertices = VertexFactory.createQuad(xs, ys, cell);

		for (int i = 0, x = 0; x <= xs; x++) {
			for (int y = 0; y <= ys; y++) {
				quadVertices[i].textureSlot = tex.getSlot();
				quadVertices[i].textureUV.set((float) x / xs, (float) y / ys);
				quadVertices[i].color.set(MathUtils.random(0f, 1f), MathUtils.random(0.5f, 1f),
						MathUtils.random(0f, 1f), 1f);
				i++;
			}
		}

		VertexBuffer vertexBuffer = new VertexBuffer(quadVertices);

		QuadTriangulator q = new QuadTriangulator();
		q.calculate(xs, ys);

		IndexBuffer indexBuffer = new IndexBuffer(q.getIndices());

		return Mesh.createInstance(DrawMode.TRIANGLES, DrawType.STATIC_DRAW, vertexBuffer, indexBuffer);
	}

	@Override
	public void setup() {
		registerFrameCounter();
		Camera2 camera = new Camera2(1280, 720);
		camera.position.set(-32, -32);
		// camera.rotation.setDEG(45);
		camera.update();

		meshRenderer2D = MeshRenderer2D.createInstance(camera);

		transform = new Transform2();
		meshRenderer2D.submit(createMesh(4, 4, 32), transform);

		Blending.getInstance().set(true);
	}

	@Override
	public void update() {
		meshRenderer2D.update();
	}

	@Override
	public void fixedUpdate(float deltaTime) {
	}

}
