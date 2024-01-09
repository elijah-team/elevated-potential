package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public interface ICompilerInstructionsObserver {
	@NotNull Operation<Ok> almostComplete();

	void onComplete();

	void onError(Throwable e);

	void onNext(CompilerInstructions aCompilerInstructions);
}
