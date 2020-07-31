package platform.opengl;

import static org.lwjgl.system.MemoryUtil.memByteBuffer;

import static org.lwjgl.opengl.GL45.*;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.system.MemoryUtil;

import common.Console;
import graphics.renderer2d.Renderer2D;

//TODO: nvidia cards sends debug message for vbo and ibo.Code to ignore those messages from logging.
public class OGL_RenderUtils {
	public static void enableDebug(Renderer2D renderer2d) {
		glEnable(GL_DEBUG_OUTPUT);
		glDebugMessageCallback(new GLDebugMessageCallbackI() {
			@Override
			public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
				Console.severe(renderer2d, MemoryUtil.memUTF8(memByteBuffer(message, length)));
			}
		}, 0);
	}

	public static int getplatformMaxCombinedTexUnits() {
		return glGetInteger(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
	}

	public static void WireframeMode(int value) {
		if (value == 1) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		} else if (value == 2) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
		} else {
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}
	}

	public static class Blending {
		private static int blendingSRC = GL_SRC_ALPHA;
		private static int blendingDST = GL_ONE_MINUS_SRC_ALPHA;

		public static void set(boolean value) {
			if (value == true) {
				glEnable(GL_BLEND);
				glBlendFunc(Blending.blendingSRC, Blending.blendingDST);
			} else {
				glDisable(GL_BLEND);
			}
		}

		public static void setValues(int src, int dest) {
			Blending.blendingSRC = src;
			Blending.blendingDST = dest;
		}
	}
}
