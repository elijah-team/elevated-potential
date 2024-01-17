package tripleo.elijah.compiler_model;

import java.util.Objects;
import tripleo.wrap.File;

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
