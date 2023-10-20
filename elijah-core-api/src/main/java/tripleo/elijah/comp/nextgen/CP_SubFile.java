package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface CP_SubFile {
	CP_Path getPath();

	@NotNull File toFile();
}
