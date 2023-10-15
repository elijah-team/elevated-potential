package tripleo.elijah.comp.i;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.util.*;

import java.util.function.Consumer;

public interface CD_FindStdLib extends CompilerDriven {
	void findStdLib(CR_State crState, String aPreludeName, Consumer<Operation2<CompilerInstructions>> coci);

	CompilerInstructions maybeFoundResult();
}
