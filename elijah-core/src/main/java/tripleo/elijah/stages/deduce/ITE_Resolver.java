package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.gen_fn.*;

public interface ITE_Resolver {
	void check();

	IdentTableEntry.ITE_Resolver_Result getResult();

	boolean isDone();
}
