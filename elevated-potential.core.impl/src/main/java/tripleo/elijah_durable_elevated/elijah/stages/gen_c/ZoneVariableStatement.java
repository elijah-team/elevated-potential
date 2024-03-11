package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import tripleo.elijah.lang.i.*;

public class ZoneVariableStatement {

	private final VariableStatement variableStatement;

	public ZoneVariableStatement(final VariableStatement aVariableStatement) {
		variableStatement = aVariableStatement;
	}

	public OS_Element getContainerParent() {
		return this.variableStatement.getParent().getParent();
	}
}
