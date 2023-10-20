package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.Stages;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.i.RP_Context;
import tripleo.elijah.comp.i.RuntimeProcess;
import tripleo.elijah.comp.i.extra.*;

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
	public void run(Compilation0 aComp, RP_Context ctx) {
		run_((Compilation)aComp, null, null);
	}
}
