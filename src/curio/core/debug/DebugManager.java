package core.debug;

import java.awt.Font;
import java.util.HashMap;

import common.Console;
import common.FrameCounter;
import core.EngineUtils;
import graphics.Color;
import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;
import graphics.renderer2d.StringRenderer;

public class DebugManager {
	public static HashMap<DebugObject, Color> debugObjects = new HashMap<DebugObject, Color>();
	public static FontData debugfont;
	private static boolean printActive = false;
	private static boolean drawActive = false;
	private static boolean logDebugPrint = false;;

	public static void register(DebugObject debug, Color color) {
		debugObjects.put(debug, color);
	}

	public static void register(DebugObject debug) {
		debugObjects.put(debug, new Color(Color.white));
	}

	public static void draw(Renderer2D renderer2d) {
		if (!drawActive)
			return;

		for (DebugObject debugObj : debugObjects.keySet()) {
			debugObj.debugDraw(renderer2d, debugObjects.get(debugObj));
		}
	}

	public static void createDebugFont(String name) {
		debugfont = FontData.createFontDataFromAWT(new Font(name, Font.PLAIN, 12));
	}

	public static void renderString(Renderer2D renderer2D, String string, Color color, float x, float y) {
		if (debugfont != null) {
			StringRenderer.render(renderer2D, debugfont, string, color, x, y - 20);
		}
	}

	public static void print() {
		if (!printActive)
			return;
		for (DebugObject debugObj : debugObjects.keySet()) {
			if (debugObj.debugPrint() == null) {
				continue;
			}
			if (logDebugPrint) {
				Console.info(debugObj, debugObj.debugPrint());
			} else {
				System.out.println(debugObj.getClass().getSimpleName() + ": " + debugObj.debugPrint());
			}
		}
	}

	public static void applySettings(DebugManagerSettings debugManagerSettings) {
		if (debugManagerSettings.printSysInfo) {
			EngineUtils.printSystemInfo();
		}

		DebugManager.printActive = debugManagerSettings.debugPrint;
		DebugManager.drawActive = debugManagerSettings.debugDraw;

		DebugManager.logDebugPrint = debugManagerSettings.log;

	}

	public static void applySettings(DebugManagerSettings debugManagerSettings, Renderer2D renderer2D,
			FrameCounter frameCounter) {
		if (debugManagerSettings.registerFrameCounter) {
			DebugManager.register(frameCounter);
		}
		if (debugManagerSettings.registerRenderer2d) {
			DebugManager.register(renderer2D);
		}
		applySettings(debugManagerSettings);
	}
}
