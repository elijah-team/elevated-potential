package tripleo.elijah.comp.specs;

import org.jetbrains.annotations.*;
import tripleo.elijah.util.*;

import java.io.*;

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
}
