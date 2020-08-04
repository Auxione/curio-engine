package graphics;

import graphics.renderer2d.Vertex2;

public class VertexFactory {

	public static Vertex2[] createQuad(int xs, int ys, int cell) {
		Vertex2[] quadVertices = new Vertex2[(xs + 1) * (ys + 1)];

		for (int i = 0, x = 0; x <= xs; x++) {
			for (int y = 0; y <= ys; y++) {
				quadVertices[i] = new Vertex2();
				quadVertices[i].position.set(x * cell, y * cell);
				i++;
			}
		}

		return quadVertices;
	}
}
