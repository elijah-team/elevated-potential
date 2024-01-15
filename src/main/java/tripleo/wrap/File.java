package tripleo.wrap;

import com.google.common.io.Files;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.i.USE_Reasoning;
import tripleo.elijah.comp.nextgen.i.CP_Path;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class File {
	java.io.File wrap;
	private CP_Path connect;

	private USE_Reasoning useReasoning;

	public File(final String aBase, final String aName) {
		wrap = new java.io.File(aBase, aName);
	}

	public File(final String aBase) {
		wrap = new java.io.File(aBase);
	}

	public File(final java.io.File aDirectory, final String aFileName) {
		wrap = new java.io.File(aDirectory, aFileName);
	}

	public File(final java.io.File aFile) {
		assert aFile != null;
		wrap = aFile;
	}

	public File(final File aDirectory, final String aFileName) {
		wrap = new java.io.File(aDirectory.wrapped(), aFileName);
	}

	public static File wrap(final java.io.File aFile) {
		return new File(aFile);
	}

	public static List<String> readLines(final String aFilename, final Charset aCharset) throws IOException {
		return Files.readLines(new java.io.File(aFilename), aCharset);
	}

	public boolean exists() {
		return wrap.exists();
	}

	public String getName() {
		return wrap.getName();
	}

	public java.io.File getCanonicalFile() throws IOException {
		return wrap.getCanonicalFile();
	}

	public String getAbsolutePath() {
		return wrap.getAbsolutePath();
	}

	@Override
	public String toString() {
		final String wrapExistsS = wrap != null ? "" + wrap.exists() : "<null>";
		final String s = "File{"
				+ "wrap=" + wrap
				+ " exists=" + wrapExistsS
				+ " connect=" + connect
				+ '}';
		return s;
	}

	public InputStream readFile(final IO aIo) throws FileNotFoundException {
		return aIo.readFile(this);
	}

	public FileInputStream getFileInputStream() throws FileNotFoundException {
		return new FileInputStream(wrap);
	}

	public boolean isDirectory() {
		return wrap.isDirectory();
	}

	public java.io.File wrapped() {
		return this.wrap;
	}

	public long length() {
		return wrap.length();
	}

	public void mkdirs() {
		wrap.mkdirs();
	}

	public tripleo.wrap.File getParentFile() {
		final java.io.File pf = wrap.getParentFile();
		if (pf == null) return null; // README 12/28 interesting
		return tripleo.wrap.File.wrap(pf);
	}

	public String[] list(final FilenameFilter aFilter) {
		return wrap.list(aFilter);
	}

	public @NotNull tripleo.wrap.File[] listFiles(final FilenameFilter aFilter) {
		// FIXME 11/27 Get some help with this one
		final java.io.File[] r = wrap.listFiles(aFilter);
		if (r != null) {
			final tripleo.wrap.File[] R = new File[r.length];
			for (int i = 0; i < r.length; i++) {
				R[i] = new tripleo.wrap.File(r[i]);
			}
			return R;
		} else {
			return new File[0];
		}
	}

	public String getCanonicalPath() throws IOException {
		// TODO Auto-generated method stub
		return wrap.getCanonicalPath();
	}

	public CP_Path getConnect() {
		return connect;
	}

	public void setConnect(final CP_Path aConnectPath) {
		connect = aConnectPath;
	}

	public USE_Reasoning getUseReasoning() {
		return useReasoning;
	}

	public void setUseReasoning(final USE_Reasoning aUseReasoning) {
		useReasoning = aUseReasoning;
	}
}
