package tripleo.elijah.world.i;

import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.util.Eventual;

public interface LF_CodeRegistration {
	void accept(final EvaFunction aEvaFunction, final Eventual<Integer> aCodeCallback);
}
