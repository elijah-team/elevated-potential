package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;

public interface RuntimeProcess {
	void postProcess();

	void prepare() throws Exception;

	Operation<Ok> run(final Compilation0 aComp, RP_Context ctx);
}
