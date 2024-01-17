package tripleo.elijah.comp.specs;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.Compilation0;

import java.util.*;

public interface EzCache {
	Optional<CompilerInstructions> get(String aAbsolutePath);

	void put(EzSpec aSpec, String aAbsolutePath, CompilerInstructions aR);

	Compilation0 getCompilation();
}
