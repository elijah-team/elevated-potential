package tripleo.elijah_elevateder.world.i;

import tripleo.elijah.util.Eventual;
import tripleo.elijah_elevateder.stages.gen_fn.EvaFunction;

public interface LF_CodeRegistration {
	void accept(final EvaFunction aEvaFunction, final Eventual<Integer> aCodeCallback);
}
