package common.utilities;

import java.util.ArrayList;

import common.Console;

/**
 * NativeObject manager for cleaning any remaining resource from non java
 * memory.
 * 
 * @see NativeObject
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class NativeObjectManager {
	private static ArrayList<NativeObject> List = new ArrayList<NativeObject>();

	public static void register(NativeObject nativeObject) {
		List.add(nativeObject);
	}

	/**
	 * Clean all non java memory.
	 */
	public static void terminateAll() {
		for (NativeObject njo : NativeObjectManager.List) {
			njo.terminate();
			Console.fine(njo, "Terminated.");
		}
	}

}
