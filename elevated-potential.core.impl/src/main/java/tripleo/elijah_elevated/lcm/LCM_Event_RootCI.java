package tripleo.elijah_elevated.lcm;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.LCM_CompilerAccess;
import tripleo.elijah.comp.i.LCM_Event;
import tripleo.elijah.comp.i.LCM_HandleEvent;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;

public class LCM_Event_RootCI implements LCM_Event {
	private static final LCM_Event_RootCI INSTANCE = new LCM_Event_RootCI();

	private LCM_Event_RootCI() {}

	public static LCM_Event_RootCI instance() {
		return INSTANCE;
	}

	@Override
	public void handle(final LCM_HandleEvent aHandleEvent) {
		final LCM_CompilerAccess   c      = aHandleEvent.compilation();
		final CompilerInstructions rootCI = (CompilerInstructions) aHandleEvent.obj();
//		final CompilationImpl.CompilationConfig cfg    = c.cfg();

		try {
			final CompilationEnclosure ce = (CompilationEnclosure) c.c().getCompilationEnclosure();

			ce.getPipelineAccessPromise().then(pa -> {
				c.cr().start(rootCI, pa);
			});
		} catch (Exception aE) {
			aHandleEvent.lcm().exception(aHandleEvent, aE);
		}
	}
}
