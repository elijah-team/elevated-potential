package tripleo.elijah.comp.percy;

import tripleo.elijah.comp.i.CompilerInput;

public interface CN_CompilerInputWatcher {
	void event(e aEvent, CompilerInput aCompilerInput, final Object aObject);

	enum e {ACCEPT_CI, IS_EZ, APPLY0}
}
