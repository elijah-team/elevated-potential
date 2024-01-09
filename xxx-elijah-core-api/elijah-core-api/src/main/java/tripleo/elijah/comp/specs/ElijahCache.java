package tripleo.elijah.comp.specs;

import tripleo.elijah.lang.i.*;

import java.util.*;

public interface ElijahCache {
	Optional<OS_Module> get(String aAbsolutePath);

	void put(ElijahSpec aSpec, String aAbsolutePath, OS_Module aModule);
}
