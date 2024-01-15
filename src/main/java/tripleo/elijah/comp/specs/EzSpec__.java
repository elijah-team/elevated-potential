package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.ExceptionDiagnostic;

import java.io.*;

import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.wrap.File;
import java.util.function.Supplier;

public record EzSpec__(
		String file_name,
		tripleo.wrap.File file,
		@Nullable Supplier<InputStream> sis
		) implements EzSpec {
	public static Operation2<EzSpec> of(final String aFileName, final File aFile, final Operation<InputStream> aInputStreamOperation) {
		if (aInputStreamOperation.mode() == Mode.SUCCESS) {
			final EzSpec__ ezSpec = new EzSpec__(aFileName, aFile, new Supplier<InputStream>() {
				@Override
				public InputStream get() {
					return aInputStreamOperation.success();
				}
			});
			return Operation2.success(ezSpec);
		} else {
			return Operation2.failure(new ExceptionDiagnostic(aInputStreamOperation.failure()));
		}
	}

	@Override
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
