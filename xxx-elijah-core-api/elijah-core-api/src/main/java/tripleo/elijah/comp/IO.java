package tripleo.elijah.comp;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.util.*;
import tripleo.elijah.util.io.*;
import tripleo.wrap.File;

public interface IO {
	@Nullable
	CharSource openRead(@NotNull Path p);

	@NotNull
	DisposableCharSink openWrite(@NotNull Path p) throws IOException;

	@NotNull
	InputStream readFile(@NotNull tripleo.wrap.File f) throws FileNotFoundException;

	_IO_ReadFile readFile2(@NotNull File f) throws FileNotFoundException;

	void record(@NotNull FileOption read, @NotNull File file);

	void record(@NotNull _IO_ReadFile aReadFile);

	default void record(@NotNull FileOption read, @NotNull Path p) {
		record(read, tripleo.wrap.File.wrap(p.toFile()));
	}

	boolean recordedRead(File file);

	boolean recordedWrite(File file);

	List<_IO_ReadFile> recordedreads_io();

	public static class _IO_ReadFile {

		private final File            file;
		private       FileInputStream inputStream;

		public _IO_ReadFile(File aFile) {
			file = aFile;
		}

		@Override
		public String toString() {
			return "_IO_ReadFile{" +
					"file=" + file +
					'}';
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

		public EIT_SourceOrigin getSourceOrigin() {
			final String           fn = getFileName();
			final EIT_SourceOrigin x;

			if (fn.equals("lib_elijjah/lib-c/Prelude.elijjah")) {
				x = EIT_SourceOrigin.PREL;
			} else if (fn.startsWith("lib_elijjah/")) {
				x = EIT_SourceOrigin.LIB;
			} else if (fn.startsWith("test/")) {
				x = EIT_SourceOrigin.SRC;
			} else {
				throw new IllegalStateException("Error"); // Operation??
			}

			return x;
		}

		public void setInputStream(FileInputStream aInputStream) {

			inputStream = aInputStream;
		}

		public FileInputStream getInputStream() {
			return inputStream;
		}

		public String getLongPath1() throws IOException {
			return getFile().getCanonicalPath();
		}
	}

	List<File> recordedwrites();
}
