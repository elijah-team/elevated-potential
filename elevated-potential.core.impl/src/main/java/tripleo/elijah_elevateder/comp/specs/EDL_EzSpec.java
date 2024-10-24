package tripleo.elijah_elevateder.comp.specs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah_fluffy.diagnostic.ExceptionDiagnostic;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public record EDL_EzSpec(
		String file_name,
		tripleo.wrap.File file,
		Supplier<Operation<InputStream>> sois) implements EzSpec {

	public static Operation2<EzSpec> of(final String aFileName, final File aFile, final Operation<InputStream> aInputStreamOperation) {
		if (aInputStreamOperation.mode() == Mode.SUCCESS) {
			final EDL_EzSpec ezSpec = new EDL_EzSpec(aFileName, aFile, new Supplier<Operation<InputStream>>() {
				@Override
				public Operation<InputStream> get() {
					return aInputStreamOperation;
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

	@Override
	public @Nullable Operation<InputStream> sis() {
		return sois.get();
	}
}
