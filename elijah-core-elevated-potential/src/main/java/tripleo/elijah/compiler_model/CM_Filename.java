package tripleo.elijah.compiler_model;

import triepl.wrap.File;
import java.io.IOException;
import java.util.Objects;

public interface CM_Filename {
	String getString();

	default File fileOf() {
		return new File(getString());
	}

	default String printableString() {
		return getString();
	}

	default boolean sameString(String string) {
		return Objects.equals(string, getString());
	}
}
