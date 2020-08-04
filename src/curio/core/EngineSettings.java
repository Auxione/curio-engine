package core;

/**
 * Contains engine parameters to use when running application.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class EngineSettings {
	private EngineSettings() {
	}

	/**
	 * The OpenGL batch renderer.
	 * 
	 * @see OGL_QuadRenderer2D
	 */
	public static final int RENDERER_OPENGL = 0;
	/**
	 * GLFW window for OpenGL.
	 * 
	 * @see GLFW_Window
	 */
	public static final int WINDOW_GLFW = 0;
	/**
	 * OpenAL audio.
	 * 
	 * @see OAL_Listener
	 */
	public static final int AUDIO_OPENAL = 0;

	/**
	 * GLFW window input.
	 * 
	 * @see GLFW_Input
	 */
	public static final int INPUT_GLFW = 0;
	/**
	 * The parameter for {@link Renderer2D} object. Default is
	 * {@link RENDERER_OPENGL}.
	 */
	public static int renderer = RENDERER_OPENGL;
	/**
	 * The parameter for {@link Window} object. Default is {@link WINDOW_GLFW}.
	 */
	public static int window = WINDOW_GLFW;
	/**
	 * The parameter for {@link AudioListener} object. Default is
	 * {@link AUDIO_OPENAL}.
	 */
	public static int audio = AUDIO_OPENAL;
}
