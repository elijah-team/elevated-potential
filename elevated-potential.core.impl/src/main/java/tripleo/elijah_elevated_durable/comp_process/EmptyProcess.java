package tripleo.elijah_elevated_durable.comp_process;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;
import tripleo.elijah_elevated_durable.comp.CR_State;
import tripleo.elijah_elevated_durable.pipelines.Pipeline;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.comp.i.ProcessRecord;

public final class EmptyProcess implements RuntimeProcess {
	public EmptyProcess(final ICompilationAccess aCompilationAccess, final ProcessRecord aPr) {
	}

	@Override
	public void postProcess() {
	}

	@Override
	public void prepare() {
	}

	@Override
	public Operation<Ok> run(final Compilation0 aComp, final RP_Context ctx0) {
		var ctx = (Pipeline.RP_Context_1) ctx0;
		run((Compilation) aComp, ctx.getState(), ctx.getContext());
		return null;
	}

	//@Override
	public void run(final Compilation aComp, final CR_State st, final CB_Output output) {

	}
}
