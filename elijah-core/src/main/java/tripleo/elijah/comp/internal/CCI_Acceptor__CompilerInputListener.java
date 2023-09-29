package tripleo.elijah.comp.internal;

import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

import java.util.*;

public /* static */ class CCI_Acceptor__CompilerInputListener implements CompilerInputListener {
	private final Compilation     compilation;
	public final  InstructionDoer id;
	private       CCI             cci;
	private       IProgressSink   _ps;

	public CCI_Acceptor__CompilerInputListener(CompilationImpl aCompilation) {
		this.compilation = aCompilation;

		this.id = new InstructionDoer(aCompilation);
	}

	public CompilerInstructions _root() {
		return id.root;
	}

	@Override
	public void change(CompilerInput i, CompilerInput.CompilerInputField field) {
		var inputTree = compilation.getInputTree();

		compilation.getCompilationEnclosure().logProgress(CompProgress.__CCI_Acceptor__CompilerInputListener__change__logInput, i);

		switch (field) {
		case TY -> {

			switch (i.ty()) {
			case NULL -> {
				int y2 = 2;
				// inputTree.addNode(i); README obviously skip nulls
			}
			case SOURCE_ROOT -> {
				int y3 = 2;
				inputTree.addNode(i);


			}
			case ROOT -> {
				inputTree.addNode(i);

				final CompilationRunner cr = compilation.getCompilationEnclosure().getCompilationRunner();
				final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
				if (instructionsMaybe != null) {
					var ci = instructionsMaybe.o.get();

					assert ci != null;

					cr._cis().onNext(ci);
					id.add(ci);
				}
			}
			// README has to wait for ACCEPT_CI, as it is assigned after `ty` is changed
//						hasInstructions(List_of(i....));
			case ARG -> {
				// inputTree.addNode(i); README skip ARGS

				// FIXME processOption here (ie apply compiler change)
				int yyy = 3;
			}
			case STDLIB -> {
				int y4=4;
			}
			}
		}
		case ACCEPT_CI -> {
			if (i.ty() == CompilerInput.Ty.ROOT) {
				final CompilationRunner cr = compilation.getCompilationEnclosure().getCompilationRunner();
				final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
				if (instructionsMaybe != null) {
					var ci = instructionsMaybe.o.get();

					assert ci != null;

					if (true || false) {
						cr._cis().onNext(ci);
						id.add(ci);
//								hasInstructions(List_of(i.acceptance_ci().o.get()));
					}
				}
			} else {
				throw new UnintendedUseException();
			}
		}
		case HASH -> {
			int yy = 2;
			// FIXME latch all create/commit inputs.txt -> should be Buffer!!
		}
		case DIRECTORY_RESULTS -> {
			int y = 2;

			if (i.getDirectoryResults() != null) {
				List<Operation2<CompilerInstructions>> directoryResults = i.getDirectoryResults();

				for (Operation2<CompilerInstructions> directoryResult : directoryResults) {
					if (directoryResult.mode() == Mode.SUCCESS) {
						ILazyCompilerInstructions iLazyCompilerInstructions = ILazyCompilerInstructions.of(directoryResult.success());

						id.add(iLazyCompilerInstructions.get());

						cci.accept(new Maybe<>(iLazyCompilerInstructions, null), _ps);
					}
				}
			}
		}
		}
	}

	public void set(CCI aCci, IProgressSink aPs) {
		cci = aCci;
		_ps = aPs;
	}
}
