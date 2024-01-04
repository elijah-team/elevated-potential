package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.comp.Compilation;

import tripleo.elijah.comp.IO;
import tripleo.elijah.util.io.DisposableCharSink;
import tripleo.wrap.File;

import java.io.IOException;
import java.nio.file.Path;

public class CP_StdlibPath__ implements CP_StdlibPath {
	//private final Compilation                     c;
	private final Eventual<Path> _pathPromise = new Eventual<>();
	private final String                     compilationNumberString;

	public CP_StdlibPath__(final Compilation aC) {
		//c                       = aC;
		compilationNumberString = aC.getCompilationNumberString();

		if (aC instanceof EventualRegister er) {
			er.register(_pathPromise);
		} else {
			assert false;
		}
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
	public @NotNull Eventual<Path> getPathPromise() {
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
		return tripleo.wrap.File.wrap(getPath().toFile());
	}

	@Override
	public String toString() {
		String result;

		if (_pathPromise.isPending()) {
			result = "CP_StdlibPath{UNRESOLVED c='%s'}".formatted(compilationNumberString);
		} else {
			final String[] pathPromise = {""}; // !!
			_pathPromise.then((Path x) -> pathPromise[0] = x.toString());
			result = "CP_StdlibPath{RESOLVED c=%s, pathPromise=%s}"
					.formatted(compilationNumberString, pathPromise[0]);
		}

		return result;
	}

	@Override
	public Path toPath() {
		return getPath();
	}

	@Override
	public DisposableCharSink newIOWriter(final IO io) {
		try {
			return io.openWrite(this.getPath());
		} catch (IOException aE) {
			throw new RuntimeException(aE);
		}
	}

	@Override
	public String asString() {
		return this.toString();
	}

	@Override
	public boolean samePath(Path px) {
		throw new UnsupportedOperationException("TODO 12/28");
	}
}
