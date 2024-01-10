package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.ExceptionDiagnostic;

import java.io.*;

import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.wrap.File;
import java.util.Objects;
import java.util.function.Supplier;

public final class EzSpec__ implements EzSpec {
	private final           String                file_name;
	private final           tripleo.wrap.File     file;
	private final @Nullable Supplier<InputStream> sis;

	public EzSpec__(final String aFileName, final tripleo.wrap.File aFile, final Supplier<InputStream> aInputStreamSupplier) {
		this.file_name = aFileName;
		this.file      = aFile;
		this.sis = aInputStreamSupplier;
	}

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

	@Override
	public String file_name() {
		return file_name;
	}

	@Override
	public File file() {
		return file;
	}

	@Override
	public @Nullable Supplier<InputStream> sis() {
		return sis;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (EzSpec) obj;
		return Objects.equals(this.file_name, that.file_name()) &&
				Objects.equals(this.file, that.file()) &&
				Objects.equals(this.sis, that.sis());
	}

	@Override
	public int hashCode() {
		return Objects.hash(file_name, file, sis);
	}

	@Override
	public String toString() {
		return "EzSpec[" +
				"file_name=" + file_name + ", " +
				"file=" + file + ", " +
				"sis=" + sis + ']';
	}
}

//
//
//
