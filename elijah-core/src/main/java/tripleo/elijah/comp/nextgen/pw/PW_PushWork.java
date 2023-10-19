package tripleo.elijah.comp.nextgen.pw;

import tripleo.elijah.comp.internal.*;

public interface PW_PushWork {
	void handle(PW_Controller pwc, PW_PushWork otherInstance);

	// TODO convenience function, even tho we said...
	default void execute(CompilationImpl.PW_CompilerController aPWCompilerController) {
		handle(aPWCompilerController, null);
	}
}
