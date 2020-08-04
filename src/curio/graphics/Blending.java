package graphics;

import core.EngineSettings;
import platform.opengl.OGL_Blending;

public abstract class Blending {
	private static Blending instance;

	public static Blending getInstance() {
		if (instance == null) {
			switch (EngineSettings.renderer) {

			default:
				instance = new OGL_Blending();
			}
		}
		return instance;
	}

	public abstract void set(boolean value);

	public abstract void function(int src, int dest);
}
