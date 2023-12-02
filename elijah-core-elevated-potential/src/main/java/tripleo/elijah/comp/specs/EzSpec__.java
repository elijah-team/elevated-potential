package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Operation;

import java.io.*;
import java.util.Objects;
import java.util.function.Supplier;

public final class EzSpec__ implements EzSpec {
	private final           String                file_name;
	private final           File                  file;
	private final @Nullable Supplier<InputStream> sis;

	public EzSpec__(String file_name, File file, @Nullable Supplier<InputStream> sis) {
		this.file_name = file_name;
		this.file      = file;
		this.sis       = sis;
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
