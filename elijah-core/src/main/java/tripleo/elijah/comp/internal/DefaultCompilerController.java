package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.ApacheOptionsProcessor;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.CompilerInstructionsObserver;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Stupidity;

import java.util.List;

public class DefaultCompilerController implements CompilerController {
	List<String> args;
	private Compilation c;
	ICompilationBus cb;
	List<CompilerInput> inputs;

	@Override
	public void _setInputs(final Compilation aCompilation, final List<CompilerInput> aInputs) {
		c      = aCompilation;
		inputs = aInputs;
	}

	public void hook(final CompilationRunner aCr) {

	}

	@Override
	public void printUsage() {
		Stupidity.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
	}

	@Override
	public Operation<Ok> processOptions() {
		final OptionsProcessor             op                   = new ApacheOptionsProcessor();
		final CompilerInstructionsObserver cio                  = new CompilerInstructionsObserver(c);

		final CompilationEnclosure         compilationEnclosure = c.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(c.con().createCompilationAccess());
		compilationEnclosure.setCompilationBus(c.con().createCompilationBus());//new CompilationBus(compilationEnclosure));

		cb = c.getCompilationEnclosure().getCompilationBus();

		c._cis()._cio = cio;

		return op.process(c, inputs, cb); // TODO 09/08 Make this more complicated
	}

	@Override
	public void runner() {
		runner(new _DefaultCon());
	}

	@Override
	public void runner(final @NotNull Con con) {
		c.subscribeCI(c._cis()._cio);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		var cr = con.newCompilationRunner(compilationAccess);

		ce.setCompilationRunner(cr);

		hook(cr);

		var inputTree = c.getInputTree();

		for (CompilerInput input : inputs) {
			if (input.isNull()) // README filter out args
				inputTree.addNode(input);
		}

		cb.add(new CB_FindCIs(cr, inputs));
		cb.add(new CB_FindStdLibAction(ce, cr).process());

		((DefaultCompilationBus) cb).runProcesses();
	}

	public interface Con {
		CompilationRunner newCompilationRunner(ICompilationAccess aCompilationAccess);
	}

	public class _DefaultCon implements Con {
		@Override
		public CompilationRunner newCompilationRunner(final ICompilationAccess compilationAccess) {
			final CR_State          crState = new CR_State(compilationAccess);
			final CompilationRunner cr      = new CompilationRunner(compilationAccess, crState);

			crState.setRunner(cr);

			return cr;
		}
	}
}
