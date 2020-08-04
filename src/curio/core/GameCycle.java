package core;

import graphics.renderer2d.Renderer2D;

/**
 * The Game cycle designed to use in curio-engine. The application cycle is:
 * Setup-Game Loop-Terminate. Game Loop in application cycle runs in this order
 * {@link earlyUpdate}, {@link fixedUpdate}, {@link update}, {@link lateUpdate},
 * {@link render2D}.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface GameCycle {
	/**
	 * Setup phase for cycle.
	 */
	public void setup();

	/**
	 * This update method called first when game loop starts.
	 */
	public void earlyUpdate();

	/**
	 * Fixed 60 updates per second in game loop. If the application cannot run 60
	 * fixed updates per second {@link Console} will print an error message.
	 * 
	 * @param deltaTime : delta time of the last frame in milliseconds.
	 */
	public void fixedUpdate(float deltaTime);

	/**
	 * Called every frames after {@link fixedUpdate} but before the
	 * {@link lateUpdate}.
	 */
	public void update();

	/**
	 * Called every frames after {@link update}.
	 */
	public void lateUpdate();

	/**
	 * 
	 * Called at the end every frames before swapping buffers.
	 * 
	 * @param renderer2D : The renderer2D of the application.
	 */
	public void onGUIRender(Renderer2D renderer);

	/**
	 * After successful break from the game loop this method called. The application
	 * stops after calling this method.
	 */
	public void terminate();

}
