package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.function.*;

public interface EzSpec {
	@NotNull Operation<String> absolute1();

	String file_name();

	File file();

	@Nullable Supplier<InputStream> sis();

	@Override
	boolean equals(Object obj);

	@Override
	int hashCode();

	@Override
	String toString();
}
