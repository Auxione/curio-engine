package graphics.renderer3d;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import graphics.Color;
import graphics.Texture;

/**
 * Vertex3 class for curio-engine. Contains position, color, textureCoordinate
 * and textureID.
 * 
 * @see Vector3f
 * @see Color
 * @author Mehmet Cem Zarifoglu
 *
 */

public class Vertex3 {
	/**
	 * Total size in floats.
	 */
	public static final int SIZE = 10;
	/**
	 * Total size in bytes.
	 */
	public static final int BYTES = SIZE * Float.BYTES;

	public static int[] DATASIZES = new int[] { 3, 4, 2, 1 };
	/**
	 * Position of the vertex.
	 */
	public Vector3f position = new Vector3f();
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
	public static Vertex3 reset(Vertex3 vertex) {
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
	public void set(Vertex3 vertex) {
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
	public static Vertex3[] setColor(Vertex3[] vertices, Color color) {
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
	public static Vertex3[] setTextureSlot(Vertex3[] vertices, int textureSlot) {
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
	public static Vertex3[] setTextureSlot(Vertex3[] vertices, Texture texture) {
		return setTextureSlot(vertices, texture.getSlot());
	}

	@Override
	public String toString() {
		return "pos: " + this.position.x + "-" + this.position.y + "-" + this.position.z + " |texPos: "
				+ this.textureUV.x + "-" + this.textureUV.y + " |Color: " + this.color.x + "-" + this.color.y + "-"
				+ this.color.z + "-" + this.color.w + " |TexID: " + textureSlot;
	}

	public static float[] toArray2D(Vertex3 vertex) {
		vertexData[0] = vertex.position.x;
		vertexData[1] = vertex.position.y;
		vertexData[2] = vertex.position.z;

		vertexData[3] = vertex.color.x;
		vertexData[4] = vertex.color.y;
		vertexData[5] = vertex.color.z;
		vertexData[6] = vertex.color.w;

		vertexData[7] = vertex.textureUV.x;
		vertexData[8] = vertex.textureUV.y;

		vertexData[8] = vertex.textureSlot;
		return vertexData;
	}
}
