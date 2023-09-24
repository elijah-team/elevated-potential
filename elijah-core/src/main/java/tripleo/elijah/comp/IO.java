/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.util.*;
import tripleo.elijah.util.io.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class IO {

	// exists, delete, isType ....

	public final List<_IO_ReadFile> recordedreads  = new ArrayList<>();
	public final List<File> recordedwrites = new ArrayList<File>();

	public @Nullable CharSource openRead(final @NotNull Path p) {
		record(FileOption.READ, p);
		return null;
	}

	public @NotNull DisposableCharSink openWrite(final @NotNull Path p) throws IOException {
		record(FileOption.WRITE, p);
		return new FileCharSink(Files.newOutputStream(p));
	}

	public @NotNull InputStream readFile(final @NotNull File f) throws FileNotFoundException {
		record(FileOption.READ, f);
		return new FileInputStream(f);
	}

	private void record(@NotNull final FileOption read, @NotNull final File file) {
		switch (read) {
		case WRITE:
			recordedwrites.add(file);
			break;
		case READ:
			recordedreads.add(new _IO_ReadFile(file));
			break;
		default:
			throw new IllegalStateException("Cant be here");
		}
	}

	private void record(final @NotNull FileOption read, @NotNull final Path p) {
		record(read, p.toFile());
	}

	public boolean recordedRead(final File file) {
		return recordedreads.contains(file);
	}

	public boolean recordedWrite(final File file) {
		return recordedwrites.contains(file);
	}

	public List<_IO_ReadFile> recordedreads_io() {
		return recordedreads;
	}

	public class _IO_ReadFile {

		private final File file;

		public _IO_ReadFile(File aFile) {
			file = aFile;
		}

		public File getFile() {
			return file;
		}

		public String getFileName() {
			return file.toString();
		}

		public Operation<String> hash() {
			final @NotNull Operation<String> hh2 = Helpers.getHashForFilename(getFileName());
			return hh2;
		}
	}
}

//
//
//
