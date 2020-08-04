package graphics;

import core.EngineSettings;
import graphics.renderer2d.Renderer2D;
import platform.opengl.OGL_RenderUtils;

public abstract class RenderUtilities {
	private static RenderUtilities instance;

	public static RenderUtilities getInstance() {
		if (instance == null) {
			switch (EngineSettings.renderer) {

			default:
				instance = new OGL_RenderUtils();
			}
		}
		return instance;
	}

	public abstract void wireFrameMode(int mode);

	public abstract void enableDebug(Renderer2D renderer2d);
	
	public abstract int getMaxTextureSlots();

	public abstract int getTextureSlotSize();

	public abstract String getVendorName();

	public abstract String getHardwareName();

}
