package tripleo.elijah.comp.nextgen;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.nio.file.*;

public interface CP_StdlibPath extends CP_Path, _CP_RootPath {
	@Override
	CP_Path child(String aSubPath);

	@Override
	@Nullable String getName();

	@Override
	@Nullable CP_Path getParent();

	@Override
	@NotNull Path getPath();

	@Override
	@NotNull Promise<Path, Void, Void> getPathPromise();

	@Override
	@Nullable File getRootFile();

	@Override
	@NotNull _CP_RootPath getRootPath();

	@Override
	@Nullable CP_SubFile subFile(String aFile);

	@Override
	@NotNull File toFile();

	@Override
	String toString();
}
