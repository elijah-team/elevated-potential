package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Operation;

import java.io.*;
import java.util.function.Supplier;

public record EzSpec(String file_name, File file, @Nullable Supplier<InputStream> sis) {
	public @NotNull Operation<String> absolute1() {
		// [T920907]
		String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString();
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
		return Operation.success(absolutePath);
	}
}
