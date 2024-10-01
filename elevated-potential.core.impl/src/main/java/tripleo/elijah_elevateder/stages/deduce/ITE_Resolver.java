package tripleo.elijah_elevateder.stages.deduce;

import tripleo.elijah_elevateder.stages.gen_fn.IdentTableEntry;

public interface ITE_Resolver {
	void check();

	IdentTableEntry.ITE_Resolver_Result getResult();

	boolean isDone();
}
