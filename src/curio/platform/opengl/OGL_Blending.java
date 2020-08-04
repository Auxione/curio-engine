package platform.opengl;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import graphics.Blending;

public class OGL_Blending extends Blending {
	private int blendingSRC = GL_SRC_ALPHA;
	private int blendingDST = GL_ONE_MINUS_SRC_ALPHA;

	public void set(boolean value) {
		if (value == true) {
			glEnable(GL_BLEND);
			glBlendFunc(blendingSRC, blendingDST);
		} else {
			glDisable(GL_BLEND);
		}
	}

	public void function(int src, int dest) {
		blendingSRC = src;
		blendingDST = dest;
	}
}
