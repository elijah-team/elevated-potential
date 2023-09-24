package tripleo.elijah.comp;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;

public class InstructionDoer implements CompletableProcess<CompilerInstructions> {
	private final Compilation compilation1;
	public CompilerInstructions root;

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

				__cr.start(compilation1.getRootCI(), compilation1.pa());
			} catch (Exception aE) {
				throw new RuntimeException(aE);
			}
		} else {
			System.err.println("second: " + item.getFilename());

			var do_out = false;
			var compilation = __cr.c();

			try {
				if (false)
					compilation.use(item, do_out);
			} catch (Exception aE) {
				throw new RuntimeException(aE);
			}
		}
	}

	@Override
	public void complete() {
		System.err.println("InstructionDoer::complete");
	}

	@Override
	public void error(final Diagnostic d) {
		System.err.println("InstructionDoer::error");
	}

	@Override
	public void preComplete() {
		System.err.println("InstructionDoer::preComplete");
	}

	@Override
	public void start() {
		System.err.println("InstructionDoer::start");
	}
}
