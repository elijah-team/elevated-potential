package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class DStageProcess implements RuntimeProcess {
	private final ICompilationAccess ca;
	private final ProcessRecord pr;

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
		assert ca.getStage() == Stages.D;
	}

	public void run_(final Compilation aComp, final CR_State st, final CB_Output output) {

	}

	@Override
	public Operation<Ok> run(Compilation0 aComp, RP_Context ctx) {
		run_((Compilation)aComp, null, null);
		return null;
	}
}
