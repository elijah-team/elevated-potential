package tripleo.elijah_durable_elevated.elijah.comp.impl;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah_durable_elevated.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.elijah.comp.internal.CompilationImpl;

public class LCM_Event_RootCI implements LCM_Event {
	private static final LCM_Event_RootCI INSTANCE = new LCM_Event_RootCI();

	private LCM_Event_RootCI() {}

	public static LCM_Event_RootCI instance() {
		return INSTANCE;
	}

	@Override
	public void handle(final LCM_HandleEvent aHandleEvent) {
		final LCM_CompilerAccess                c      = aHandleEvent.compilation();
		final CompilerInstructions              rootCI = (CompilerInstructions) aHandleEvent.obj();
		final CompilationImpl.CompilationConfig cfg    = c.cfg();

		try {
//			c.c().setRootCI(rootCI);
			c.cr().start(rootCI, c.c().pa());
		} catch (Exception aE) {
			aHandleEvent.lcm().exception(aHandleEvent, aE);
		}
	}
}
