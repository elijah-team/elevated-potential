package tripleo.elijah.comp.i;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

public interface RuntimeProcess {
	void postProcess();

	void prepare() throws Exception;

	Operation<Ok> run(final Compilation0 aComp, RP_Context ctx);
}
