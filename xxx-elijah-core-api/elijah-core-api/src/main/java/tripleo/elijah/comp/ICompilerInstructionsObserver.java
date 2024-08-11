package tripleo.elijah.comp;

import org.jetbrains.annotations.*;

import tripleo.elijah.ci.*;
import tripleo.elijah.util.*;

public interface ICompilerInstructionsObserver {
	@NotNull
	Operation<Ok> almostComplete();

	void onComplete();

	void onError(Throwable e);

	void onNext(CompilerInstructions aCompilerInstructions);
}
