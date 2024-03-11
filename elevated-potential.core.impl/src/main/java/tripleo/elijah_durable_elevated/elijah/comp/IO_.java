/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.comp;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.FileOption;
import tripleo.elijah.comp.IO;
import tripleo.elijah.util.io.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

import tripleo.elijah_prolific.v.V;
import tripleo.wrap.File;

public class IO_ implements IO {

	// exists, delete, isType ....

	public final  List<File>         recordedwrites = new ArrayList<>();
	private final List<_IO_ReadFile> recordedreads  = new ArrayList<>();

	@Override
	public @Nullable CharSource openRead(final @NotNull Path p) {
		record(FileOption.READ, p);
		return null;
	}

	@Override
	public @NotNull DisposableCharSink openWrite(final @NotNull Path p) throws IOException {
		record(FileOption.WRITE, p);
		V.asv(V.e.IO_openWrite, ""+p);
		return new FileCharSink(Files.newOutputStream(p));
	}

	@Override
	public @NotNull InputStream readFile(final @NotNull File f) throws FileNotFoundException {
		record(FileOption.READ, f);
		return f.getFileInputStream();
	}

	@Override
	public _IO_ReadFile readFile2(final @NotNull File f) throws FileNotFoundException {
		final _IO_ReadFile readFile = new _IO_ReadFile(f);

		record(readFile);

		FileInputStream inputStream = f.getFileInputStream();
		readFile.setInputStream(inputStream);

		return readFile;
	}

	@Override
	public void record(@NotNull final FileOption read, @NotNull final File file) {
 		switch (read) {
		case WRITE:
			recordedwrites.add(file);
			break;
		case READ:
			record(new _IO_ReadFile(file));
			break;
		default:
			throw new IllegalStateException("Cant be here");
		}
	}

	@Override
	public boolean recordedRead(final File file) {
		return recordedreads.stream().anyMatch(x-> Objects.equals(x.getFile().getAbsolutePath(), file.getAbsolutePath()));
	}

	@Override
	public boolean recordedWrite(final File file) {
		return recordedwrites.contains(file);
	}

	@Override
	public List<_IO_ReadFile> recordedreads_io() {
		return recordedreads;
	}

	@Override
	public void record(@NotNull _IO_ReadFile aReadFile) {
		recordedreads.add((aReadFile));
	}

	@Override
	public List<File> recordedwrites() {
		return this.recordedwrites;
	}
}

//
//
//
