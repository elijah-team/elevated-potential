package tripleo.elijah.nextgen.inputtree;

import tripleo.elijah.g.*;

import java.util.*;
import java.util.stream.*;

public interface EIT_ModuleList {
	void add(GWorldModule m);

	List<GWorldModule> getMods();

	Stream<GWorldModule> stream();
}
