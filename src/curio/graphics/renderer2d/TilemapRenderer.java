package graphics.renderer2d;

import common.buffers.IndexBuffer;
import common.buffers.VertexBuffer;
import common.tilemapsys.Grid2;
import common.tilemapsys.Tilemap;
import graphics.Mesh;
import graphics.Mesh.DrawMode;
import graphics.Mesh.DrawType;
import graphics.TextureAtlas;
import graphics.TextureCoordinate;

public class TilemapRenderer {
	private Mesh mesh;

	private TextureAtlas textureAtlas;
	private Tilemap tilemap;
	private Grid2 grid;

	private Vertex2[] vertices;

	public TilemapRenderer(Tilemap tilemap, Grid2 grid, TextureAtlas textureAtlas) {
		this.tilemap = tilemap;
		this.grid = grid;
		this.textureAtlas = textureAtlas;
		createMesh(tilemap, grid);
	}

	private void createMesh(Tilemap tilemap, Grid2 grid) {
		this.vertices = new Vertex2[tilemap.getWidth() * tilemap.getHeight() * 4];
		int vertexIndex = 0;
		for (int y = 0; y < tilemap.getWidth(); y++) {
			for (int x = 0; x < tilemap.getHeight(); x++) {
				vertices[vertexIndex] = new Vertex2();
				vertices[vertexIndex + 1] = new Vertex2();
				vertices[vertexIndex + 2] = new Vertex2();
				vertices[vertexIndex + 3] = new Vertex2();

				vertexIndex += 4;
			}
		}

		updateTilemap(tilemap);

		IndexBuffer indexBuffer = new IndexBuffer(tilemap.getWidth() * tilemap.getHeight() * 6);
		int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };
		for (int i = 0; i < tilemap.getWidth() * tilemap.getHeight(); i++) {
			indexBuffer.putIndexed(indices);
		}

		VertexBuffer vertexBuffer = new VertexBuffer(vertices);
		this.mesh = Mesh.createInstance(DrawMode.TRIANGLES, DrawType.DYNAMIC_DRAW, vertexBuffer, indexBuffer);
	}

	public void updateTilemap(Tilemap tilemap) {
		int vertexIndex = 0;
		for (int y = 0; y < tilemap.getWidth(); y++) {
			for (int x = 0; x < tilemap.getHeight(); x++) {
				vertices[vertexIndex].position.set(x * grid.x, y * grid.y);
				vertices[vertexIndex + 1].position.set((x + 1) * grid.x, y * grid.y);
				vertices[vertexIndex + 2].position.set((x + 1) * grid.x, (y + 1) * grid.y);
				vertices[vertexIndex + 3].position.set(x * grid.x, (y + 1) * grid.y);

				vertices[vertexIndex].position.add(x * grid.offsetX, y * grid.offsetY);
				vertices[vertexIndex + 1].position.add(x * grid.offsetX, y * grid.offsetY);
				vertices[vertexIndex + 2].position.add(x * grid.offsetX, y * grid.offsetY);
				vertices[vertexIndex + 3].position.add(x * grid.offsetX, y * grid.offsetY);

				vertices[vertexIndex].textureSlot = this.textureAtlas.getTexture().getSlot();
				vertices[vertexIndex + 1].textureSlot = this.textureAtlas.getTexture().getSlot();
				vertices[vertexIndex + 2].textureSlot = this.textureAtlas.getTexture().getSlot();
				vertices[vertexIndex + 3].textureSlot = this.textureAtlas.getTexture().getSlot();

				Vertex2.setTexturePositionRect(vertices[vertexIndex], //
						vertices[vertexIndex + 1], //
						vertices[vertexIndex + 2], //
						vertices[vertexIndex + 3], //
						this.textureAtlas.get(tilemap.getCellID(x, y)));//

				vertexIndex += 4;
			}
		}
	}

	public void updateCell(int x, int y) {
		int vertexIndex = (x + y * this.tilemap.getHeight()) * 4;
		VertexBuffer ptr = getMesh().vertexBuffer;
		Vertex2 v1 = new Vertex2();
		Vertex2 v2 = new Vertex2();
		Vertex2 v3 = new Vertex2();
		Vertex2 v4 = new Vertex2();

		Vertex2.fromBuffer(ptr, vertexIndex, v1);
		Vertex2.fromBuffer(ptr, vertexIndex + 1, v2);
		Vertex2.fromBuffer(ptr, vertexIndex + 2, v3);
		Vertex2.fromBuffer(ptr, vertexIndex + 3, v4);

		TextureCoordinate deftexcoord = this.textureAtlas.get(this.tilemap.getCellID(x, y));
		v1.textureUV.x = deftexcoord.x;
		v1.textureUV.y = deftexcoord.y;
		v2.textureUV.x = deftexcoord.x + deftexcoord.width;
		v2.textureUV.y = deftexcoord.y;
		v3.textureUV.x = deftexcoord.x + deftexcoord.width;
		v3.textureUV.y = deftexcoord.y + deftexcoord.height;
		v4.textureUV.x = deftexcoord.x;
		v4.textureUV.y = deftexcoord.y + deftexcoord.height;

		ptr.clear();

		ptr.put(v1);
		ptr.put(v2);
		ptr.put(v3);
		ptr.put(v4);

		getMesh().uploadSubData(vertexIndex, -1);
	}

	public void updateCell(int x, int y, int id) {
		int vertexIndex = (x + y * this.tilemap.getHeight()) * 4;
		VertexBuffer ptr = getMesh().vertexBuffer;
		Vertex2 v1 = new Vertex2();
		Vertex2 v2 = new Vertex2();
		Vertex2 v3 = new Vertex2();
		Vertex2 v4 = new Vertex2();

		Vertex2.fromBuffer(ptr, vertexIndex, v1);
		Vertex2.fromBuffer(ptr, vertexIndex + 1, v2);
		Vertex2.fromBuffer(ptr, vertexIndex + 2, v3);
		Vertex2.fromBuffer(ptr, vertexIndex + 3, v4);

		TextureCoordinate deftexcoord = this.textureAtlas.get(id);
		v1.textureUV.x = deftexcoord.x;
		v1.textureUV.y = deftexcoord.y;
		v2.textureUV.x = deftexcoord.x + deftexcoord.width;
		v2.textureUV.y = deftexcoord.y;
		v3.textureUV.x = deftexcoord.x + deftexcoord.width;
		v3.textureUV.y = deftexcoord.y + deftexcoord.height;
		v4.textureUV.x = deftexcoord.x;
		v4.textureUV.y = deftexcoord.y + deftexcoord.height;

		ptr.clear();

		ptr.put(v1);
		ptr.put(v2);
		ptr.put(v3);
		ptr.put(v4);

		getMesh().uploadSubData(vertexIndex, -1);
	}

	public Mesh getMesh() {
		return mesh;
	}
}
