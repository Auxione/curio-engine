package core;

import common.Console;
import graphics.Graphics;

public class EngineUtils {
	/**
	 * Print system info to console.
	 * 
	 * @param graphicsContext info to get from
	 */
	public static void printSystemInfo(Graphics graphics) {
		Console.info("GPU: " + graphics.getHardwareName());
		printSystemInfo();
	}

	/**
	 * Print system info to console.
	 * 
	 */
	public static void printSystemInfo() {
		Console.info("CPU: " + System.getenv("PROCESSOR_IDENTIFIER") + " " + System.getenv("NUMBER_OF_PROCESSORS")
				+ " cores.");
		Console.info("Architecture: " + System.getenv("PROCESSOR_ARCHITECTURE"));
		Console.info("JVM-MAXMEM: " + getMaxMemory() + " MB");
		Console.info("JVM-FREEMEM: " + getFreeMemory() + " MB");
		Console.info("JVM-VERSION: " + Runtime.version().feature());
	}

	/**
	 * Returns the maximum amount of memory that the JVM can use.
	 */
	public static int getMaxMemory() {
		return (int) (Runtime.getRuntime().maxMemory() * 0.000001f);
	}

	/**
	 * Returns the amount of free memory in JVM.
	 */
	public static int getFreeMemory() {
		return (int) (Runtime.getRuntime().freeMemory() * 0.000001f);
	}
}
