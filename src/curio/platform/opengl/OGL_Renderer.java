package platform.opengl;

public interface OGL_Renderer {
	/**
	 * Maximum quads per batch.
	 */
	public static final int MAXQUADS = 20_000;
	/**
	 * Maximum vertices per quads in batch.
	 */
	public static final int MAXVERTICES = MAXQUADS * 4;
	/**
	 * Maximum indices for vertices.
	 */
	public static final int MAXINDICES = MAXQUADS * 6;
	/**
	 * The number of textures active on screen simultaneously.
	 */
	public static final int TEXTURESAMPLERSIZE = 32;
}
