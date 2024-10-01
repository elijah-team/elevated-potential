package tripleo.elijah.comp.nextgen.i;

import java.nio.file.Path;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tripleo.elijah_fluffy.util.Eventual;
import tripleo.wrap.File;

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
	@NotNull Eventual<Path> getPathPromise();

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
