package core.debug;

import graphics.Color;
import graphics.renderer2d.Renderer2D;

public interface DebugObject {

	public void debugDraw(Renderer2D renderer2D, Color color);

	public String debugPrint();
}
