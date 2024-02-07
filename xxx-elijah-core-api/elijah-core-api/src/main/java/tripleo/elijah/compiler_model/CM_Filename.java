package tripleo.elijah.compiler_model;

import java.util.Objects;
import tripleo.wrap.File;

public interface CM_Filename {
	String getFilenameString();

	default File fileOf() {
		return new File(getFilenameString());
	}

	default String printableString() {
		return getFilenameString();
	}

	default boolean sameString(String string) {
		return Objects.equals(string, getFilenameString());
	}
}
