package tripleo.elijah_elevateder.comp.internal;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.compilation_bus.EDL_CompilationRunner;
import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.comp.InstructionDoer;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public /* static */ class CCI_Acceptor__CompilerInputListener implements CompilerInputListener {
	private final Compilation       compilation;
	public final  InstructionDoer       id;
	private final EDL_CompilationRunner cr;
	private       CCI                   cci;
	private       IProgressSink _progressSink;

	public CCI_Acceptor__CompilerInputListener(EDL_Compilation aCompilation) {
		this.compilation = aCompilation;

		this.id = new InstructionDoer(aCompilation);

		cr = compilation.getCompilationEnclosure().getCompilationRunner();
	}

	public CompilerInstructions _root() {
		return id.root;
	}

	@Override
	public void change(CompilerInput i, CompilerInput.CompilerInputField field) {
		if (compilation.getCompilerInputListener() instanceof CCI_Acceptor__CompilerInputListener cci_listener) {
			if (DebugFlags.CCI_gate) {
				if (cci == null) {
					cci = new EDL_CCI(compilation, compilation._cis(), _progressSink);
				}
				if (_progressSink == null) {
					_progressSink = compilation.getCompilationEnclosure().getCompilationBus().defaultProgressSink();
				}
				cci_listener.set(cci, _progressSink);
			}
		}



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
						int y4 = 4;
					}
				}
			}
			case ACCEPT_CI -> {
				if (i.ty() == CompilerInput.Ty.ROOT) {
					final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
					if (instructionsMaybe != null) {
						final Eventual<CompilerInstructions> e = new Eventual<>();
						ILazyCompilerInstructions_.ofEventual(i,
																	 compilation.getCompilationClosure(),
																	 e);
						e.then(id::add);
					}
				} else if (i.ty() == CompilerInput.Ty.SOURCE_ROOT) {
					System.err.println("9997-102 hit SOURCE_ROOT");
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
					List<Operation2<CompilerInstructions>> directoryResults = i.getDirectoryResults().getDirectoryResult();

					for (Operation2<CompilerInstructions> directoryResult : directoryResults) {
						if (directoryResult.mode() == Mode.SUCCESS) {
						ILazyCompilerInstructions iLazyCompilerInstructions = ILazyCompilerInstructions_.of(directoryResult.success());

							id.add(iLazyCompilerInstructions.get());

							if (DebugFlags.CCI_gate) {
								cci.accept(Maybe.of(iLazyCompilerInstructions), _progressSink);
							}
						}
					}
				}
			}
		}
	}

	public void set(CCI aCci, IProgressSink aPs) {
		cci           = aCci;
		_progressSink = aPs;
	}
}
