package tripleo.elijah.comp.specs;

import java.io.*;
import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.util.*;
import tripleo.wrap.File;

public interface EzSpec {
	@NotNull
	Operation<String> absolute1();

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
