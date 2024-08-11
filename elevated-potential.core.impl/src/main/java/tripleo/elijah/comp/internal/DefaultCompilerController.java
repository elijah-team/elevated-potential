package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.g.*;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.comp.backbone.*;
import tripleo.elijah_prolific.v.*;

import java.util.*;

public class DefaultCompilerController implements CompilerController {
	private ICompilationBus     cb;
	private List<CompilerInput> inputs;
	private Compilation c;
	//private final ICompilationAccess3 ca3;

	public DefaultCompilerController(final ICompilationAccess3 ignoredACa3) {
		//ca3 = aCa3;
	}

	@Override
	public void setEnclosure(final GCompilationEnclosure ce/*aCompilationEnclosure*/) {
		c      = (Compilation) ce.getCompilation();
		inputs = ((CompilationEnclosure) ce).getCompilerInput();
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

		// TODO 09/08 Make this more complicated
		//  24/08/08 picocli upsets me
		//  24/08/08 picoinjector upsets me (a little, should have vendored/forked)
		return op.process(c, inputs, cb);
	}

	@Override
	public void runner() {
		runner(new _DefaultCon());
	}

	public void hook(final CompilationRunner aCr) {

	}

	@Override
	public void runner(final @NotNull Con con) {
		// hi clojure, from another branch
		if (false) c.____m();

		c._cis().subscribeTo(c);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		final ICompilationRunner icr = con.newCompilationRunner(compilationAccess);
		final CompilationRunner  cr  = (CompilationRunner) icr;

		ce.setCompilationRunner(cr);

		hook(cr);

		cb.add(new CB_FindCIs(cr, inputs));
		cb.add(new CB_FindStdLibProcess(ce, cr));

		((DefaultCompilationBus) cb).runProcesses();

		c.getFluffy().checkFinishEventuals();
		V.exit(c);
	}

	public static class _DefaultCon implements Con {
		@Override
		public CompilationRunner newCompilationRunner(final ICompilationAccess compilationAccess) {
			final CR_State          crState = new CR_State(compilationAccess);
			final CompilationRunner cr      = new CompilationRunner(compilationAccess, crState);

			crState.setRunner(cr);

			return cr;
		}
	}
}
