package gen;

import java.io.File;
import java.nio.file.Path;

public class Util {
	// separated because too much variations how to get
	public static final File getCurrentDir()
	{
		return Path.of(".").toFile();
	}
}
