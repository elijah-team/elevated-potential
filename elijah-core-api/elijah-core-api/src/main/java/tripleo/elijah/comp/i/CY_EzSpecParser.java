package tripleo.elijah.comp.i;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.util.*;

public interface CY_EzSpecParser {
	Operation2<CompilerInstructions> parse(EzSpec spec);
}
