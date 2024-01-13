package tripleo.elijah.comp;

import tripleo.elijah.ci.CompilerInstructions;

public interface ICompilerInstructionsObserver {
	void almostComplete();

	void onComplete();

	void onError(Throwable e);

	void onNext(CompilerInstructions aCompilerInstructions);
}
