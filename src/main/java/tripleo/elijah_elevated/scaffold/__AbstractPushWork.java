package tripleo.elijah_elevated.scaffold;

import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;

public abstract class __AbstractPushWork implements PW_PushWork {
	@Override
	public void execute(final PW_Controller aController) {
		handle(aController, null);
	}
}
