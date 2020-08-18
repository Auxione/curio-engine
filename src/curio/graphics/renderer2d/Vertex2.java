package graphics.renderer2d;

import org.joml.Vector2f;

import common.buffers.VertexBuffer;
import graphics.Color;
import graphics.Texture;
import graphics.TextureCoordinate;

/**
 * Vertex2 class for curio-engine. Contains position, color, textureCoordinate
 * and textureID.
 * 
 * @see Vector2f
 * @see Color
 * @author Mehmet Cem Zarifoglu
 *
 */
public class Vertex2 {
	/**
	 * Total size in floats.
	 */
	public static final int SIZE = 9;
	/**
	 * Total size in bytes.
	 */
	public static final int BYTES = SIZE * Float.BYTES;

	/**
	 * Data indices for buffer.
	 */
	public static int[] DATAFORMAT = new int[] { 2, 4, 2, 1 };
	/**
	 * Position of the vertex.
	 */
	public Vector2f position = new Vector2f();
	/**
	 * Color of the vertex.
	 */
	public Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	/**
	 * texturePosition of the vertex.
	 */
	public Vector2f textureUV = new Vector2f();
	/**
	 * TextureID of the vertex.
	 */
	public float textureSlot = 0;

	private static float[] vertexData = new float[SIZE];

	/**
	 * Reset vertex data to 0.
	 * 
	 * @param vertex : The vertex to reset
	 * @return the modified vertex.
	 */
	public static Vertex2 reset(Vertex2 vertex) {
		vertex.position.zero();
		vertex.color.zero();
		vertex.textureUV.zero();
		vertex.textureSlot = 0;
		return vertex;
	}

	/**
	 * Copy vertex data from source.
	 * 
	 * @param vertex : source
	 */
	public void set(Vertex2 vertex) {
		this.position.set(vertex.position);
		this.color.set(vertex.color);
		this.textureUV.set(vertex.textureUV);
		this.textureSlot = vertex.textureSlot;
	}

	/**
	 * Set color of given vertices.
	 * 
	 * @param vertices : The vertices to modify color.
	 * @param color    : The color.
	 * @return the modified vertices.
	 */
	public static Vertex2[] setColor(Vertex2[] vertices, Color color) {
		for (int i = 0; i < vertices.length; i++) {
			vertices[i].color.set(color);
		}
		return vertices;
	}

	/**
	 * Set TextureSlot of given vertices.
	 * 
	 * @param vertices  : The vertices to modify TextureSlot.
	 * @param TextureID : The ID of the texture.
	 * @return the modified vertices.
	 */
	public static Vertex2[] setTextureSlot(Vertex2[] vertices, int textureSlot) {
		for (int i = 0; i < vertices.length; i++) {
			vertices[i].textureSlot = textureSlot;
		}
		return vertices;
	}

	/**
	 * Set TextureSlot of given vertices.
	 * 
	 * @param vertices : The vertices to modify TextureSlot.
	 * @param texture  : The texture to get ID.
	 * @return the modified vertices.
	 */
	public static Vertex2[] setTextureSlot(Vertex2[] vertices, Texture texture) {
		return setTextureSlot(vertices, texture.getSlot());
	}

	public static void setTexturePositionRect(Vertex2 v1, Vertex2 v2, Vertex2 v3, Vertex2 v4, float x, float y,
			float width, float height) {
		v1.textureUV.set(x, y + height);
		v2.textureUV.set(x + width, y + height);
		v3.textureUV.set(x + width, y);
		v4.textureUV.set(x, y);
	}

	public static void setTexturePositionRect(Vertex2 v1, Vertex2 v2, Vertex2 v3, Vertex2 v4,
			TextureCoordinate textureCoordinate) {
		setTexturePositionRect(v1, v2, v3, v4, textureCoordinate.x, textureCoordinate.y, textureCoordinate.width,
				textureCoordinate.height);
	}

	@Override
	public String toString() {
		return "pos: " + this.position.x + "-" + this.position.y + " |texPos: " + this.textureUV.x + "-"
				+ this.textureUV.y + " |Color: " + this.color.getRed() + "-" + this.color.getGreen() + "-"
				+ this.color.getBlue() + "-" + this.color.getAlpha() + " |TexID: " + textureSlot;
	}

	public static float[] toArray(Vertex2 vertex) {
		vertexData[0] = vertex.position.x;
		vertexData[1] = vertex.position.y;

		vertexData[2] = vertex.color.r;
		vertexData[3] = vertex.color.g;
		vertexData[4] = vertex.color.b;
		vertexData[5] = vertex.color.a;

		vertexData[6] = vertex.textureUV.x;
		vertexData[7] = vertex.textureUV.y;

		vertexData[8] = vertex.textureSlot;
		return vertexData;
	}

	public static void fromBuffer(VertexBuffer buffer, int index, Vertex2 target) {
		int indexInBuffer = index * Vertex2.SIZE;

		target.position.set(//
				buffer.get(indexInBuffer), //
				buffer.get(indexInBuffer + 1));//
		target.color.set(//
				buffer.get(indexInBuffer + 2), // red
				buffer.get(indexInBuffer + 3), // gr
				buffer.get(indexInBuffer + 4), // bl
				buffer.get(indexInBuffer + 5));// al

		target.textureUV.set(//
				buffer.get(indexInBuffer + 6), //
				buffer.get(indexInBuffer + 7));//

		target.textureSlot = //
				buffer.get(indexInBuffer + 8);
	}
}
