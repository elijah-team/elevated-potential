package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.gen_fn.*;

interface IVariableConnector {
	void connect(VariableTableEntry aVte, String aName);
}
