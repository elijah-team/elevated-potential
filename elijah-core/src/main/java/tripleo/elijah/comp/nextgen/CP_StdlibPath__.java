package tripleo.elijah.comp.nextgen;

import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;

import java.io.*;
import java.nio.file.*;

public class CP_StdlibPath__ implements CP_StdlibPath {
	private final Compilation                     c;
	private final DeferredObject<Path, Void, Void> _pathPromise = new DeferredObject<>();

	public CP_StdlibPath__(final Compilation aC) {
		c = aC;
	}

	@Override
	public CP_Path child(final String aSubPath) {
		return new CP_SubFile__(this, aSubPath).getPath();
	}

	@Override
	public @Nullable String getName() {
		return null;
	}

	@Override
	public @Nullable CP_Path getParent() {
		return null;
	}

	@Override
	public @NotNull Path getPath() {
		return Path.of("lib_elijjah");
	}

	@Override
	public @NotNull Promise<Path, Void, Void> getPathPromise() {
		return _pathPromise;
	}

	@Override
	public @Nullable File getRootFile() {
		return null;
	}

	@Override
	public @NotNull _CP_RootPath getRootPath() {
		return this;
	}

	@Override
	public @Nullable CP_SubFile subFile(final String aFile) {
		return null;
	}

	@Override
	public @NotNull File toFile() {
		return getPath().toFile();
	}

	@Override
	public String toString() {
		String result;

		if (_pathPromise.isPending()) {
			result = "CP_StdlibPath{UNRESOLVED c='%s'}".formatted(c.getCompilationNumberString());
		} else {
			final String[] pathPromise = {""}; // !!
			_pathPromise.then((Path x) -> pathPromise[0] = x.toString());
			result = "CP_StdlibPath{RESOLVED c=%s, pathPromise=%s}"
					.formatted(c.getCompilationNumberString(), pathPromise[0]);
		}

		return result;
	}
}
