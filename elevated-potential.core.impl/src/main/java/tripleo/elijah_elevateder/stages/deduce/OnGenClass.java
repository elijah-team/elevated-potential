package tripleo.elijah_elevateder.stages.deduce;

import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;

@FunctionalInterface
public interface OnGenClass {
	void accept(final EvaClass aGeneratedClass);
}
