package tripleo.elijah.comp.nextgen.pw;

import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.*;

public final class PW_signalCalculateFinishParse implements PW_PushWork {
	private final static PW_signalCalculateFinishParse _instance = new PW_signalCalculateFinishParse();

	public static PW_signalCalculateFinishParse instance() {
		return _instance;
	}

	private PW_signalCalculateFinishParse() { }

	@Override
	public void handle(final PW_Controller pwc, final PW_PushWork otherInstance) {
		if (pwc instanceof CompilationImpl.PW_CompilerController pwcc) {
			final CP_Paths paths = pwcc.paths();
			paths.signalCalculateFinishParse(); // TODO maybe move this 06/22
		}
	}
}
