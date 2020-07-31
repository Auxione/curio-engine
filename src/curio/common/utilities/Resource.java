package common.utilities;

import java.io.File;
import java.io.IOException;

/**
 * Resource interface for {@link ResourceManager}
 * 
 * @author Mehmet Cem Zarifoglu
 *
 * @param <type> the type of the resource
 */
public interface Resource<type> {
	/**
	 * Load resource from file.
	 * 
	 * @param file the file to read.
	 * @return the loaded resource or null if there is an error in loading.
	 * @throws IOException
	 */
	public type loadFile(File file) throws IOException;
}
