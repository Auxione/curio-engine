package platform.opengl;

import static org.lwjgl.system.MemoryUtil.memByteBuffer;
import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL20.GL_MAX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL45.*;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.system.MemoryUtil;

import common.Console;
import graphics.RenderUtilities;
import graphics.renderer2d.Renderer2D;

public class OGL_RenderUtils extends RenderUtilities {
	@Override
	public void wireFrameMode(int mode) {
		if (mode == 1) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		} else if (mode == 2) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
		} else {
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}
	}

	public void enableDebug(Renderer2D renderer2d) {
		glEnable(GL_DEBUG_OUTPUT);
		glDebugMessageCallback(new GLDebugMessageCallbackI() {
			@Override
			public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
				Console.severe(renderer2d, MemoryUtil.memUTF8(memByteBuffer(message, length)));
			}
		}, 0);
	}

	@Override
	public final String getVendorName() {
		return glGetString(GL_VENDOR);
	}

	@Override
	public final String getHardwareName() {
		return glGetString(GL_RENDERER);
	}

	@Override
	public final int getMaxTextureSlots() {
		return glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS);
	}

	@Override
	public final int getTextureSlotSize() {
		return glGetInteger(GL_MAX_TEXTURE_SIZE);
	}
}
