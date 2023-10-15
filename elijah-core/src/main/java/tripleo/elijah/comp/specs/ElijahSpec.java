package tripleo.elijah.comp.specs;

import java.io.*;

public record ElijahSpec(String file_name, File file, InputStream s) {
	public String getLongPath2() {
		// [T920907]
		// TODO 10/13 why new File(file_name) and not file??
		//noinspection UnnecessaryLocalVariable
		final String absolutePath = new File(file_name).getAbsolutePath(); // !!
		return absolutePath;
	}
}
