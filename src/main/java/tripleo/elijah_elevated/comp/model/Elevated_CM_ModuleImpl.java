package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevated.r.ReactingReduxCallback;
import tripleo.elijah_elevated.r.ReactingReduxItem;

public class Elevated_CM_ModuleImpl implements Elevated_CM_Module, ReactingReduxItem {
	private final OS_Module module;
	private final Compilation compilation;

	public Elevated_CM_ModuleImpl(final OS_Module aModule, final Compilation aCompilation) {
		module      = aModule;
		compilation = aCompilation;
	}

	@Override
	public OS_Module getModule() {
		return this.module;
	}

	@Override
	public Compilation getCompilation() {
		return this.compilation;
	}

	@Override
	public void reactTo(final ReactingReduxCallback aRAC) {
		aRAC.reacting(this);
	}
}
