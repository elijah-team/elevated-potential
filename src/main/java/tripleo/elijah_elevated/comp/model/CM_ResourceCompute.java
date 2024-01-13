package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.util.Operation;

public interface CM_ResourceCompute {
	<T> Operation<T> compute();
}
