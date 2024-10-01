package tripleo.elijah.comp.i;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.g.GCR_State;
import tripleo.elijah_fluffy.util.Operation2;

import java.util.function.Consumer;

public interface CD_FindStdLib extends CompilerDriven {
	void findStdLib(GCR_State crState, String aPreludeName, Consumer<Operation2<CompilerInstructions>> coci);

	CompilerInstructions maybeFoundResult();
}
