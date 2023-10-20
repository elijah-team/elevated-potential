package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation0;

public interface RuntimeProcess {
	void postProcess();

	void prepare() throws Exception;

	void run(final Compilation0 aComp, RP_Context ctx);
}
