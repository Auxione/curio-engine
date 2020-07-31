package vendor.glfw;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowMaximizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import common.Console;
import common.utilities.ImageBuffer;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import core.Window;
import core.WindowSettings;
import core.events.WindowListener;

public class GLFW_Window implements Window, NativeObject {
	private boolean focused;
	private boolean iconified;
	private boolean maximized;

	private GLFWWindowSizeCallback windowSizeCallback;
	private GLFWWindowFocusCallback windowFocusCallback;
	private GLFWErrorCallback errorCallback;
	private GLFWWindowMaximizeCallback maximizeCallback;
	private GLFWWindowIconifyCallback iconifyCallback;
	private GLFWDropCallback dropCallback;

	private WindowSettings windowSettings;
	private static PointerBuffer monitors;
	private long[] cursorIDs;
	private long windowID;
	private WindowListener windowListener;

	public GLFW_Window() {
		if (!glfwInit()) {
			Console.severe(this, "Error initializing GLFW");
			System.exit(1);
		}
		setErrorCallback();
	}

	@Override
	public void run(WindowSettings windowSettings) {
		this.windowSettings = windowSettings;
		monitors = glfwGetMonitors();

		preInit(windowSettings);
		this.windowID = glfwCreateWindow(windowSettings.width, windowSettings.height, windowSettings.title, NULL, NULL);
		glfwMakeContextCurrent(this.windowID);
		applySettings(windowSettings);

		if (windowExists(this.windowID) == false) {
			Console.severe(this, "Error creating GLFW window");
			System.exit(1);
		}

		setLocalCallbacks();
		createCursors();
		NativeObjectManager.register(this);
	}

	@Override
	public void applySettings(WindowSettings windowSettings) {
		if (windowExists(this.windowID) == true) {
			glfwSetWindowTitle(this.windowID, windowSettings.title);
			glfwSetWindowPos(this.windowID, windowSettings.monitorPositionX, windowSettings.monitorPositionY);
			glfwSetWindowSize(this.windowID, windowSettings.width, windowSettings.height);
			glfwSetWindowAttrib(this.windowID, GLFW_SAMPLES, windowSettings.samples);
			glfwSetWindowAttrib(this.windowID, GLFW_RESIZABLE, windowSettings.resizeable ? GL_TRUE : GL_FALSE);

			if (windowSettings.icon != null) {
				setIcon(windowSettings.icon);
			}
			glfwSetWindowMonitor(this.windowID,
					windowSettings.fullscreen ? monitors.get(windowSettings.fullScreenMonitorIndex) : NULL,
					windowSettings.monitorPositionX, windowSettings.monitorPositionY, windowSettings.width,
					windowSettings.height, windowSettings.refreshRate);
			glfwSwapInterval(windowSettings.vsync ? GL_TRUE : GL_FALSE);
		}
	}

	private void preInit(WindowSettings windowSettings) {
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, WindowSettings.OGLVersion / 10);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, WindowSettings.OGLVersion % 10);
		glfwWindowHint(GLFW_REFRESH_RATE, windowSettings.refreshRate);
		glfwWindowHint(GLFW_SAMPLES, windowSettings.samples);
		glfwWindowHint(GLFW_RESIZABLE, windowSettings.resizeable ? GL_TRUE : GL_FALSE);
		if (windowSettings.noAPI) {
			glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
		}
	}

	private void createCursors() {
		cursorIDs = new long[6];
		for (int i = 0; i < cursorIDs.length; i++) {
			cursorIDs[i] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR + i);
		}
	}

	private static boolean windowExists(long windowID) {
		return windowID != NULL ? true : false;
	}

	public void setErrorCallback() {
		glfwSetErrorCallback(this.errorCallback = new GLFWErrorCallback() {
			@Override
			public void invoke(int error, long description) {
				Console.severe(
						"GLFW: [" + Integer.toHexString(error) + "]: " + GLFWErrorCallback.getDescription(description));
			}
		});
	}

	private void setLocalCallbacks() {
		glfwSetWindowSizeCallback(this.windowID, this.windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				GLFW_Window.this.windowSettings.width = width;
				GLFW_Window.this.windowSettings.height = height;
				windowListener.windowResized(width, height);
			}
		});

		glfwSetWindowFocusCallback(this.windowID, this.windowFocusCallback = new GLFWWindowFocusCallback() {
			@Override
			public void invoke(long window, boolean focused) {
				GLFW_Window.this.focused = focused;
				windowListener.windowFocus(focused);
			}
		});

		glfwSetWindowIconifyCallback(this.windowID, this.iconifyCallback = new GLFWWindowIconifyCallback() {
			@Override
			public void invoke(long window, boolean iconified) {
				GLFW_Window.this.iconified = iconified;
				windowListener.windowIconified();
			}
		});
		glfwSetWindowMaximizeCallback(this.windowID, this.maximizeCallback = new GLFWWindowMaximizeCallback() {
			@Override
			public void invoke(long window, boolean maximized) {
				GLFW_Window.this.maximized = maximized;
				windowListener.windowMaximized();
			}
		});

		glfwSetDropCallback(windowID, this.dropCallback = new GLFWDropCallback() {

			@Override
			public void invoke(long window, int count, long names) {
				String[] out = new String[count];
				for (int i = 0; i < count; i++) {
					out[i] = GLFWDropCallback.getName(names, i);
				}

				windowListener.windowDrop(out);
			}
		});
	}

	@Override
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}

	@Override
	public void swapBuffers() {
		glfwSwapBuffers(this.windowID);
	}

	@Override
	public void pollEvents() {
		glfwPollEvents();
	}

	@Override
	public double getTime() {
		return glfwGetTime();
	}

	@Override
	public boolean closeRequest() {
		return glfwWindowShouldClose(this.windowID);
	}

	@Override
	public void terminate() {
		glfwDestroyWindow(this.windowID);
		this.windowSizeCallback.close();
		this.windowFocusCallback.close();
		this.iconifyCallback.close();
		this.maximizeCallback.close();
		this.errorCallback.close();
		this.dropCallback.close();
		glfwTerminate();
	}

	@Override
	public void showWindow() {
		glfwShowWindow(this.windowID);
	}

	@Override
	public void hideWindow() {
		glfwHideWindow(this.windowID);
	}

	@Override
	public void iconifyWindow() {
		glfwIconifyWindow(this.windowID);
	}

	@Override
	public void createCursor(ImageBuffer imageBuffer, int xOffset, int yOffset) {
		GLFWImage image = GLFWImage.malloc();
		GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
		image.set(imageBuffer.getWidth(), imageBuffer.getHeight(), imageBuffer.getData());
		imagebf.put(0, image);
		glfwCreateCursor(image, xOffset, yOffset);
	}

	@Override
	public void cursorMode(int i) {
		glfwSetInputMode(this.windowID, GLFW_CURSOR, GLFW_CURSOR_NORMAL + i);
	}

	@Override
	public void requestAttention() {
		glfwRequestWindowAttention(this.windowID);
	}

	@Override
	public boolean isFocused() {
		return this.focused;
	}

	@Override
	public boolean isIconified() {
		return this.iconified;
	}

	@Override
	public boolean isMaximized() {
		return this.maximized;
	}

	@Override
	public void setListener(WindowListener windowListener) {
		this.windowListener = windowListener;
	}

	@Override
	public String getMonitorName(int index) {
		return glfwGetMonitorName(GLFW_Window.monitors.get(index));
	}

	@Override
	public void setCursor(int index) {
		glfwSetCursor(windowID, cursorIDs[index]);
	}

	@Override
	public void setIcon(ImageBuffer imageBuffer) {
		GLFWImage image = GLFWImage.malloc();
		GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
		image.set(imageBuffer.getWidth(), imageBuffer.getHeight(), imageBuffer.getData());
		imagebf.put(0, image);
		glfwSetWindowIcon(windowID, imagebf);
	}

	public long getWindowID() {
		return this.windowID;
	}

}
