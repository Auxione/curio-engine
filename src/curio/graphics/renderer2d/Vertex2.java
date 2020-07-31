package graphics.renderer2d;

import org.joml.Vector2f;
import org.joml.Vector4f;

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
	public static int[] DATASIZES = new int[] { 2, 4, 2, 1 };
	/**
	 * Position of the vertex.
	 */
	public Vector2f position = new Vector2f();
	/**
	 * Color of the vertex.
	 */
	public Vector4f color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
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

	/**
	 * Set TextureSlot of given vertices.
	 * 
	 * @param vertices : The vertices to modify TextureSlot.
	 * @param texture  : The texture to get ID.
	 * @return the modified vertices.
	 */
	public static Vertex2[] setTexturePositionRect(Vertex2[] vertices, float x, float y, float width, float height) {
		if (vertices.length == 4) {
			vertices[0].textureUV.set(x, y);
			vertices[1].textureUV.set(x + width, y);
			vertices[2].textureUV.set(x + width, y + height);
			vertices[3].textureUV.set(x, y + height);
		}
		return vertices;
	}

	/**
	 * Set TexturePosition of given vertices to rectangular area.
	 * 
	 * @param vertices          : The vertices to modify TexturePosition.
	 * @param textureCoordinate : The textureCoordinate to get area.
	 * @return the modified vertices.
	 */
	public static Vertex2[] setTexturePositionRect(Vertex2[] vertices, TextureCoordinate textureCoordinate) {
		return setTexturePositionRect(vertices, textureCoordinate.x,
				textureCoordinate.y, textureCoordinate.width, textureCoordinate.height);
	}

	@Override
	public String toString() {
		return "pos: " + this.position.x + "-" + this.position.y + " |texPos: " + this.textureUV.x + "-"
				+ this.textureUV.y + " |Color: " + this.color.x + "-" + this.color.y + "-" + this.color.z + "-"
				+ this.color.w + " |TexID: " + textureSlot;
	}

	public static float[] toArray(Vertex2 vertex) {
		vertexData[0] = vertex.position.x;
		vertexData[1] = vertex.position.y;

		vertexData[2] = vertex.color.x;
		vertexData[3] = vertex.color.y;
		vertexData[4] = vertex.color.z;
		vertexData[5] = vertex.color.w;

		vertexData[6] = vertex.textureUV.x;
		vertexData[7] = vertex.textureUV.y;

		vertexData[8] = vertex.textureSlot;
		return vertexData;
	}
}
