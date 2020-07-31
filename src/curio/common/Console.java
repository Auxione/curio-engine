package common;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Console {
	private static Logger logger;
	private static Console instance;

	private Console(Level level) {
		logger = Logger.getLogger(Console.class.getName());
		logger.setLevel(level);
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");

		FileHandler fh;
		try {
			fh = new FileHandler("curio.log", true);
			SimpleFormatter sf = new SimpleFormatter();
			fh.setFormatter(sf);
			logger.addHandler(fh);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		Console.fine("====================================");
		Console.fine(this, "Started.");
	}

	public static void createInstance(Level level) {
		if (instance == null) {
			instance = new Console(level);
		}
	}

	private static String format(Object source, String input) {
		return source.getClass().getSimpleName() + " : " + input;
	}

	public static void finest(Object source, String input) {
		logger.finest(format(source, input));
	}

	public static void finer(Object source, String input) {
		logger.finer(format(source, input));
	}

	public static void fine(Object source, String input) {
		logger.fine(format(source, input));
	}

	public static void config(Object source, String input) {
		logger.config(format(source, input));
	}

	public static void info(Object source, String input) {
		logger.info(format(source, input));
	}

	public static void warning(Object source, String input) {
		logger.warning(format(source, input));
	}

	public static void severe(Object source, String input) {
		logger.severe(format(source, input));
	}

	public static void finest(String input) {
		logger.finest(input);
	}

	public static void finer(String input) {
		logger.finer(input);
	}

	public static void fine(String input) {
		logger.fine(input);
	}

	public static void config(String input) {
		logger.config(input);
	}

	public static void info(String input) {
		logger.info(input);
	}

	public static void warning(String input) {
		logger.warning(input);
	}

	public static void severe(String input) {
		logger.severe(input);
	}
}
