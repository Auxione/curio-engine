package graphics;

import common.geom.Shape2;
import graphics.renderer2d.Image;
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

	public static Vertex2[] fromShape(Shape2 shape) {
		Vertex2[] quadVertices = new Vertex2[shape.getDefaultPoints().length];
		for (int i = 0; i < quadVertices.length; i++) {
			quadVertices[i] = new Vertex2();
			quadVertices[i].position.set(shape.getDefaultPoints()[i]);
		}
		return quadVertices;
	}

	public static Vertex2[] fromImage(Image image) {
		Vertex2[] quadVertices = new Vertex2[image.getDefaultPoints().length];
		for (int i = 0; i < quadVertices.length; i++) {
			quadVertices[i] = new Vertex2();
			quadVertices[i].position.set(image.getDefaultPoints()[i]);
			quadVertices[i].textureSlot = image.texture.getSlot();
		}
		Vertex2.setTexturePositionRect(quadVertices[0], quadVertices[1], quadVertices[2], quadVertices[3], 0, 0, 1, 1);
		return quadVertices;
	}
}
