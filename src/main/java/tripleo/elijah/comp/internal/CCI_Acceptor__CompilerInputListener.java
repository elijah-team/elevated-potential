package tripleo.elijah.comp.internal;

import com.google.common.base.Preconditions;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.inputs.CompilerInput_;
import tripleo.elijah.comp.inputs.ILazyCompilerInstructions_;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.util.*;
import tripleo.elijah.util2.DebugFlags;
import tripleo.elijah.util2.UnintendedUseException;

import java.util.*;

public class CCI_Acceptor__CompilerInputListener implements CompilerInputListener {
	private final Compilation    compilation;
	public final  InstructionDoer id;

	public CCI_Acceptor__CompilerInputListener(CompilationImpl aCompilation) {
		this.compilation = aCompilation;
		this.id = new InstructionDoer(aCompilation);
	}

	public CompilerInstructions _root() {
		return id.root;
	}

	@Override
	public void change(CompilerInput i, CompilerInput_.CompilerInputField field) {
		final EIT_InputTree inputTree = compilation.getInputTree();

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

					final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
					if (instructionsMaybe != null) {
						var ci = instructionsMaybe.o.get();

						assert ci != null;

/*
						cr.pushNextCompilerInsructions(ci);
*/
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
					int y4 = 4;
				}
			}
		}
		case ACCEPT_CI -> {
			if (i.ty() == CompilerInput.Ty.ROOT) {
				final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
				if (instructionsMaybe != null) {
					final CompilerInstructions ci = instructionsMaybe.o.get();
					Preconditions.checkNotNull(ci);
					id.add(ci);
				}
			} else if (i.ty() == CompilerInput.Ty.SOURCE_ROOT) {
				final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
				if (instructionsMaybe != null) {
					CompilerInstructions ci = instructionsMaybe.o.get();
					id.add(ci);
				}
			} else {
				throw new UnintendedUseException();
			}
		}
		case HASH -> {
			int yy = 2;
			// FIXME latch all create/commit inputs.txt -> should be Buffer!!
			NotImplementedException.raise_stop();
		}
		case DIRECTORY_RESULTS -> {
			NotImplementedException.raise_stop();

			if (i.getDirectoryResults() != null) {
				List<Operation2<CompilerInstructions>> directoryResults = i.getDirectoryResults().getDirectoryResult();

				for (Operation2<CompilerInstructions> directoryResult : directoryResults) {
					if (directoryResult.mode() == Mode.SUCCESS) {
						final ILazyCompilerInstructions iLazyCompilerInstructions = ILazyCompilerInstructions_.of(directoryResult.success());

						id.add(iLazyCompilerInstructions.get());
					}
				}
			}
		}
		}
	}
}
