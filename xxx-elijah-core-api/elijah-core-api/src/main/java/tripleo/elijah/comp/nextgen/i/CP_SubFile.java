package tripleo.elijah.comp.nextgen.i;

import org.jetbrains.annotations.NotNull;

import tripleo.wrap.File;

public interface CP_SubFile {
	CP_Path getPath();

	@NotNull File toFile();
}
