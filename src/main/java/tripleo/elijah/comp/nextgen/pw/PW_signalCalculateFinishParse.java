package tripleo.elijah.comp.nextgen.pw;

import tripleo.elijah.comp.chewtoy.PW_CompilerController;
import tripleo.elijah.comp.nextgen.i.CP_Paths;

public final class PW_signalCalculateFinishParse implements PW_PushWork {
	private final static PW_signalCalculateFinishParse INSTANCE = new PW_signalCalculateFinishParse();

	public static PW_signalCalculateFinishParse instance() {
		return INSTANCE;
	}

	private PW_signalCalculateFinishParse() { }

	@Override
	public void handle(final PW_Controller pwc, final PW_PushWork otherInstance) {
		if (pwc instanceof PW_CompilerController pwcc) {
			final CP_Paths paths = pwcc.paths();
			paths.signalCalculateFinishParse();  // TODO maybe move this 06/22
		}
	}

	@Override
	public void execute(final PW_Controller aController) {
		handle(aController, null);  // FIXME 10/20 try to remember what other is for
	}
}
