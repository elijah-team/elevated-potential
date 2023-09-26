package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.*;
import tripleo.elijah.util.*;

import java.io.*;

public record EzSpec(String f, InputStream s, File file) {
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
