package tripleo.elijah.comp.internal;

import java.util.function.*;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.nextgen.impl.*;
import tripleo.elijah.g.*;
import tripleo.elijah.util.*;
import tripleo.wrap.*;

public class CD_FindStdLibImpl implements CD_FindStdLib {

	private Operation2<CompilerInstructions> foundResult;

	@Override
	public void findStdLib(final GCR_State crState, final String aPreludeName, final Consumer<Operation2<CompilerInstructions>> coci) {
		findStdLib((CR_State) crState, aPreludeName, coci);
	}

	//@Override
	public void findStdLib(final @NotNull CR_State crState,
			final @NotNull String aPreludeName,
			final @NotNull Consumer<Operation2<CompilerInstructions>> coci) {
		final CompilationRunner  compilationRunner = crState.runner();
		final CompilationClosure cc                = compilationRunner._accessCompilation().getCompilationClosure();
		final CP_StdlibPath      slr               = cc.getCompilation().paths().stdlibRoot();
		final CP_Path            pl                = slr.child("lib-" + aPreludeName);
		final CP_Path            sle               = pl.child("stdlib.ez");

		@NotNull
		Operation2<CompilerInstructions> result = null;

		try {
			final File local_stdlib = sle.toFile();
			cc.getCompilation().getCompilationEnclosure().logProgress(CompProgress.DriverPhase, Pair.of(3939, "" + local_stdlib));

			if (local_stdlib.exists()) {
				try {
					//final CK_SourceFile<CompilerInstructions> sourceFile2 = sle.getSourceFile();
					final CK_SourceFile<CompilerInstructions> sourceFile2 = CK_SourceFileFactory.get(sle, CK_SourceFileFactory.K.SpecifiedPathEzFile);
					sourceFile2.associate(cc);

					result = sourceFile2.process_query();

//					if (false) { // matrix test
//						//noinspection StatementWithEmptyBody
//						if (result.mode() == Mode.SUCCESS) {
//							cc.getCompilation().pushItem(result.success());
//						} else {
//							// README otherwise pass through
//						}
//					}

					assert result != null;
					assert result.mode() == Mode.SUCCESS;
				} catch (final Exception e) {
					result = Operation2.failure_exc(e);
				}
			}
			if (result == null) {
				throw new NeverReached();

//				result = Operation.failure(new Exception("No stdlib found"));
			}

		} catch (Exception aE) {
			result = Operation2.failure_exc(aE);
		}

		foundResult = result;
		coci.accept(result);
	}

	@Override
	public CompilerInstructions maybeFoundResult() {
		if (foundResult.mode() == Mode.SUCCESS)
			return foundResult.success();
		return null;
	}


	static class NeverReached extends RuntimeException {
	}
}
