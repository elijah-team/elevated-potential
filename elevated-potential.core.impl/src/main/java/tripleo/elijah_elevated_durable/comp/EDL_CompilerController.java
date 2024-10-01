package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.g.GCompilationEnclosure;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.compilation_bus.*;
import tripleo.elijah_elevateder.comp.*;
import tripleo.elijah_prolific.v.V;

import java.util.List;

public class EDL_CompilerController implements CompilerController {
	ICompilationBus     cb;
	List<CompilerInput> inputs;
	private       Compilation         c;
	private final ICompilationAccess3 ca3;

	public EDL_CompilerController(final ICompilationAccess3 aCa3) {
		ca3 = aCa3;
	}

	public void _setInputs(final Compilation0 aCompilation, final List<CompilerInput> aInputs) {
		c      = (Compilation) aCompilation;
		inputs = aInputs;
	}

	@Override
	public void setEnclosure(final GCompilationEnclosure aCompilationEnclosure) {
		final CompilationEnclosure ce = (CompilationEnclosure) aCompilationEnclosure;
		_setInputs(ce.getCompilation(), ce.getCompilerInput());
	}

	@Override
	public void printUsage() {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
	}

	@Override
	public Operation<Ok> processOptions() {
		final OptionsProcessor             op                   = new ApacheOptionsProcessor();
		final CompilerInstructionsObserver cio                  = new CompilerInstructionsObserver(c);
		final CompilationEnclosure         compilationEnclosure = c.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(c.con().createCompilationAccess());
		compilationEnclosure.setCompilationBus(cb = c.con().createCompilationBus());

		c._cis().set_cio(cio);

		return op.process(c, inputs, cb); // TODO 09/08 Make this more complicated
	}

	@Override
	public void runner() {
		runner(new _DefaultCon());
	}

	public void hook(final EDL_CompilationRunner aCr) {

	}

	@Override
	public void runner(final @NotNull Con con) {
		if (false) c.____m();

		c._cis().subscribeTo(c);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		final ICompilationRunner    icr = con.newCompilationRunner(compilationAccess);
		final EDL_CompilationRunner cr  = (EDL_CompilationRunner) icr;

		ce.setCompilationRunner(cr);

		hook(cr);

//		var inputTree = c.getInputTree();
//
//		for (CompilerInput input : inputs) {
//			if (input.isNull()) // README filter out args
//				inputTree.addNode(input);
//		}

		cb.add(new CB_FindCIs(cr, inputs));
		cb.add(new CB_FindStdLibProcess(ce, cr));

//		for (CompilerInput input : inputs) {
//			input.
//		}

		((EDL_CompilationBus) cb).runProcesses();

		c.getFluffy().checkFinishEventuals();
		V.exit();
	}

	public static class _DefaultCon implements Con {
		@Override
		public EDL_CompilationRunner newCompilationRunner(final ICompilationAccess compilationAccess) {
			final CR_State              crState = new CR_State(compilationAccess);
			final EDL_CompilationRunner cr      = new EDL_CompilationRunner(compilationAccess, crState);

			crState.setRunner(cr);

			return cr;
		}
	}
}
