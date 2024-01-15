package tripleo.elijah.comp.impl;

//import org.eclipse.emf.ecore.xml.type.util.XMLTypeResourceImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.CompilerController;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.comp.i.OptionsProcessor;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.process.CB_FindCIs;
import tripleo.elijah.comp.process.CB_FindStdLibProcess;
import tripleo.elijah.g.GCompilationEnclosure;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util2.DebugFlags;

import java.util.List;

public class DefaultCompilerController implements CompilerController {
	private static    Con                 con;
	private final     ICompilationAccess3 ca3;
	private           ICompilationBus     cb;
	private @Nullable List<CompilerInput> inputs; // NullMarked explicitly can be
	private           Compilation         c;

	public DefaultCompilerController(final ICompilationAccess3 aCa3) {
		ca3 = aCa3;
	}

	public Con getCon() {
		if (con == null) {
			con = new _DefaultCon();
		}
		return con;
	}

	public void _setInputs(final Compilation0 aCompilation, final List<CompilerInput> aInputs) {
		c = (Compilation) aCompilation;
		assert c == ca3.getComp();
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

		compilationEnclosure.provideCompilationAccess(c.con().createCompilationAccess());
		cb = c.con().createCompilationBus();
		compilationEnclosure.setCompilationBus(cb);

		c.provideCio(null);

		return op.process(c, inputs, cb); // TODO 09/08 Make this more complicated
	}

	@Override
	public void runner() {
		runner(getCon());
	}

	public void hook(final ICompilationRunner aCr) {

	}

	@Override
	public void runner(final @NotNull Con con) {
//		XMLTypeResourceImpl.DataFrame eclipse;
		if (DebugFlags.CLOJURE_FLAG) c.____m();

		c._cis().subscribeTo(c);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		ce.provideCompilationRunner(() -> con.createCompilationRunner(compilationAccess));
		final ICompilationRunner cr = ce.getCompilationRunner();

		hook(cr);

		cb.add(new CB_FindCIs(cr, inputs));
		cb.add(new CB_FindStdLibProcess(ce, cr));

		((DefaultCompilationBus) cb).runProcesses();

		c.getFluffy().checkFinishEventuals();
	}

	public static class _DefaultCon implements Con {
		@Override
		public CompilationRunner createCompilationRunner(final ICompilationAccess compilationAccess) {
			final CR_State          crState = new CR_State(compilationAccess);
			final CompilationRunner cr      = new CompilationRunner(compilationAccess, crState);

			crState.setRunner(cr);

			return cr;
		}
	}
}
