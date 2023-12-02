package tripleo.elijah.comp.specs;

import tripleo.elijah.comp.graph.CM_Module;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public final class ElijahSpec_ implements ElijahSpec {
	private final String      file_name;
	private final File        file;
	private final InputStream s;
	private CM_Module cmModule;

	public ElijahSpec_(String file_name, File file, InputStream s) {
		this.file_name = file_name;
		this.file      = file;
		this.s         = s;
	}

	@Override
	public String getLongPath2() {
		// [T920907]
		// TODO 10/13 why new File(file_name) and not file??
		//noinspection UnnecessaryLocalVariable
		final String absolutePath = new File(file_name).getAbsolutePath(); // !!
		return absolutePath;
	}

	@Override
	public String file_name() {
		return file_name;
	}

	@Override
	public File file() {
		return file;
	}

	@Override
	public InputStream s() {
		return s;
	}

	@Override
	public CM_Module getModule() {
		return cmModule;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ElijahSpec) obj;
		return Objects.equals(this.file_name, that.file_name()) &&
				Objects.equals(this.file, that.file()) &&
				Objects.equals(this.s, that.s());
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
