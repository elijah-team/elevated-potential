package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;

public interface ICompilerInstructionsObserver {
	@NotNull Operation<Ok> almostComplete();

	void onComplete();

	void onError(Throwable e);

	void onNext(CompilerInstructions aCompilerInstructions);
}
