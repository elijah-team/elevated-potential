package tripleo.elijah.comp.queries;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah_fluffy.util.Operation2;

import java.util.List;

public interface CompilerInstructions_Result {
	List<Operation2<CompilerInstructions>> getDirectoryResult();

	List<Pair<Operation2<CompilerInstructions>,QSEZ_Reasoning>> getDirectoryResult2();

	void add(Operation2<CompilerInstructions> aCio, final QSEZ_Reasoning aReasoning);
}
