package tripleo.elijah.comp;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;

public class InstructionDoer implements CompletableProcess<CompilerInstructions> {
	private final Compilation          _g_compilation;
	public        CompilerInstructions root;

	public InstructionDoer(Compilation compilation1) {
		this._g_compilation = compilation1;
	}

	@Override
	public void add(final CompilerInstructions item) {
		ICompilationRunner __cr = _g_compilation.getCompilationEnclosure().getCompilationRunner();
		if (root == null) {
			root = item;
			try {
				_g_compilation.setRootCI(root);

//				__cr.start(compilation1.getRootCI(), compilation1.pa());
			} catch (Exception aE) {
				throw new RuntimeException(aE);
			}
		} else {
			SimplePrintLoggerToRemoveSoon.println_err_4("second: " + item.getFilename());

			final Compilation compilation = __cr.c();

			compilation.use(item, USE_Reasonings.instruction_doer_addon(item));
		}
	}

	@Override
	public void complete() {
		SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::complete");
	}

	@Override
	public void error(final Diagnostic d) {
		SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::error");
	}

	@Override
	public void preComplete() {
		SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::preComplete");
	}

	@Override
	public void start() {
		SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::start");
	}
}
