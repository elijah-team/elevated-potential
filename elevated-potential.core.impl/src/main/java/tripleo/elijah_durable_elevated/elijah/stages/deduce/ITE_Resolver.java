package tripleo.elijah_durable_elevated.elijah.stages.deduce;

import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.IdentTableEntry;

public interface ITE_Resolver {
	void check();

	IdentTableEntry.ITE_Resolver_Result getResult();

	boolean isDone();
}
