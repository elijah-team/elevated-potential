package tripleo.elijah_durable_elevated.elijah.world.i;

import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaFunction;

public interface LF_CodeRegistration {
	void accept(final EvaFunction aEvaFunction, final Eventual<Integer> aCodeCallback);
}