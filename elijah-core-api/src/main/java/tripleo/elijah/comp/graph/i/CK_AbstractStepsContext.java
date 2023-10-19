package tripleo.elijah.comp.graph.i;

import tripleo.elijah.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;

import java.util.*;

public abstract class CK_AbstractStepsContext implements CK_StepsContext {
	private final List<CB_OutputString> lo = new ArrayList<>();
	private final     List<Diagnostic>  ld = new ArrayList<>();

	@Override public void addOutputString(final CB_OutputString os) {
		lo.add(os);
	}

	@Override public void addDiagnostic(final Diagnostic d) {

	}

	@Override
	public Operation<Ok> getExecutionResult() {
		return Operation.success(Ok.instance());
	}

	@Override
	public void begin() {
		throw new UnintendedUseException();
	}
}
