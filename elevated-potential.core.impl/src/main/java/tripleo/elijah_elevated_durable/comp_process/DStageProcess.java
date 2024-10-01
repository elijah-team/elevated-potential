package tripleo.elijah_elevated_durable.comp_process;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;
import tripleo.elijah_elevated_durable.comp.CR_State;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.comp.i.ProcessRecord;

// FIXME 10/18 move to Processbuilder (Steps)
class DStageProcess implements RuntimeProcess {
	private final ICompilationAccess ca;
	private final ProcessRecord      pr;

	@Contract(pure = true)
	public DStageProcess(final ICompilationAccess aCa, final ProcessRecord aPr) {
		ca = aCa;
		pr = aPr;
	}

	@Override
	public void postProcess() {
	}

	@Override
	public void prepare() {
		// assert pr.stage == Stages.D; // FIXME
	}

	public void run_(final Compilation aComp, final CR_State st, final CB_Output output) {

	}

	@Override
	public Operation<Ok> run(Compilation0 aComp, RP_Context ctx) {
		run_((Compilation) aComp, null, null);
		return null;
	}
}
