package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.function.*;

public record EzSpec(String file_name, @Nullable Supplier<InputStream> s, File file) {
	public @NotNull InputStream getInputStream(Compilation c) throws FileNotFoundException {
		if (s == null) {
			return c.getIO().readFile(file);
		} else {
			return s.get();
		}
	}

	public @NotNull Operation<String> absolute1() {
		String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString();
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
		return Operation.success(absolutePath);
	}
}
