package tripleo.elijah.comp.specs;

import java.io.*;

public record ElijahSpec(String f, File file, InputStream s) {
	public String getLongPath2() {
		//noinspection UnnecessaryLocalVariable
		final String absolutePath = new File(f).getAbsolutePath(); // !!
		return absolutePath;
	}
}
