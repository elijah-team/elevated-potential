package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevated.r.ReactingReduxCallback;

public interface Elevated_CM_Module {
	OS_Module getModule();
	Compilation getCompilation();

	void reactTo(ReactingReduxCallback aRAC);
}
