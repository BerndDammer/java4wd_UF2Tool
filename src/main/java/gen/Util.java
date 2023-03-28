package gen;

import java.io.File;
import java.nio.file.Path;

public class Util {
	public static final File getCurrentDir()
	{
//		return FileSystems.getDefault().getPath(".").toFile();
		return Path.of(".").toFile();
	}
}
