package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.*;

import java.io.*;
import java.util.Objects;

import tripleo.elijah_fluffy.util.Operation;
import tripleo.wrap.File;

public interface EzSpec {
	@NotNull Operation<String> absolute1();

	String file_name();

	File file();

	@Nullable Operation<InputStream> sis();

	@Override
	boolean equals(Object obj);

	@Override
	int hashCode();

	@Override
	String toString();

	default String file_name_string() {
		final String result = file().toString();

		assert Objects.equals(result, file().getWrappedFilename());

		return result; //getWrappedFilename(); // File.toString!!
	}
}
