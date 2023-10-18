package tripleo.elijah.comp.specs;

import java.io.*;
import java.util.Objects;

public final class ElijahSpec {
	private final String      file_name;
	private final File        file;
	private final InputStream s;

	public ElijahSpec(String file_name, File file, InputStream s) {
		this.file_name = file_name;
		this.file      = file;
		this.s         = s;
	}

	public String getLongPath2() {
		// [T920907]
		// TODO 10/13 why new File(file_name) and not file??
		//noinspection UnnecessaryLocalVariable
		final String absolutePath = new File(file_name).getAbsolutePath(); // !!
		return absolutePath;
	}

	public String file_name() {
		return file_name;
	}

	public File file() {
		return file;
	}

	public InputStream s() {
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ElijahSpec) obj;
		return Objects.equals(this.file_name, that.file_name) &&
				Objects.equals(this.file, that.file) &&
				Objects.equals(this.s, that.s);
	}

	@Override
	public int hashCode() {
		return Objects.hash(file_name, file, s);
	}

	@Override
	public String toString() {
		return "ElijahSpec[" +
				"file_name=" + file_name + ", " +
				"file=" + file + ", " +
				"s=" + s + ']';
	}

}
