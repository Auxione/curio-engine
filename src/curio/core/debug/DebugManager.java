package core.debug;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import common.Console;
import core.EngineUtils;
import graphics.Color;
import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;
import graphics.renderer2d.StringRenderer;

public class DebugManager {
	public static HashMap<DebugDraw, Color> debugDrawObjects = new HashMap<DebugDraw, Color>();
	public static ArrayList<DebugPrint> debugPrintObjects = new ArrayList<DebugPrint>();

	public static FontData debugfont;
	private static boolean printActive = false;
	private static boolean drawActive = false;
	private static boolean logDebugPrint = false;;

	public static void register(DebugDraw debug, Color color) {
		debugDrawObjects.put(debug, color);
	}

	public static void register(DebugPrint debug) {
		debugPrintObjects.add(debug);
	}

	public static void register(DebugObject debug, Color color) {
		register(debug, color);
		register(debug);
	}

	public static void draw(Renderer2D renderer2d) {
		if (!drawActive)
			return;

		for (DebugDraw debugObj : debugDrawObjects.keySet()) {
			if (debugObj == null) {
				continue;
			}
			debugObj.debugDraw(renderer2d, debugDrawObjects.get(debugObj));
		}
	}

	public static void createDebugFont(String name) {
		debugfont = FontData.createFromAWT(new Font(name, Font.PLAIN, 12));
	}

	public static void renderString(Renderer2D renderer2D, String string, Color color, float x, float y) {
		if (debugfont != null) {
			StringRenderer.render(renderer2D, debugfont, string, color, x, y - 20);
		}
	}

	public static void print() {
		if (!printActive)
			return;
		for (DebugPrint debugObj : debugPrintObjects) {
			if (debugObj == null) {
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
}
