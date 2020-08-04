package core;

import common.buffers.ImageBuffer;

public class WindowSettings {

	/**
	 * OpenGL version.
	 */
	public static int OGLVersion = 45;

	/**
	 * Title of the application window.
	 */
	public String title = "curio-engine";

	/**
	 * Width of the application window.
	 */
	public int width = 1280;

	/**
	 * Height of the application window.
	 */
	public int height = 720;

	/**
	 * x component of the monitor position.The application top left corner is equals
	 * to this position on monitor.
	 */
	public int monitorPositionX = 50;

	/**
	 * y component of the monitor position.The application top left corner is equals
	 * to this position on monitor.
	 */
	public int monitorPositionY = 25;

	/**
	 * Wanted refresh rate from the application. Drivers can override this option.
	 */
	public int refreshRate = 144;

	/**
	 * If {@link WindowSettings#fullscreen} is true this method is the index of the
	 * monitor to display application.
	 */
	public int fullScreenMonitorIndex = 0;

	/**
	 * True if fullscreen is wanted.
	 */
	public boolean fullscreen = false;

	/**
	 * Enable/Disable vertical synchronization
	 */
	public boolean vsync = true;

	/**
	 * Enable/Disable window resizing.
	 */
	public boolean resizeable = false;

	public boolean noAPI = false;

	/**
	 * Application icon.
	 */
	public ImageBuffer icon = null; // ResourceManager.getInstance().load("src/curio/core/ico.png").asImageBuffer();

	/**
	 * Multisampling.
	 */
	public int samples = 0;
}
