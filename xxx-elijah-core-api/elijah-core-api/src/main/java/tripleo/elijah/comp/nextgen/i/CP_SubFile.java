package tripleo.elijah.comp.nextgen.i;

import org.jetbrains.annotations.*;

import tripleo.wrap.*;

public interface CP_SubFile {
	CP_Path getPath();

	@NotNull
	File toFile();
}
