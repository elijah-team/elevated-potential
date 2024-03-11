package tripleo.elijah_durable_elevated.elijah.comp;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah_durable_elevated.elijah.comp.internal.CompilationRunner;
import tripleo.elijah_durable_elevated.elijah.comp.internal.USE_Reasonings;

public class InstructionDoer implements CompletableProcess<CompilerInstructions> {
	private final Compilation         compilation1;
	public        CompilerInstructions root;

	public InstructionDoer(Compilation compilation1) {
		this.compilation1 = compilation1;
	}

	@Override
	public void add(final CompilerInstructions item) {
		CompilationRunner __cr = compilation1.getCompilationEnclosure().getCompilationRunner();
		if (root == null) {
			root = item;
			try {
				compilation1.setRootCI(root);

//				__cr.start(compilation1.getRootCI(), compilation1.pa());
			} catch (Exception aE) {
				throw new RuntimeException(aE);
			}
		} else {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("second: " + item.getFilename());

			var compilation = __cr.c();

			compilation.use(item, USE_Reasonings.instruction_doer_addon(item));
		}
	}

	@Override
	public void complete() {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::complete");
	}

	@Override
	public void error(final Diagnostic d) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::error");
	}

	@Override
	public void preComplete() {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::preComplete");
	}

	@Override
	public void start() {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::start");
	}
}
