package tripleo.elijah_durable_elevated.elijah.stages.deduce;

import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaClass;

@FunctionalInterface
public interface OnGenClass {
	void accept(final EvaClass aGeneratedClass);
}
