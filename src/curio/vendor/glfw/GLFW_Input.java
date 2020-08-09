package vendor.glfw;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import static org.lwjgl.glfw.GLFW.glfwGetKeyName;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.system.MemoryUtil;

import common.Console;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import core.Input;
import core.Window;
import core.events.InputListener;

public class GLFW_Input implements Input, NativeObject {
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWScrollCallback mouseScrollCallBack;
	private GLFWKeyCallback KeyCallBack;
	private GLFWCursorPosCallback cursorPositionCallBack;

	private long window;

	private DoubleBuffer mouseBuffer = MemoryUtil.memAllocDouble(4);

	private InputListener inputListener;

	public GLFW_Input() {
	}

	@Override
	public void run(Window window) {
		this.window = ((GLFW_Window) window).getWindowID();
		Console.fine(this, "Initialized.");
		setCallBack(this.window);
		NativeObjectManager.register(this);
		Console.fine(this, "Reading input from window: " + this.window);
	}

	@Override
	public void setListener(InputListener inputListener) {
		this.inputListener = inputListener;
		Console.fine(this, "Listener registered: " + inputListener);
	}

	@Override
	public void terminate() {
		this.mouseButtonCallback.close();
		this.KeyCallBack.close();
		this.mouseScrollCallBack.close();
		this.cursorPositionCallBack.close();
	}

	private void setCallBack(long window) {
		glfwSetCursorPosCallback(window, this.cursorPositionCallBack = new GLFWCursorPosCallback() {

			@Override
			public void invoke(long window, double xpos, double ypos) {
				GLFW_Input.this.mouseBuffer.put(0, xpos);
				GLFW_Input.this.mouseBuffer.put(1, ypos);
			}
		});
		
		glfwSetMouseButtonCallback(window, this.mouseButtonCallback = new GLFWMouseButtonCallback() {

			@Override
			public void invoke(long window, int button, int action, int mods) {
				if (action == GLFW_PRESS) {
					inputListener.mousePressed(button, (int) GLFW_Input.this.mouseBuffer.get(0),
							(int) GLFW_Input.this.mouseBuffer.get(1));
				}

				if (action == GLFW_RELEASE) {
					inputListener.mouseReleased(button, (int) GLFW_Input.this.mouseBuffer.get(0),
							(int) GLFW_Input.this.mouseBuffer.get(1));
				}
				if (action == GLFW_REPEAT) {
					inputListener.mouseRepeat(button, (int) GLFW_Input.this.mouseBuffer.get(0),
							(int) GLFW_Input.this.mouseBuffer.get(1));
				}
			}
		});

		glfwSetScrollCallback(window, this.mouseScrollCallBack = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				inputListener.mouseWheelChanged((int) xoffset, (int) yoffset);
				GLFW_Input.this.mouseBuffer.put(2, xoffset);
				GLFW_Input.this.mouseBuffer.put(3, yoffset);
			}
		});

		glfwSetKeyCallback(window, this.KeyCallBack = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (action == GLFW_PRESS && glfwGetKeyName(key, scancode) != null) {
					inputListener.keyPressed(key, glfwGetKeyName(key, scancode).charAt(0));
				} else if (action == GLFW_PRESS) {
					inputListener.keyPressed(key, '\0');
				}

				if (action == GLFW_RELEASE && glfwGetKeyName(key, scancode) != null) {
					inputListener.keyReleased(key, glfwGetKeyName(key, scancode).charAt(0));
				} else if (action == GLFW_RELEASE) {
					inputListener.keyReleased(key, '\0');
				}

				if (action == GLFW_REPEAT && glfwGetKeyName(key, scancode) != null) {
					inputListener.keyRepeat(key, glfwGetKeyName(key, scancode).charAt(0));
				} else if (action == GLFW_REPEAT) {
					inputListener.keyRepeat(key, '\0');
				}
			}
		});
	}

	@Override
	public int getMouseX() {
		return (int) mouseBuffer.get(0);
	}

	@Override
	public int getMouseY() {
		return (int) mouseBuffer.get(1);
	}

	@Override
	public int getScrollX() {
		return (int) mouseBuffer.get(2);
	}

	@Override
	public int getScrollY() {
		return (int) mouseBuffer.get(3);
	}

	@Override
	public boolean getKeyPress(int key) {
		return glfwGetKey(this.window, key) == GLFW_PRESS;
	}

	@Override
	public boolean getKeyRelease(int key) {
		return glfwGetKey(this.window, key) == GLFW_RELEASE;
	}

	@Override
	public boolean getKeyRepeat(int key) {
		return glfwGetKey(this.window, key) == GLFW_REPEAT;
	}

	@Override
	public boolean getMousePress(int button) {
		if ((button == Input.MOUSE_BUTTON_LEFT || button == Input.MOUSE_BUTTON_RIGHT
				|| button == Input.MOUSE_BUTTON_MIDDLE || button == Input.MOUSE_BUTTON_4
				|| button == Input.MOUSE_BUTTON_5) && glfwGetMouseButton(window, button) == GLFW_PRESS) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getMouseRelease(int button) {
		if ((button == Input.MOUSE_BUTTON_LEFT || button == Input.MOUSE_BUTTON_RIGHT
				|| button == Input.MOUSE_BUTTON_MIDDLE || button == Input.MOUSE_BUTTON_4
				|| button == Input.MOUSE_BUTTON_5) && glfwGetMouseButton(window, button) == GLFW_RELEASE) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getMouseRepeat(int button) {
		if ((button == Input.MOUSE_BUTTON_LEFT || button == Input.MOUSE_BUTTON_RIGHT
				|| button == Input.MOUSE_BUTTON_MIDDLE || button == Input.MOUSE_BUTTON_4
				|| button == Input.MOUSE_BUTTON_5) && glfwGetMouseButton(window, button) == GLFW_REPEAT) {
			return true;
		}
		return false;
	}

	@Override
	public String getClipboardContent() {
		return glfwGetClipboardString(this.window);
	}

	@Override
	public void setClipboardContent(String string) {
		glfwSetClipboardString(this.window, string);
	}
}